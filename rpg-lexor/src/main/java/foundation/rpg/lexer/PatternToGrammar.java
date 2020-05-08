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

import foundation.rpg.grammar.Grammar;
import foundation.rpg.grammar.Rule;
import foundation.rpg.grammar.Symbol;
import foundation.rpg.lexer.pattern.*;

import java.util.*;

import static foundation.rpg.grammar.Grammar.grammar;
import static foundation.rpg.grammar.Rule.rule;
import static foundation.rpg.grammar.Symbol.symbol;
import static java.util.Arrays.asList;
import static java.util.Collections.*;
import static java.util.stream.Collectors.toList;

public class PatternToGrammar {

    private final Map<Object, Symbol> map = new LinkedHashMap<>();
    private Symbol of(Object o) {
        return map.computeIfAbsent(o, k -> symbol(o.toString()));
    }

    public final Grammar grammarFromPatterns(List<Pattern> patterns) {
        Set<Rule> rules = new LinkedHashSet<>();
        Symbol start = symbol("Token");
        for(Pattern pattern : patterns) {
            Symbol patternSymbol = of(pattern);
            rules.add(rule(start, Collections.singletonList(patternSymbol), 10));
            pattern.accept(new Visitor() {
                @Override
                public void visitOptions(Pattern pattern) {
                    Symbol patternSymbol = of(pattern);
                    pattern.getOptions().forEach(option -> rules.add(rule(patternSymbol, option.getUnits().stream().map(u -> {
                        u.accept(this);
                        return of(u);
                    }).collect(toList()))));
                }

                @Override
                public void visitAnyTimes(AnyTimes anyTimes) {
                    Symbol left = of(anyTimes);
                    rules.add(rule(left, emptyList()));
                    rules.add(rule(left, asList(of(anyTimes.getChunk()), left)));
                    anyTimes.getChunk().accept(this);
                }

                @Override
                public void visitAtLeastOnce(AtLeastOnce node) {
                    Symbol left = of(node);
                    Symbol chunkSymbol = of(node.getChunk());
                    rules.add(rule(left, singletonList(chunkSymbol)));
                    rules.add(rule(left, asList(chunkSymbol, left)));
                    node.getChunk().accept(this);
                }

                @Override
                public void visitChar(char character) {
                    // No additional rule.
                }

                @Override
                public void visitGroup(char group) {
                    // No additional rule.
                }

                @Override
                public void visitChars(Set<Character> characters) {
                    characters.forEach(this::visitChar);
                }

                @Override
                public void visitNot(Set<Character> characters) {

                }

            });
        }
        return grammar(start, rules, emptySet());
    }

}
