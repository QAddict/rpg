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

import foundation.rpg.lexer.regular.RegularGenerator;
import foundation.rpg.lexer.regular.ast.Node;
import foundation.rpg.lexer.regular.dfa.DFA;
import foundation.rpg.lexer.regular.dfa.GNFATransformer;
import foundation.rpg.lexer.regular.thompson.GNFA;
import foundation.rpg.lexer.regular.thompson.ThompsonVisitor;

import javax.lang.model.type.TypeMirror;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparingInt;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

public class LexerGenerator {

    private final ThompsonVisitor visitor = new ThompsonVisitor();
    private final GNFATransformer transformer = new GNFATransformer();
    private final RegularGenerator generator = new RegularGenerator();

    public static class TokenInfo {
        private final Object element;
        private final String call;
        private final Node pattern;
        private final int priority;

        public TokenInfo(Object element, String call, Node pattern, int priority) {
            this.element = element;
            this.call = call;
            this.pattern = pattern;
            this.priority = priority;
        }

        public Object getElement() {
            return element;
        }

        public Node getPattern() {
            return pattern;
        }

        public int getPriority() {
            return priority;
        }
    }

    public void generateLexer(String pkg, String name, List<TokenInfo> info, PrintWriter w, TypeMirror factoryType) {
        Map<Object, TokenInfo> infoMap = info.stream().collect(toMap(TokenInfo::getPattern, identity()));
        GNFA gnfa = visitor.visit(info.stream().map(TokenInfo::getPattern).collect(toList()));
        DFA dfa = transformer.transform(gnfa);
        Comparator<TokenInfo> comparator = comparingInt(TokenInfo::getPriority);
        generator.generate(pkg, name, dfa, w, set -> set.stream().map(infoMap::get).max(comparator).orElseThrow(() -> new IllegalArgumentException("")).call, factoryType);
    }

}
