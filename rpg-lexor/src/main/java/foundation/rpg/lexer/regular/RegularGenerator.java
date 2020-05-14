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

package foundation.rpg.lexer.regular;

import foundation.rpg.lexer.regular.Bfs;
import foundation.rpg.lexer.regular.RegularParser;
import foundation.rpg.lexer.regular.ast.*;
import foundation.rpg.lexer.regular.dfa.DFA;
import foundation.rpg.lexer.regular.dfa.StateSet;
import foundation.rpg.lexer.regular.dfa.Transformer;
import foundation.rpg.lexer.regular.thompson.GNFA;
import foundation.rpg.lexer.regular.thompson.State;
import foundation.rpg.lexer.regular.thompson.ThompsonPatternVisitor;
import foundation.rpg.parser.ParseErrorException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

public class RegularGenerator {

    private final RegularParser parser = new RegularParser();
    private final Transformer transformer = new Transformer();
    private final ThompsonPatternVisitor visitor = new ThompsonPatternVisitor();

    public Pattern parsePattern(String pattern) {
        try {
            return parser.parse(pattern);
        } catch (IOException | ParseErrorException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Pattern parseName(String name) {
        Pattern pattern = new Empty();
        for(int i = 0; i < name.length(); i++)
            pattern = new Chain(pattern, new Char(name.charAt(i)));
        return pattern;
    }

    public GNFA gnfaFrom(List<Pattern> patterns) {
        return visitor.visit(patterns);
    }

    public DFA transform(GNFA gnfa) {
        return transformer.transform(gnfa);
    }

    public void generate(DFA dfa, PrintWriter w) {
        Map<StateSet, Integer> states = new HashMap<>();
        Bfs.bfs((item, consumer) -> {
            w.println("\t\t\tcase " + states.computeIfAbsent(item, k -> states.size()) + ": switch(symbol) {");
            item.getCharTransitions().forEach((atom, nextSet) -> {
                w.println("\t\t\t\tcase '" + atom + "': state = " + states.computeIfAbsent(nextSet, k -> states.size()) + "; break;");
                consumer.accept(nextSet);
            });
            w.println("\t\t\t\tdefault:");
            item.getGroupTransitions().forEach((atom, nextSet) -> {
                w.println("\t\t\t\t\tif('" + atom + "') { state = " + states.computeIfAbsent(nextSet, k -> states.size()) + "; break; }");
                consumer.accept(nextSet);
            });
            List<Object> results = item.getStates().stream().filter(s -> nonNull(s.getResult())).map(State::getResult).collect(toList());
            if(results.isEmpty()) {
                w.println("\t\t\t\t\tthrow new IllegalStateException(\"\")");
            } else {
                w.println("\t\t\t\t\treturn visitor -> visitor.visit(" + results + ");");
            }
            w.println("\t\t\t}");
        }, Collections.singleton(dfa.getStart()));
        w.flush();
    }

}
