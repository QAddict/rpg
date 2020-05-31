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

import foundation.rpg.dfa.DFA;
import foundation.rpg.dfa.GNFATransformer;
import foundation.rpg.gnfa.GNFA;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class RegularExpressionParserTest {

    private final RegularExpressionParser parser = new RegularExpressionParser();
    private final GNFATransformer transformer = new GNFATransformer((g,c) -> false);

    @Test
    public void testParseText() {
        parser.parseText("abc");
        parser.parseText("");
    }

    @Test
    public void testParseAlternation() {
        GNFA gnfa = parser.parsePattern("a|b");
        DFA dfa = transformer.transform(gnfa);
        System.out.println(dfa);
    }

    @Test
    public void testParseAlternation2() {
        GNFA gnfa = parser.parsePattern("c(a|b)");
        DFA dfa = transformer.transform(gnfa);
        System.out.println(dfa);
    }

    @Test
    public void testParseOptional() {
        GNFA gnfa = parser.parsePattern("c?b");
        DFA dfa = transformer.transform(gnfa);
        System.out.println(dfa);
        assertEquals(dfa.getStart().getTransitions().get('b'), dfa.getStart().getTransitions().get('c').getTransitions().get('b'));
    }

    @Test
    public void testParseEsc() {
        assertTrue(transformer.transform(parser.parsePattern("\\*")).getStart().getTransitions().containsKey('*'));
    }

}
