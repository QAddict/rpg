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

package foundation.rpg.sample.json;

import foundation.rpg.common.*;
import foundation.rpg.common.Class;
import foundation.rpg.common.Package;
import foundation.rpg.parser.*;
import foundation.rpg.parser.Token;

import java.io.IOException;

public class JsonLexer extends KeywordLexerBase<State> implements Lexer<State> {

    public JsonLexer() {
        super(new LiteralFactory<State>() {
            @Override public Token<State> newIdentifier(Position position, String name) {
                return new TokenString(name);
            }
            @Override public Token<State> newString(Position position, String value) {
                return new TokenString(value);
            }
            @Override public Token<State> newDouble(Position position, String value) {
                return new TokenDouble(Double.parseDouble(value));
            }
            @Override public Token<State> newInteger(Position position, String value) {
                return new TokenInteger(Integer.parseInt(value));
            }
            @Override public Token<State> newWhitespace(Position position, String value) {
                return new TokenWhiteSpace(new WhiteSpace(position));
            }
        });
    }

    @Override
    public Token<State> next(Input input) throws ParseErrorException, IOException {
        Position mark = input.position();
        if(input.lookahead() < 0) return new TokenEnd(new End(mark));
        char lookahead = (char) input.lookahead();
        input.move();
        switch (lookahead) {
            case ',': return new TokenComma(new Comma(mark));
            case '{': return new TokenLCurl(new LCurl(mark));
            case '}': return new TokenRCurl(new RCurl(mark));
            case '[': return new TokenLBr(new LBr(mark));
            case ']': return new TokenRBr(new RBr(mark));
            case ':': return new TokenColon(new Colon(mark));
            case '"': return string(mark, input);
            default: return groups(mark, lookahead, input);
        }
    }




}
