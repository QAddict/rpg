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

package foundation.rpg.sample.language;

import foundation.rpg.common.symbols.*;
import foundation.rpg.parser.*;
import foundation.rpg.sample.language.ast.Identifier;
import foundation.rpg.sample.language.ast.Program;
import foundation.rpg.sample.language.ast.*;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LanguageParserTest {

    @Test
    public void testParser() throws ParseErrorException, IOException {
        TokenInputParser<Program, State> parser = new TokenInputParser<>(new State1(new AstFactory()));
        Token p = new Token("", 0, 0, 0, 0, 0, 0, "");
        TokenInput<State> lexer = mock(TokenInput.class);
        when(lexer.next()).thenReturn(
                new ElementComment(new Comment(p)),
                new ElementLPar(new LPar(p)),
                new ElementWhiteSpace(new WhiteSpace(p)),
                new ElementIdentifier(new Identifier("variable")),
                new ElementRPar(new RPar(p)),
                new ElementDot(new Dot(p)),
                new ElementEnd(new End(p))
        );
        Program program = parser.parse(lexer);
        System.out.println(program);
    }

}
