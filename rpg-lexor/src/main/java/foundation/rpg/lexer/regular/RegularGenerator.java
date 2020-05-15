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

import foundation.rpg.lexer.regular.ast.*;
import foundation.rpg.lexer.regular.dfa.DFA;
import foundation.rpg.lexer.regular.dfa.StateSet;
import foundation.rpg.lexer.regular.thompson.State;

import java.io.PrintWriter;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class RegularGenerator {

    private String escape(Object e) {
        return e.toString().replace("\\", "\\\\").replace("'", "\\'");
    }

    public void generate(DFA dfa, PrintWriter w, Function<Set<Object>, String> prioritizer) {
        Map<StateSet, Integer> states = new HashMap<>();
        w.println("public class NewLexer implements Lexer<State> {");
        w.println("\tpublic Token<State> next(Input input) throws IOException {");
        w.println("\t\tint state = " + states.computeIfAbsent(dfa.getStart(), k -> states.size()) + ";");
        w.println("\t\tint symbol = input.lookahead();");
        w.println("\t\tPosition mark = input.mark();");
        w.println("\t\tif(symbol < 0) return new TokenEnd(new End(mark));");
        w.println("\t\tfor(; true; symbol = input.move().lookahead()) {");
        w.println("\t\t\tswitch(state) {");
        Bfs.bfs((item, consumer) -> {
            Consumer<String> r = pref -> {
                item.getGroupTransitions().forEach((atom, nextSet) -> {
                    w.println(pref + "\t\t\t\t\tif(matches(\"" + atom + "\")) { state = " + states.computeIfAbsent(nextSet, k -> states.size()) + "; break; }");
                    consumer.accept(nextSet);
                });
                Set<Object> results = item.getStates().stream().filter(s -> nonNull(s.getResult())).map(State::getResult).collect(toSet());
                if (nonNull(item.getDefaultState())) {
                    w.println(pref + "\t\t\t\t\tif(symbol < 0) throw new IllegalStateException(\"\");");
                    w.println(pref + "\t\t\t\t\tstate = " + states.computeIfAbsent(item.getDefaultState(), k -> states.size()) + "; break;");
                } else if (results.isEmpty()) {
                    w.println(pref + "\t\t\t\t\tthrow new IllegalStateException(\"\")");
                } else {
                    w.println(pref + "\t\t\t\t\treturn new " + prioritizer.apply(results) + "(mark);");
                }
            };
            w.println("\t\t\t\tcase " + states.computeIfAbsent(item, k -> states.size()) + ":");
            int cases = item.getCharTransitions().size() + item.getInversions().size();
            if(cases > 1) {
                w.println("\t\t\t\t\tswitch(symbol) {");
                item.getCharTransitions().forEach((atom, nextSet) -> {
                    w.println("\t\t\t\t\t\tcase '" + escape(atom) + "': state = " + states.computeIfAbsent(nextSet, k -> states.size()) + "; break;");
                    consumer.accept(nextSet);
                });
                List<Character> invs = item.getInversions().stream().flatMap(inversion -> inversion.getCharClass().getItems().stream()).flatMap(Item::getChars)
                        .filter(c -> !item.getCharTransitions().containsKey(new Char(c))).collect(toList());
                if (!invs.isEmpty()) {
                    invs.forEach(c -> w.println("\t\t\t\t\t\tcase '" + escape(c) + "':"));
                    w.println("\t\t\t\t\t\t\tthrow new IllegalStateException(\"\");");
                }
                w.println("\t\t\t\t\t\tdefault:");
                r.accept("\t\t");
                w.println("\t\t\t\t\t}");
                w.println("\t\t\t\t\tbreak;");
            } else {
                item.getCharTransitions().forEach((atom, nextSet) -> {
                    w.println("\t\t\t\t\tif(symbol == '" + escape(atom) + "') { state = " + states.computeIfAbsent(nextSet, k -> states.size()) + "; break; }");
                    consumer.accept(nextSet);
                });
                item.getInversions().stream().flatMap(inversion -> inversion.getCharClass().getItems().stream()).flatMap(Item::getChars)
                        .filter(c -> !item.getCharTransitions().containsKey(new Char(c))).forEach(c -> {
                    w.println("\t\t\t\t\tif(symbol == '" + escape(c) + "') throw new IllegalStateException(\"\");");
                });
                r.accept("");
            }
        }, Collections.singleton(dfa.getStart()));
        w.println("\t\t\t}");
        w.println("\t\t}");
        w.println("\t}");
        w.println("}");
        w.flush();
    }
}
