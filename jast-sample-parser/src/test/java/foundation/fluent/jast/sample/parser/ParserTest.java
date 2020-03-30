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

package foundation.fluent.jast.sample.parser;

import foundation.fluent.jast.parser.Lexer;
import foundation.fluent.jast.parser.ParseErrorException;
import foundation.fluent.jast.sample.ast.Expression;
import foundation.fluent.jast.sample.lexer.SimpleLexer;
import foundation.fluent.jast.sample.states.InitialState;
import foundation.fluent.jast.sample.states.StateVisitor;
import org.testng.annotations.Test;
import foundation.fluent.jast.parser.Parser;

import java.io.StringReader;

import static foundation.fluent.jast.sample.tokens.Token.*;
import static java.util.Arrays.asList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ParserTest {

    @Test
    public void testParse() throws ParseErrorException {
        Lexer<StateVisitor> lexer = mock(Lexer.class);
        when(lexer.next()).thenReturn(
                LPAR,
                LPAR,
                IDENT,
                RPAR,
                PLUS,
                IDENT,
                PLUS,
                LPAR,
                IDENT,
                PLUS,
                IDENT,
                RPAR,
                RPAR,
                END
        );
        Expression expression = new Parser<StateVisitor>(new InitialState()).parse(lexer).result();
        System.out.println(expression);
    }

    @Test
    public void testParserAndLexer() throws ParseErrorException {
        SimpleLexer lexer = new SimpleLexer(new StringReader("(abcd + efg) + gfds"));
        Parser<StateVisitor> parser = new Parser<>(new InitialState());
        StateVisitor stateVisitor = parser.parse(lexer);
        Expression expression = stateVisitor.result();
        System.out.println(expression);
    }

}
