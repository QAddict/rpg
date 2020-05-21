/*
 * BSD 2-Clause License
 *
 * Copyright (c) 2020, Ondrej Fischer
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package foundation.rpg.parser.context;

import foundation.rpg.MetaRule;
import foundation.rpg.Priority;
import foundation.rpg.StartSymbol;
import foundation.rpg.SymbolPart;
import foundation.rpg.grammar.Grammar;
import foundation.rpg.grammar.Rule;
import foundation.rpg.grammar.Symbol;
import foundation.rpg.parser.Token;
import foundation.rpg.parser.generator.EnvironmentGenerator;
import foundation.rpg.parser.generator.TypeUtils;
import foundation.rpg.util.Bfs;

import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

import java.util.*;
import java.util.stream.Stream;

import static foundation.rpg.grammar.Rule.rule;
import static foundation.rpg.grammar.Symbol.symbol;
import static foundation.rpg.parser.context.Entry.entry;
import static foundation.rpg.parser.context.Entry.typeEntry;
import static java.util.Collections.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.*;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.STATIC;
import static javax.lang.model.util.ElementFilter.methodsIn;

public class ClassToGrammarContext {

    private final String packageName;
    private final Set<String> usedNames = new LinkedHashSet<>();
    private final Map<String, Symbol> symbolMap = new LinkedHashMap<>();
    private final Map<Symbol, Entry> typeMap = new LinkedHashMap<>();
    private final Map<Rule, ExecutableElement> ruleAssociation = new LinkedHashMap<>();
    private final Grammar grammar;
    private final boolean isStaticFactory;
    private final Element factoryClass;
    private final EnvironmentGenerator tokenContext;

    private Stream<ExecutableElement> methods(Element factory) {
        return Stream.concat(methodsIn(factory.getEnclosedElements()).stream(), ((TypeElement)factory).getInterfaces().stream().flatMap(i -> methods(((DeclaredType) i).asElement())));
    }

    public boolean isLexerRule(ExecutableElement method) {
        return method.getParameters().size() == 1 && method.getParameters().get(0).asType().toString().equals(Token.class.getCanonicalName());
    }

    public ClassToGrammarContext(ExecutableElement startRule, Elements elements, EnvironmentGenerator tokenContext) {
        this.tokenContext = tokenContext;
        String pkg = startRule.getAnnotation(StartSymbol.class).packageName();
        Set<Rule> rules = new LinkedHashSet<>();
        Set<Symbol> ignored = new LinkedHashSet<>();
        factoryClass = startRule.getEnclosingElement();
        packageName = pkg.isEmpty() ? factoryClass.getEnclosingElement().toString() : pkg;
        int priority = priority(factoryClass, 0);
        methods(factoryClass).filter(this::isLexerRule).forEach(tokenContext::accept);
        List<ExecutableElement> methods = methods(factoryClass).filter(m -> !m.getModifiers().contains(PRIVATE)).filter(m -> !isLexerRule(m)).collect(toList());
        Map<String, List<ExecutableElement>> metaRules = methods.stream().filter(this::hasMetaRuleAnnotation).collect(groupingBy(this::getMetaRuleAnnotation));
        methods.stream().filter(m -> !hasMetaRuleAnnotation(m)).forEach(method -> {
            if(method.getReturnType().getKind().equals(TypeKind.VOID)) {
                method.getParameters().forEach(p -> ignored.add(of(entry(p))));
            } else {
                rules.add(ruleOf(method, priority(method, priority)));
                Map<String, TypeMirror> map = new LinkedHashMap<>();
                Bfs.bfs((m, q) -> {
                    m.getParameters().stream().filter(this::hasMetaRuleAnnotation).forEach(p -> {
                        DeclaredType l = (DeclaredType) metaSymbol(p.asType(), map);
                        metaRules.getOrDefault(getMetaRuleAnnotation(p), emptyList()).forEach(metaRule -> {
                            map.putAll(TypeUtils.resolveParameters(metaRule, l));
                            rules.add(ruleOf(metaSymbol(entry(metaRule), map), priority, l, metaSymbols(metaRule, map)));
                            q.accept(metaRule);
                        });
                    });
                }, singleton(method));
            }
        });
        isStaticFactory = methods.stream().allMatch(method -> method.getModifiers().contains(STATIC));
        grammar = Grammar.grammar(of(entry(startRule)), rules, ignored);
        typeMap.put(Symbol.start, entry(startRule));
        typeMap.put(Symbol.end, typeEntry(elements.getTypeElement("foundation.rpg.parser.End")));
    }

    private void addMetaRules(ExecutableElement method, Map<String, List<ExecutableElement>> metaRules, Set<Rule> rules, int priority, Map<String, TypeMirror> pMap) {
        method.getParameters().stream().filter(this::hasMetaRuleAnnotation).forEach(p -> {
            DeclaredType l = (DeclaredType) metaSymbol(p.asType(), pMap);
            metaRules.getOrDefault(getMetaRuleAnnotation(p), emptyList()).forEach(metaRule -> {
                Map<String, TypeMirror> map = pMap.isEmpty() ? TypeUtils.resolveParameters(metaRule, l) : pMap;
                rules.add(ruleOf(metaSymbol(entry(metaRule), map), priority, l, metaSymbols(metaRule, map)));
                addMetaRules(metaRule, metaRules, rules, priority, map);
            });
        });
    }

    private boolean isMetaRuleAnnotation(AnnotationMirror a) {
        return nonNull((a.getAnnotationType()).asElement().getAnnotation(MetaRule.class));
    }

    private boolean hasMetaRuleAnnotation(Element e) {
        return e.getAnnotationMirrors().stream().anyMatch(this::isMetaRuleAnnotation);
    }

    private String getMetaRuleAnnotation(Element e) {
        return e.getAnnotationMirrors().stream().filter(this::isMetaRuleAnnotation).findFirst().map(Object::toString).orElse(null);
    }

    private List<Entry> metaSymbols(ExecutableElement metaRule, Map<String, TypeMirror> map) {
        return metaRule.getParameters().stream().map(this::scanTokens).map(t -> metaSymbol(t, map)).collect(toList());
    }

    private Entry metaSymbol(Entry t, Map<String, TypeMirror> map) {
        return entry(t.getElement(), map.getOrDefault(t.getType().toString(), t.getType()));
    }

    private TypeMirror metaSymbol(TypeMirror t, Map<String, TypeMirror> map) {
        return map.getOrDefault(t.toString(), t);
    }

    public int priority(Element element, int defaultPriority) {
        Priority annotation = element.getAnnotation(Priority.class);
        return isNull(annotation) ? defaultPriority : annotation.value();
    }

    public Symbol of(Entry element) {
        String name = element.getElement().getAnnotationMirrors().stream().map(AnnotationMirror::getAnnotationType)
                .filter(t -> nonNull(t.asElement().getAnnotation(MetaRule.class)) || nonNull(t.asElement().getAnnotation(SymbolPart.class)))
                .map(a -> a.asElement().toString()).collect(joining()) + element.getType();
        return symbolMap.computeIfAbsent(name, key -> {
            Symbol s = symbol(uniqueName(element));
            typeMap.put(s, element);
            return s;
        });
    }

    public Rule ruleOf(ExecutableElement method, int priority) {
        return ruleOf(entry(method), priority, method.getReturnType(), method.getParameters().stream().map(this::scanTokens).collect(toList()));
    }

    private Entry scanTokens(VariableElement e) {
        tokenContext.accept(e);
        return entry(e);
    }

    public Rule ruleOf(Entry method, int priority, TypeMirror l, List<? extends Entry> r) {
        Rule rule = rule(of(method), r.stream().map(this::of).collect(toList()), priority);
        ruleAssociation.put(rule, (ExecutableElement) method.getElement());
        return rule;
    }

    public Grammar getGrammar() {
        return grammar;
    }

    public TypeMirror symbolType(Symbol symbol) {
        return typeMap.get(symbol).getType();
    }

    public ExecutableElement methodOf(Rule rule) {
        return ruleAssociation.get(rule);
    }

    public String getPackageName() {
        return packageName;
    }

    private String uniqueName(Entry e) {
        String prefix = e.getElement().getAnnotationMirrors().stream().map(AnnotationMirror::getAnnotationType)
                .filter(t -> nonNull(t.asElement().getAnnotation(MetaRule.class)) || nonNull(t.asElement().getAnnotation(SymbolPart.class)))
                .map(a -> a.asElement().getSimpleName().toString()).collect(joining());
        return uniqueName(prefix, e.getType());
    }

    private String uniqueName(String prefix, TypeMirror typeMirror) {
        String full = typeMirror.toString().replaceAll(">", "");
        String s = prefix + Stream.of(full.split("<")).map(p -> p.substring(p.lastIndexOf(".") + 1)).collect(joining("Of"));
        while(!usedNames.add(s)) {
            s = s + "$";
        }
        return s;
    }

    public boolean isStaticFactory() {
        return isStaticFactory;
    }

    public Element getFactoryClass() {
        return factoryClass;
    }

}
