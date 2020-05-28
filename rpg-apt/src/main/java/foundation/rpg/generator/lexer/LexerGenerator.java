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

import foundation.rpg.gnfa.State;
import foundation.rpg.gnfa.Thompson;
import foundation.rpg.dfa.DFA;
import foundation.rpg.dfa.GNFATransformer;
import foundation.rpg.gnfa.GNFA;

import javax.lang.model.type.TypeMirror;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparingInt;

public class LexerGenerator {

    private final GNFATransformer transformer = new GNFATransformer(new RegularTypes());
    private final RegularGenerator generator = new RegularGenerator();

    public static class TokenInfo {
        private final Object element;
        private final String call;
        private final GNFA pattern;
        private final int priority;

        public TokenInfo(Object element, String call, GNFA pattern, int priority) {
            this.element = element;
            this.call = call;
            this.pattern = pattern;
            this.priority = priority;
        }

        public Object getElement() {
            return element;
        }

        public GNFA getPattern() {
            return pattern;
        }

        public int getPriority() {
            return priority;
        }
    }

    public void generateLexer(String pkg, String name, List<TokenInfo> info, PrintWriter w, TypeMirror factoryType) {
        Map<State, TokenInfo> finalStates = new HashMap<>();
        GNFA gnfa = new Thompson().alternation(info.stream().map(i -> {
            finalStates.put(i.getPattern().getEnd(), i);
            return i.getPattern();
        }));
        DFA dfa = transformer.transform(gnfa);
        Comparator<TokenInfo> comparator = comparingInt(TokenInfo::getPriority);
        generator.generate(pkg, name, dfa, finalStates, w, set -> set.stream().max(comparator).orElseThrow(() -> new IllegalArgumentException("")).call, factoryType);
    }

}
