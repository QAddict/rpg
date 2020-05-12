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

import foundation.rpg.automata.*;
import foundation.rpg.grammar.Grammar;
import foundation.rpg.parser.ParseErrorException;

import javax.lang.model.element.Element;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class LexerGenerator {

    private final PatternToGrammar patternToGrammar = new PatternToGrammar();
    private final Map<LrItemSet, Integer> states = new LinkedHashMap<>();

    private int stateOf(LrItemSet set) {
        return states.computeIfAbsent(set, k -> states.size());
    }

    public LrParserAutomata process(Set<Element> elements, PrintWriter w) throws IOException, ParseErrorException {
        Grammar grammar = patternToGrammar.grammarFromPatterns(elements);
        LrParserAutomata lrParserAutomata = LexerConstructor.generateParser(grammar);
        w.println("\t\tif(symbol == -1) return new TokenEnd(new End(input.position()));");
        w.println("\t\tstate = 0;");
        w.println("\t\tfor(;;symbol = input.move()) switch(state) {");
        lrParserAutomata.getSets().forEach(set -> {
            Map<Character, Integer> chars = new LinkedHashMap<>();
            Map<Character, Integer> groups = new LinkedHashMap<>();
            AtomicReference<String> otherwise = new AtomicReference<>();
            otherwise.set("throw new IllegalStateException()");
            lrParserAutomata.actionsFor(set).forEach((s, a) -> {
                a.accept(new LrAction.LrActionVisitor() {
                    @Override
                    public void visitGoto(LrItemSet set) {
                        if(grammar.getTerminals().contains(s))
                            if(set.toString().startsWith("\\")) {
                                groups.put(set.toString().charAt(1), stateOf(set));
                            } else {
                                chars.put(set.toString().charAt(0), stateOf(set));
                            }
                    }

                    @Override
                    public void visitReduction(LrItem item) {
                        otherwise.set("return v -> v.visit(new " + item.getRule().getLeft() + "());");
                    }

                    @Override
                    public void visitAccept(LrItem item) {

                    }
                });
            });
            w.println("\t\t\tcase " + stateOf(set) + ": switch(symbol) {");
            chars.forEach((c, s) -> w.println("\t\t\t\tcase '" + c + "': state = " + s + "; break;"));
            w.println("\t\t\t\tdefault:");
            groups.forEach((g, s) -> w.println("\t\t\t\t\tif(" + g + "Has(symbol)) { state = " + s +"; break; }"));
            w.println("\t\t\t\t\t" + otherwise.get());
            w.println("\t\t\t}");

        });
        w.println("\t\t}");
        return lrParserAutomata;
    }

}
