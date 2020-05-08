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
import foundation.rpg.automata.*;
import foundation.rpg.grammar.Grammar;
import foundation.rpg.lexer.pattern.Char;
import foundation.rpg.lexer.pattern.Option;
import foundation.rpg.lexer.pattern.Pattern;
import foundation.rpg.parser.ParseErrorException;

import javax.lang.model.element.Element;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

public class LexerGenerator {

    private final PatternParser patternParser = new PatternParser();
    private final PatternToGrammar patternToGrammar = new PatternToGrammar();
    private final Map<LrItemSet, Integer> states = new LinkedHashMap<>();

    private int stateOf(LrItemSet set) {
        return states.computeIfAbsent(set, k -> states.size());
    }

    public LrParserAutomata process(List<Element> elements, PrintWriter w) {
        List<Pattern> patterns = elements.stream().map(this::patternOf).collect(toList());
        Grammar grammar = patternToGrammar.grammarFromPatterns(patterns);
        LrParserAutomata lrParserAutomata = LexerConstructor.generateParser(grammar);
        w.println("\t\tswitch(state) {");
        w.println("\t\t\tcase -1: return new TokenEnd(new End(input.position()));");
        lrParserAutomata.getSets().forEach(set -> {
            w.println("\t\t\tcase " + stateOf(set) + ": switch(symbol) {");
            lrParserAutomata.actionsFor(set).forEach((s, a) -> {
                a.accept(new LrAction.LrActionVisitor() {
                    @Override
                    public void visitGoto(LrItemSet set) {
                        //if(grammar.getTerminals().contains(s))
                            w.println("\t\t\t\tcase " + s + ": state = " + stateOf(set) + "; break;");
                    }

                    @Override
                    public void visitReduction(LrItem item) {
                        w.println("\t\t\t\tdefault: return;");
                    }

                    @Override
                    public void visitAccept(LrItem item) {

                    }
                });
            });
            w.println("\t\t\t}");
        });
        w.println("\t\t}");
        return lrParserAutomata;
    }

    private foundation.rpg.lexer.pattern.Pattern patternOf(Element token) {
        foundation.rpg.Pattern pattern = token.getAnnotation(foundation.rpg.Pattern.class);
        if(nonNull(pattern)) {
            try {
                return patternParser.parse(pattern.value());
            } catch (IOException | ParseErrorException e) {
                throw new IllegalStateException(e);
            }
        }
        Name name = token.getAnnotation(Name.class);
        if(isNull(name))
            throw new IllegalArgumentException(token.getSimpleName().toString());
        return new Pattern(singletonList(new Option(name.value().chars().mapToObj(c -> new Char((char) c)).collect(toList()))));
    }

}
