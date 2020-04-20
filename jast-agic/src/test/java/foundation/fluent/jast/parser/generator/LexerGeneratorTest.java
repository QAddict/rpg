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

package foundation.fluent.jast.parser.generator;

import foundation.fluent.jast.grammar.Grammar;
import foundation.fluent.jast.grammar.Symbol;
import foundation.fluent.jast.automata.LrParserConstructor;
import foundation.fluent.jast.automata.LrParserAutomata;
import org.testng.annotations.Test;

import static foundation.fluent.jast.parser.generator.LexerGeneratorTest.Ch.*;
import static foundation.fluent.jast.parser.generator.LexerGeneratorTest.T.*;
import static foundation.fluent.jast.parser.generator.RuleBuilder.of;
import static foundation.fluent.jast.parser.generator.RuleBuilder.rule;
import static java.util.Collections.emptySet;

public class LexerGeneratorTest {


    public enum Ch implements Symbol {
        t,h,i,s,eq,dot,lp,rp
    }

    public enum Kw implements Symbol {
        THIS
    }

    public enum T implements Symbol {
        IDENT, IDEN_CONT, Sa, S
    }

    public enum C implements Symbol {
        AL, ALNUM
    }

    public enum Op implements Symbol {
        EQ, DOT, LP, RP
    }

    @Test
    public void testLexer() {
        Grammar grammar = Grammar.grammar(S, of(
                rule(S).priority(-1).to(T.IDENT),
                rule(S).priority(-1).to(Kw.THIS),
                rule(S).priority(-1).to(Op.EQ),
                rule(Op.EQ).priority(-1).to(eq),
                rule(T.IDENT).priority(-2).to(C.AL, T.IDEN_CONT),
                rule(T.IDENT).priority(-2).to(t, T.IDEN_CONT),
                rule(T.IDEN_CONT).priority(-2).to(C.ALNUM, T.IDEN_CONT),
                rule(T.IDEN_CONT).priority(-2).to(h, T.IDEN_CONT),
                rule(T.IDEN_CONT).priority(-2).to(i, T.IDEN_CONT),
                rule(T.IDEN_CONT).priority(-2).to(s, T.IDEN_CONT),
                rule(Kw.THIS).priority(-1).to(t,h,i,s)
        ), emptySet());
        LrParserAutomata parser = new LrParserConstructor(grammar).constructAutomata();
        System.out.println(parser);
    }

}
