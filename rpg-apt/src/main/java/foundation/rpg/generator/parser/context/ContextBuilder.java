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

package foundation.rpg.generator.parser.context;

import foundation.rpg.MetaRule;
import foundation.rpg.Precedence;
import foundation.rpg.StartSymbol;
import foundation.rpg.generator.parser.TypeUtils;
import foundation.rpg.grammar.Rule;
import foundation.rpg.grammar.Symbol;
import foundation.rpg.util.Bfs;

import javax.lang.model.AnnotatedConstruct;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import java.util.*;
import java.util.stream.Collectors;

import static foundation.rpg.generator.parser.TypeUtils.*;
import static foundation.rpg.generator.parser.context.Entry.*;
import static foundation.rpg.grammar.Grammar.grammar;
import static foundation.rpg.grammar.Rule.rule;
import static foundation.rpg.grammar.Symbol.symbol;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.*;
import static javax.lang.model.util.ElementFilter.typesIn;

public class ContextBuilder {

    private final Set<String> usedNames = new LinkedHashSet<>();
    private final Map<Symbol, Entry> symbolToType = new HashMap<>();
    private final Map<Entry, Symbol> typeToSymbol = new HashMap<>();
    private final SymbolNameVisitor symbolNameVisitor = new SymbolNameVisitor();

    public static Context createContext(ExecutableElement startRule, TypeMirror end) {
        return new ContextBuilder().build(startRule, end);
    }

    private Context build(ExecutableElement startRule, TypeMirror end) {
        StartSymbol startSymbolAnnotation = startRule.getAnnotation(StartSymbol.class);
        TypeElement factoryClassElement = get(startRule, TypeElement.class);
        PackageElement packageElement = get(factoryClassElement, PackageElement.class);
        String generatorPrefix = symbolNameVisitor.visit(startRule.getReturnType());
        Symbol startSymbol = returnSymbol(startRule);
        symbolToType.put(Symbol.end, typeEntry(end));
        symbolToType.put(Symbol.start, symbolToType.get(startSymbol));
        List<ExecutableElement> methods = methods(factoryClassElement).collect(toList());
        Set<Symbol> ignored = methods.stream().filter(TypeUtils::isVoid).flatMap(m -> m.getParameters().stream()).map(this::parameterSymbol).collect(Collectors.toCollection(LinkedHashSet::new));
        Map<Rule, ExecutableElement> ruleToMethod = new LinkedHashMap<>();
        methods.stream().filter(TypeUtils::notVoid).peek(ruleMethod -> {
            ruleToMethod.put(rule(returnSymbol(ruleMethod), ruleMethod.getParameters().stream().map(this::parameterSymbol).collect(toList())), ruleMethod);
            ParameterResolver map = new ParameterResolver();
            Bfs.withStream(ruleMethod.getParameters().stream().filter(this::hasMetaRuleAnnotation).map(VariableElement.class::cast), (parameter, queue) -> {
                Entry l = entry(map.resolve(parameter), parameter.getAnnotationMirrors());
                getMetaRuleAnnotations(parameter).forEach(metaRuleAnnotation -> typesIn(metaRuleAnnotation.getEnclosedElements()).stream().flatMap(TypeUtils::methods).forEach(metaRuleMethod -> {
                    map.populate(metaRuleMethod.getReturnType(), l);
                    List<Symbol> right = metaRuleMethod.getParameters().stream().peek(p -> {
                        if (hasMetaRuleAnnotation(p))
                            queue.accept(p);
                    }).map(p -> parameterMetaSymbol(p, map)).collect(toList());
                    ruleToMethod.put(rule(mapSymbol(l), right), metaRuleMethod);
                }));
            });
        }).filter(this::isPrecedenceRule).collect(toList()).forEach(precedenceRule -> {
            Element lower = findPrecedence(precedenceRule.getReturnType());
            List<? extends VariableElement> args = precedenceRule.getParameters().stream().filter(p -> nonNull(findPrecedence(p.asType()))).collect(toList());
            Map<Element, ? extends List<? extends VariableElement>> precedence = args.stream().collect(groupingBy(c -> findPrecedence(c.asType())));
            if(precedence.size() == 2 && precedence.containsKey(lower)) {
                Set<Element> keys = precedence.keySet();
                keys.remove(lower);
                ruleToMethod.put(rule(returnSymbol(precedenceRule), keys.stream().map(k -> parameterSymbol(precedence.get(k).get(0))).collect(toList())), null);
            }
        });
        return new Context(
                startRule,
                factoryClassElement,
                get(startSymbolAnnotation.packageName(), packageElement.toString()),
                get(startSymbolAnnotation.parserClassName(), generatorPrefix + "Parser"),
                get(startSymbolAnnotation.lexerClassName(), generatorPrefix + "Lexer"),
                ruleToMethod,
                symbolToType,
                grammar(startSymbol, ruleToMethod.keySet(), ignored)
        );
    }

    private boolean isPrecedenceRule(ExecutableElement e) {
        return hasAnnotationAnnotatedWith(e.getReturnType(), Precedence.class);
    }

    private List<Element> getMetaRuleAnnotations(Element parameter) {
        List<Element> a =  new ArrayList<>(getAnnotationAnnotatedWith(parameter.asType(), MetaRule.class));
        a.addAll(getAnnotationAnnotatedWith(parameter, MetaRule.class));
        return a;
    }

    private Element findPrecedence(AnnotatedConstruct e) {
        List<Element> precedence = getAnnotationAnnotatedWith(e, Precedence.class);
        return precedence.size() == 1 ? precedence.get(0) : null;
    }

    private boolean hasMetaRuleAnnotation(Element method) {
        return hasAnnotationAnnotatedWith(method, MetaRule.class) || hasAnnotationAnnotatedWith(method.asType(), MetaRule.class);
    }

    private Symbol mapSymbol(Entry entry) {
        return typeToSymbol.computeIfAbsent(entry, key -> {
            Symbol symbol = symbol(symbolName(entry));
            symbolToType.put(symbol, entry);
            return symbol;
        });
    }

    private String symbolName(Entry entry) {
        String s = entry.getSymbolPartAnnotations().stream().map(a -> a.getAnnotationType().asElement()).filter(TypeUtils::includeInName).map(e -> e.getSimpleName().toString()).collect(joining());
        s += symbolNameVisitor.visit(entry.getTypeMirror());
        while(!usedNames.add(s)) {
            s = s + "$";
        }
        return s;
    }
    private Symbol parameterSymbol(VariableElement v) {
        return mapSymbol(parameterEntry(v));
    }

    private Symbol returnSymbol(ExecutableElement method) {
        return mapSymbol(methodEntry(method));
    }

    private Symbol parameterMetaSymbol(VariableElement parameter, ParameterResolver map) {
        return mapSymbol(entry(map.resolve(parameter), parameter.getAnnotationMirrors()));
    }

    private static <T extends Element> T get(Element element, Class<T> type) {
        while (element != null && !type.isInstance(element)) element = element.getEnclosingElement();
        return type.cast(element);
    }

    private static String get(String annotationValue, String defaultValue) {
        return annotationValue.isEmpty() ? defaultValue : annotationValue;
    }


}
