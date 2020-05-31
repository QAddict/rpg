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

package foundation.rpg.generator.lexer;

import foundation.rpg.dfa.DFA;
import foundation.rpg.dfa.StateSet;
import foundation.rpg.gnfa.State;
import foundation.rpg.parser.*;
import foundation.rpg.util.Bfs;

import javax.lang.model.type.TypeMirror;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

import static foundation.rpg.dfa.StateSet.isError;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toSet;

public class RegularGenerator {

    private String escape(Object e) {
        return e.toString().replace("\\", "\\\\").replace("'", "\\'");
    }

    private void i(PrintWriter w, Class<?>... t) {
        for(Class<?> c : t) w.println("import " + c.getCanonicalName() + ";");
    }

    public <T> void generate(String pkg, String name, DFA dfa, Map<State, T> finalStates, PrintWriter ow, Function<Set<T>, String> prioritizer, TypeMirror factoryType) {
        try(PrintWriter w = ow) {
            Map<StateSet, Integer> states = new HashMap<>();
            w.println("package " + pkg + ";");
            w.println();
            i(w, Element.class, Lexer.class, Input.class, Position.class, End.class, IOException.class, TokenBuilder.class);
            w.println();
            w.println("public class " + name + " implements Lexer<State> {");
            if(nonNull(factoryType)) {
                w.println("\tprivate final " + factoryType + " factory;");
                w.println();
                w.println("\tpublic " + name + "(" + factoryType + " factory) {");
                w.println("\t\tthis.factory = factory;");
                w.println("\t}");
                w.println();
                w.println("\tpublic " + factoryType + " getFactory() {");
                w.println("\t\treturn factory;");
                w.println("\t}");
                w.println();
            }
            w.println("\tpublic Element<State> next(Input input) throws IOException {");
            w.println("\t\tint state = " + states.computeIfAbsent(dfa.getStart(), k -> states.size()) + ";");
            w.println("\t\tint symbol = input.lookahead();");
            w.println("\t\tTokenBuilder builder = new TokenBuilder(input);");
            w.println("\t\tif(symbol < 0) return new ElementEnd(new End(builder.build()));");
            w.println("\t\tfor(; true; symbol = builder.next()) {");
            w.println("\t\t\tswitch(state) {");
            Bfs.withItem(dfa.getStart(), (item, consumer) -> {
                Consumer<String> r = pref -> {
                    item.getGroups().forEach((atom, nextSet) -> {
                        w.println(pref + "\t\t\t\t\tif(Lexer.matchesGroup('" + atom + "', symbol)) { state = " + states.computeIfAbsent(nextSet, k -> states.size()) + "; break; }");
                        consumer.accept(nextSet);
                    });
                    Set<T> results = item.getStates().stream().filter(finalStates::containsKey).map(finalStates::get).collect(toSet());
                    if (!isError(item.getDefaultState())) {
                        w.println(pref + "\t\t\t\t\tif(symbol < 0) input.error(\"Unexpected end of input\");");
                        w.println(pref + "\t\t\t\t\tstate = " + states.computeIfAbsent(item.getDefaultState(), k -> states.size()) + "; break;");
                        consumer.accept(item.getDefaultState());
                    } else if (results.isEmpty()) {
                        w.println(pref + "\t\t\t\t\tinput.error(\"Unexpected character: '\" + (char) symbol + \"'\");");
                    } else {
                        String type = prioritizer.apply(results);
                        w.println(pref + "\t\t\t\t\treturn new " + type + ";");
                    }
                };
                w.println("\t\t\t\tcase " + states.computeIfAbsent(item, k -> states.size()) + ":");
                int cases = item.getTransitions().size();
                if (cases > 1) {
                    w.println("\t\t\t\t\tswitch(symbol) {");
                    item.getTransitions().forEach((atom, nextSet) -> {
                        w.println("\t\t\t\t\t\tcase '" + escape(atom) + "': " + stateBranch(nextSet, states));
                        consumer.accept(nextSet);
                    });
                    w.println("\t\t\t\t\t\tdefault:");
                    r.accept("\t\t");
                    w.println("\t\t\t\t\t}");
                    w.println("\t\t\t\t\tbreak;");
                } else {
                    item.getTransitions().forEach((atom, nextSet) -> {
                        w.println("\t\t\t\t\tif(symbol == '" + escape(atom) + "') { " + stateBranch(nextSet, states) + " }");
                        consumer.accept(nextSet);
                    });
                    r.accept("");
                }
            });
            w.println("\t\t\t}");
            w.println("\t\t}");
            w.println("\t}");
            w.println("}");
            w.flush();
        }
    }

    private String stateBranch(StateSet stateSet, Map<StateSet, Integer> states) {
        return isError(stateSet) ? "input.error(\"Unexpected character: '\" + (char) symbol + \"'\");" : "state = " + states.computeIfAbsent(stateSet, k -> states.size()) + "; break;";
    }

}
