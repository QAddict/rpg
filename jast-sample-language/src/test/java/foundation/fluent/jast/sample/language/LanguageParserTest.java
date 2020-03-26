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

package foundation.fluent.jast.sample.language;

import foundation.fluent.jast.sample.language.ast.*;
import foundation.fluent.jast.common.*;
import foundation.fluent.jast.parser.Parser;
import foundation.fluent.jast.sample.language.ast.Identifier;
import foundation.fluent.jast.sample.language.ast.Program;
import org.testng.annotations.Test;

import static java.util.Arrays.asList;

public class LanguageParserTest {

    @Test
    public void testParser() {
        Parser<State> parser = new Parser<>(new State1());
        Program program = parser.parse(asList(
                new TokenComment(new Comment()),
                new TokenLPar(LPar.SYMBOL),
                new TokenWhiteSpace(new WhiteSpace()),
                new TokenIdentifier(new Identifier("variable")),
                new TokenRPar(RPar.SYMBOL),
                new TokenDot(Dot.SYMBOL),
                new TokenEnd(End.SYMBOL)
        ).iterator()).result();
        System.out.println(program);
    }

}
