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

package foundation.rpg.regular;

import foundation.rpg.gnfa.GNFA;
import foundation.rpg.gnfa.Thompson;

import java.util.stream.Stream;

import static java.util.stream.IntStream.range;
import static java.util.stream.Stream.of;

public class RegularExpressionParser {

    private final Thompson thompson = new Thompson();

    public GNFA parsePattern(String string) {
        return parse(new In(string));
    }

    public GNFA parseText(String text) {
        return thompson.string(text);
    }

    GNFA parse(In in) {
        GNFA left = parseChain(in);
        return in.testAndConsume('|') ? thompson.alternation(of(left, parse(in))) : left;
    }

    GNFA parseEsc(In in) {
        char c = in.consume("Unfinished escape sequence");
        switch (c) {
            case '.':
            case '[':
            case '(':
            case ')':
            case '|':
            case '*':
            case '+':
            case '?':
            case '\\': return thompson.transition(c);
            default: return thompson.group(c);
        }
    }

    GNFA parseChain(In in) {
        switch (in.get()) {
            case -1:
            case '|':
            case ')': return thompson.empty();
            default: return thompson.chain(of(parseFactor(parseInput(in), in), parseChain(in)));
        }
    }

    GNFA parseFactor(GNFA factor, In in) {
        if(in.testAndConsume('*'))
            return thompson.repetition(factor);
        if(in.testAndConsume('+'))
            return thompson.chain(of(factor, thompson.repetition(factor)));
        if(in.testAndConsume('?'))
            return thompson.optional(factor);
        return factor;
    }

    GNFA parseInput(In in) {
        int c = in.consume();
        switch (c) {
            case -1: return thompson.empty();
            case '\\': return parseEsc(in);
            case '.': return thompson.any();
            case '[': return parseChars(in);
            case '(':
                GNFA sub = parse(in);
                if(!in.testAndConsume(')')) throw new IllegalStateException("Missing )");
                return sub;
            case '*':
            case '?':
            case '+': throw new IllegalStateException("Illegal use of repeating operator: '" + (char) c + "'");
            default: return thompson.transition((char) c);
        }
    }

    GNFA parseChars(In in) {
        return in.testAndConsume('^') ? thompson.inversions(parseClass(in)) : thompson.transitions(parseClass(in));
    }

    Stream<Character> parseClass(In in) {
        Stream<Character> left = parseCharOrRange(in);
        return in.testAndConsume(']') ? left : Stream.concat(left, parseClass(in));
    }

    Stream<Character> parseCharOrRange(In in) {
        in.testAndConsume('\\');
        char c = in.consume("Unterminated character class");
        return in.testAndConsume('-') ? range(c, in.consume("Unterminated character range")).mapToObj(i -> (char) i) : of(c);
    }


}
