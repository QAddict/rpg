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

package foundation.rpg.lr1;

import foundation.rpg.grammar.Grammar;
import org.testng.annotations.Test;

import static foundation.rpg.lr1.Symbols.*;
import static foundation.rpg.grammar.Rule.setOf;
import static foundation.rpg.grammar.Rule.rule;
import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toSet;

public class LrParserConstructorTest {

    private final Grammar grammar = Grammar.grammar(S, setOf(
            rule(S).to(A, ε),
            rule(A).to(a, A)
    ), emptySet());

    @Test
    public void testParser() {
        LrParserAutomata parser = LrParserConstructor.generateParser(grammar);
        System.out.println(parser);
    }

    @Test
    public void testClosure() {
        LrItemSet closure = new LrParserConstructor(grammar).closure(a, grammar.rulesFor(A).stream().map(r -> LrItem.lrItem(r, setOf(ε))).collect(toSet()));
        System.out.println(closure);
    }

}