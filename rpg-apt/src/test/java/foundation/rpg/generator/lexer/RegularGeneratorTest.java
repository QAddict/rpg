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

import foundation.rpg.gnfa.Thompson;
import foundation.rpg.regular.RegularExpressionParser;
import foundation.rpg.dfa.DFA;
import foundation.rpg.dfa.GNFATransformer;
import foundation.rpg.gnfa.GNFA;
import org.testng.annotations.Test;

import java.io.PrintWriter;
import java.util.*;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyMap;
import static java.util.Collections.max;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toMap;

public class RegularGeneratorTest {

    RegularExpressionParser parser = new RegularExpressionParser();
    RegularGenerator generator = new RegularGenerator();

    @Test
    public void testGnfaFrom() {
        List<GNFA> nodes = asList(
                parser.parseText("else"),
                parser.parseText("extends"),
                parser.parsePattern("\\w\\a*"),
                parser.parsePattern("'([^'\\\\]|\\\\['\\\\rnt])*'|\"([^\"\\\\]|\\\\[\"\\\\rnt])*\"")
        );
        Map<Object, Integer> priorities = IntStream.range(0, nodes.size()).boxed().collect(toMap(nodes::get, i -> nodes.size() - i));
        Thompson thompson = new Thompson();
        GNFA gnfa = thompson.alternation(nodes.stream());
        System.out.println(gnfa);
        DFA dfa = new GNFATransformer(new RegularTypes()).transform(gnfa);
        System.out.println(dfa);
        Comparator<Object> comparator = comparingInt(priorities::get);
        generator.generate("pkg", "MyLexer", dfa, emptyMap(), new PrintWriter(System.out), set -> max(set, comparator).toString(), null);
    }

}
