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

package foundation.fluent.jast.sample.lexer;

import foundation.fluent.jast.parser.AbstractLexer;
import foundation.fluent.jast.parser.ParseErrorException;
import foundation.fluent.jast.sample.states.StateVisitor;
import foundation.fluent.jast.sample.tokens.Token;

import java.io.Reader;

import static foundation.fluent.jast.sample.lexer.SimpleLexer.State.*;
import static java.lang.Character.*;

public class SimpleLexer extends AbstractLexer<StateVisitor> {

    public SimpleLexer(Reader reader) throws ParseErrorException {
        super("", reader);
    }

    enum State {START, LPAR, RPAR, PLUS, IDENT, END}

    @Override
    public Token next() throws ParseErrorException {
        State state = State.START;
        StringBuilder builder = new StringBuilder();
        do {
            switch(state) {
                case START:
                    switch (look) {
                        case -1: state = END; break;
                        case '(': state = LPAR; break;
                        case ')': state = RPAR; break;
                        case '+': state = PLUS; break;
                        default:
                            if(isJavaIdentifierStart(look)) {
                                state = IDENT;
                                break;
                            }
                            if(!isWhitespace(look)) {
                                throw new IllegalStateException("Unexpected character: " + (char) look);
                            }
                    }
                    break;
                case END: return Token.END;
                case LPAR: return Token.LPAR;
                case RPAR: return Token.RPAR;
                case PLUS: return Token.PLUS;
                case IDENT:
                    if(!isJavaIdentifierPart(look))
                        return Token.ident(builder.toString(), 0, 0);
            }
            builder.append((char) look);
            move();
        } while (true);
    }
}
