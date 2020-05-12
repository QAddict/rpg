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

package foundation.rpg.lexer;

import foundation.rpg.Name;
import foundation.rpg.grammar.Grammar;
import foundation.rpg.grammar.Rule;
import foundation.rpg.grammar.Symbol;
import foundation.rpg.lexer.pattern.*;
import foundation.rpg.parser.ParseErrorException;

import javax.lang.model.element.Element;
import java.io.IOException;
import java.util.*;

import static foundation.rpg.grammar.Grammar.grammar;
import static foundation.rpg.grammar.Rule.rule;
import static foundation.rpg.grammar.Symbol.symbol;
import static java.util.Arrays.asList;
import static java.util.Collections.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class PatternToGrammar {

    private final PatternParser patternParser = new PatternParser();
    private final Map<Object, Symbol> map = new LinkedHashMap<>();

    private Symbol of(Object o) {
        return map.computeIfAbsent(o, k -> symbol(o.toString()));
    }

    private boolean hasPattern(Element element) {
        return nonNull(element.getAnnotation(foundation.rpg.Pattern.class));
    }

    private Pattern patternOf(Element element) throws IOException, ParseErrorException {
        return patternParser.parse(element.getAnnotation(foundation.rpg.Pattern.class).value());
    }

    private List<Symbol> symbolsOf(Element element) {
        char[] chars = element.getAnnotation(Name.class).value().toCharArray();
        List<Symbol> left = new ArrayList<>(chars.length);
        for(char c : chars)
            left.add(of(c));
        return left;
    }

    public final Grammar grammarFromPatterns(Set<Element> elements) throws IOException, ParseErrorException {
        Set<Rule> rules = new LinkedHashSet<>();
        Symbol start = symbol("Token");
        for(Element element : elements) {
            Symbol elementSymbol = of(element);
            rules.add(rule(start, singletonList(elementSymbol)));
            if(hasPattern(element)) {
                patternOf(element).accept(new RulesVisitor(rules, elementSymbol, emptyList(), null));
            } else {
                rules.add(rule(elementSymbol, symbolsOf(element), 10));
            }
        }
        return grammar(start, rules, emptySet());
    }

    private class RulesVisitor implements Visitor {
        private final Set<Rule> rules;
        private final Symbol left;
        private final List<Symbol> append;
        private final Pattern pattern;

        public RulesVisitor(Set<Rule> rules, Symbol left, List<Symbol> append, Pattern pattern) {
            this.rules = rules;
            this.left = left;
            this.append = append;
            this.pattern = pattern;
        }

        List<Symbol> concat(Symbol... s) {
            List<Symbol> r = new ArrayList<>(append.size() + s.length);
            r.addAll(asList(s));
            r.addAll(append);
            return r;
        }

        RulesVisitor rules(Symbol left, List<Symbol> append) {
            return new RulesVisitor(rules, left, new ArrayList<>(append), pattern);
        }

        @Override
        public void visitOptions(Pattern pattern) {
            pattern.getOptions().forEach(option -> {
                rules.add(rule(left, singletonList(of(option))));
                rules(left, append).visitOption(option);
            });
        }

        @Override
        public void visitOption(Option option) {
            if(isNull(option)) return;
            Symbol symbol = of(option);
            if(nonNull(option.getSuffix())) {
                rules(symbol, append).visitOption(option.getSuffix());
                option.getPrefix().accept(rules(symbol, singletonList(of(option.getSuffix()))));
            } else {
                option.getPrefix().accept(rules(symbol, append));
            }
        }

        @Override
        public void visitAnyTimes(AnyTimes anyTimes) {
            if(nonNull(anyTimes.getSuffix())) {
                rules(left, append).visitOption(anyTimes.getSuffix());
            }
            anyTimes.getPrefix().accept(rules(left, singletonList(left)));
        }

        @Override
        public void visitChar(Char node) {
            rules.add(rule(left, concat(of(node.getChar()))));
        }

        @Override
        public void visitGroup(Group node) {
            rules.add(rule(left, concat(of(node))));
        }

        @Override
        public void visitChars(Chars chars) {
            //chars
            //characters.forEach(this::visitChar);
        }

        @Override
        public void visitNot(Not not) {
            rules.add(rule(left, concat(of(not))));
        }

    }
}
