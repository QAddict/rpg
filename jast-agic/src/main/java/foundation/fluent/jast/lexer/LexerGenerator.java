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

package foundation.fluent.jast.lexer;

import foundation.fluent.jast.parser.generator.LrItem;
import foundation.fluent.jast.parser.generator.LrItemSet;
import foundation.fluent.jast.parser.grammar.Grammar;
import foundation.fluent.jast.parser.grammar.Rule;
import foundation.fluent.jast.parser.grammar.Symbol;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.emptySet;
import static java.util.Collections.singletonList;

public class LexerGenerator {

    private final Map<Character, Symbol> symbols = new HashMap<>();

    class Node {

    }

    public Grammar patternsToGrammar(List<String> patterns) {
        Symbol start = new LexerSymbol("###Start###");
        Set<Rule> rules = new LinkedHashSet<>();
        List<Rule> collect = patterns.stream().map(this::patternToRule).collect(Collectors.toList());
        collect.forEach(r -> rules.add(new Rule(start, singletonList(r.getLeft()))));
        rules.addAll(collect);
        return Grammar.grammar(
                start,
                rules,
                emptySet()
        );
    }

    public Symbol of(char s) {
        return symbols.computeIfAbsent(s, k -> new LexerSymbol("" + k));
    }

    public Rule patternToRule(String pattern) {
        List<Symbol> r = new ArrayList<>(pattern.length());
        boolean slashed = false;
        for(int i = 0; i < pattern.length(); i ++) {
            char c = pattern.charAt(i);
            if(slashed) {
                switch (c) {
                    case '\\': r.add(of(c)); break;
                    case '*': break;
                    case '+': break;
                    default:
                        break;
                }
            } else {
                if(c == '\\') slashed = true;
                else r.add(of(c));
            }
        }
        return new Rule(new LexerSymbol(pattern), r);
    }

    public void generate(Grammar patterns) {
        patterns.rulesFor(patterns.getStart()).forEach(rule -> {

        });
    }

}
