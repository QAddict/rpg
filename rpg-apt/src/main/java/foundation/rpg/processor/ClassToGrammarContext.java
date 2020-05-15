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

package foundation.rpg.processor;

import foundation.rpg.Match;
import foundation.rpg.MetaRule;
import foundation.rpg.Name;
import foundation.rpg.Priority;
import foundation.rpg.StartSymbol;
import foundation.rpg.generator.TypeUtils;
import foundation.rpg.grammar.Grammar;
import foundation.rpg.grammar.Rule;
import foundation.rpg.grammar.Symbol;
import foundation.rpg.lexer.LexerGenerator;

import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

import java.util.*;
import java.util.stream.Stream;

import static foundation.rpg.grammar.Rule.rule;
import static foundation.rpg.grammar.Symbol.symbol;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.*;
import static javax.lang.model.element.Modifier.STATIC;
import static javax.lang.model.util.ElementFilter.methodsIn;

public class ClassToGrammarContext {

    private final String packageName;
    private final Set<String> usedNames = new LinkedHashSet<>();
    private final Map<String, Symbol> symbolMap = new LinkedHashMap<>();
    private final Map<Symbol, TypeMirror> typeMap = new LinkedHashMap<>();
    private final Map<Rule, ExecutableElement> ruleAssociation = new LinkedHashMap<>();
    private final Grammar grammar;
    private final boolean isStaticFactory;
    private final Element factoryClass;
    private final ClassToTokenContext tokenContext;

    private Stream<ExecutableElement> methods(Element factory) {
        return Stream.concat(methodsIn(factory.getEnclosedElements()).stream(), ((TypeElement)factory).getInterfaces().stream().flatMap(i -> methods(((DeclaredType) i).asElement())));
    }

    public ClassToGrammarContext(ExecutableElement startRule, Elements elements, ClassToTokenContext tokenContext) {
        this.tokenContext = tokenContext;
        String pkg = startRule.getAnnotation(StartSymbol.class).packageName();
        Set<Rule> rules = new LinkedHashSet<>();
        Set<Symbol> ignored = new LinkedHashSet<>();
        factoryClass = startRule.getEnclosingElement();
        packageName = pkg.isEmpty() ? factoryClass.getEnclosingElement().toString() : pkg;
        int priority = priority(factoryClass, 0);
        List<ExecutableElement> methods = methods(factoryClass).collect(toList());
        Map<String, List<ExecutableElement>> metaRules = methods.stream().filter(this::hasMetaRuleAnnotation).collect(groupingBy(this::getMetaRuleAnnotation));
        methods.stream().filter(m -> !hasMetaRuleAnnotation(m)).forEach(method -> {
            if(method.getReturnType().getKind().equals(TypeKind.VOID)) {
                method.getParameters().forEach(p -> ignored.add(of(p.asType())));
            } else {
                rules.add(ruleOf(method, priority(method, priority)));
                addMetaRules(method, metaRules, rules, priority, emptyMap());
            }
        });
        isStaticFactory = methods.stream().anyMatch(method -> method.getModifiers().contains(STATIC));
        grammar = Grammar.grammar(of(startRule.getReturnType()), rules, ignored);
        typeMap.put(Symbol.start, startRule.getReturnType());
        typeMap.put(Symbol.end, elements.getTypeElement("foundation.rpg.parser.End").asType());
    }

    private void addMetaRules(ExecutableElement method, Map<String, List<ExecutableElement>> metaRules, Set<Rule> rules, int priority, Map<String, TypeMirror> pMap) {
        method.getParameters().stream().filter(this::hasMetaRuleAnnotation).forEach(p -> {
            DeclaredType l = (DeclaredType) metaSymbol(p.asType(), pMap);
            metaRules.getOrDefault(getMetaRuleAnnotation(p), emptyList()).forEach(metaRule -> {
                Map<String, TypeMirror> map = pMap.isEmpty() ? TypeUtils.resolveParameters(metaRule, l) : pMap;
                rules.add(ruleOf(metaRule, priority, l, metaSymbols(metaRule, map)));
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

    private List<TypeMirror> metaSymbols(ExecutableElement metaRule, Map<String, TypeMirror> map) {
        return metaRule.getParameters().stream().map(this::scanTokens).map(t -> metaSymbol(t, map)).collect(toList());
    }

    private TypeMirror metaSymbol(TypeMirror t, Map<String, TypeMirror> map) {
        return map.getOrDefault(t.toString(), t);
    }

    public int priority(Element element, int defaultPriority) {
        Priority annotation = element.getAnnotation(Priority.class);
        return isNull(annotation) ? defaultPriority : annotation.value();
    }

    public Symbol of(TypeMirror mirror) {
        return symbolMap.computeIfAbsent(mirror.toString(), key -> {
            Symbol s = symbol(uniqueName(mirror));
            typeMap.put(s, mirror);
            return s;
        });
    }

    public Rule ruleOf(ExecutableElement method, int priority) {
        return ruleOf(method, priority, method.getReturnType(), method.getParameters().stream().map(this::scanTokens).collect(toList()));
    }

    private TypeMirror scanTokens(VariableElement e) {
        tokenContext.analyzeToken(e);
        return e.asType();
    }

    public Rule ruleOf(ExecutableElement method, int priority, TypeMirror l, List<TypeMirror> r) {
        Rule rule = rule(of(l), r.stream().map(this::of).collect(toList()), priority);
        ruleAssociation.put(rule, method);
        return rule;
    }

    public Grammar getGrammar() {
        return grammar;
    }

    public TypeMirror symbolType(Symbol symbol) {
        return typeMap.get(symbol);
    }

    public ExecutableElement methodOf(Rule rule) {
        return ruleAssociation.get(rule);
    }

    public String getPackageName() {
        return packageName;
    }

    private String uniqueName(TypeMirror typeMirror) {
        String full = typeMirror.toString().replaceAll(">", "");
        String s = Stream.of(full.split("<")).map(p -> p.substring(p.lastIndexOf(".") + 1)).collect(joining("Of"));
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
