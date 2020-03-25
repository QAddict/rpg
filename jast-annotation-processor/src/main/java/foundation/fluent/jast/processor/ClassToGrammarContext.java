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

package foundation.fluent.jast.processor;

import foundation.fluent.jast.parser.generator.LrItemSet;
import foundation.fluent.jast.parser.grammar.Grammar;
import foundation.fluent.jast.parser.grammar.Rule;
import foundation.fluent.jast.parser.grammar.Symbol;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import java.util.*;
import java.util.stream.Stream;

import static foundation.fluent.jast.parser.grammar.Rule.rule;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static javax.lang.model.util.ElementFilter.methodsIn;

public class ClassToGrammarContext {

    private final Set<String> usedNames = new HashSet<>();

    private String uniqueName(TypeMirror typeMirror) {
        String full = typeMirror.toString().replaceAll(">", "");
        String s = Stream.of(full.split("<")).map(p -> p.substring(p.lastIndexOf(".") + 1)).collect(joining("Of"));
        while(!usedNames.add(s)) {
            s = s + "$";
        }
        return s;
    }

    private static final class TypeSymbol implements Symbol {
        private final TypeMirror typeMirror;
        private final String uniqueName;

        private TypeSymbol(TypeMirror typeMirror, String uniqueName) {
            this.typeMirror = typeMirror;
            this.uniqueName = uniqueName;
        }

        @Override
        public String toString() {
            return uniqueName;
        }
    }

    private final Map<String, Symbol> symbolMap = new HashMap<>();
    private final Map<Rule, ExecutableElement> ruleAssociation = new HashMap<>();
    private final Grammar grammar;

    public ClassToGrammarContext(ExecutableElement startRule) {
        Set<Rule> rules = new LinkedHashSet<>();
        Set<Symbol> ignored = new LinkedHashSet<>();
        methodsIn(startRule.getEnclosingElement().getEnclosedElements()).forEach(method -> {
            if(method.getReturnType().getKind().equals(TypeKind.VOID)) {
                method.getParameters().forEach(p -> ignored.add(of(p.asType())));
            } else {
                rules.add(ruleOf(method));
            }
        });
        grammar = Grammar.grammar(
                of(startRule.getReturnType()),
                rules,
                ignored
        );
    }

    Symbol of(TypeMirror mirror) {
        return symbolMap.computeIfAbsent(mirror.toString(), key -> new TypeSymbol(mirror, uniqueName(mirror)));
    }

    Rule ruleOf(ExecutableElement method) {
        Rule rule = rule(of(method.getReturnType()), method.getParameters().stream().map(par -> of(par.asType())).collect(toList()));
        ruleAssociation.put(rule, method);
        return rule;
    }

    public Grammar getGrammar() {
        return grammar;
    }

    public String stateClassName(LrItemSet set) {
        return set.getName();
    }

    public TypeMirror symbolType(Symbol symbol) {
        return ((TypeSymbol) symbol).typeMirror;
    }

    public ExecutableElement methodOf(Rule rule) {
        return ruleAssociation.get(rule);
    }

}
