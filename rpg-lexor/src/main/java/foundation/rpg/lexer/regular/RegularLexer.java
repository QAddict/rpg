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

package foundation.rpg.lexer.regular;

import foundation.rpg.common.*;
import foundation.rpg.parser.*;
import foundation.rpg.parser.Token;

import java.io.IOException;

public class RegularLexer implements Lexer<State> {

    @Override
    public Token<State> next(Input input) throws IOException {
        int i = input.lookahead();
        TokenBuilder mark = new TokenBuilder(input);
        input.move();
        switch (i) {
            case -1: return new TokenEnd(new End(mark.build()));
            case '\\': return new TokenBs(new Bs(mark.build()));
            case '|': return new TokenPipe(new Pipe(mark.build()));
            case '*': return new TokenTimes(new Times(mark.build()));
            case '+': return new TokenPlus(new Plus(mark.build()));
            case '~': return new TokenTilda(new Tilda(mark.build()));
            case '-': return new TokenMinus(new Minus(mark.build()));
            case '(': return new TokenLPar(new LPar(mark.build()));
            case ')': return new TokenRPar(new RPar(mark.build()));
            case '[': return new TokenLBr(new LBr(mark.build()));
            case ']': return new TokenRBr(new RBr(mark.build()));
            case '.': return new TokenDot(new Dot(mark.build()));
            default: return new TokenCharacter((char) i);
        }
    }

}
