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

import foundation.fluent.jast.sample.tokens.Token;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

import static foundation.fluent.jast.sample.lexer.Lexer.State.*;
import static java.lang.Character.*;

public class Lexer implements Iterator<Token> {

    private final Reader reader;
    int lookahead;
    int line = 0;
    int pos = 0;

    public Lexer(Reader reader) {
        this.reader = reader;
        move();
    }

    private void move() {
        try {
            if(lookahead == '\n') {
                line++;
                pos = 0;
            } else {
                pos++;
            }
            lookahead = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    enum State {START, LPAR, RPAR, PLUS, IDENT, END}

    @Override
    public Token next() {
        State state = State.START;
        StringBuilder builder = new StringBuilder();
        int line = this.line;
        int pos = this.pos;
        do {
            switch(state) {
                case START:
                    switch (lookahead) {
                        case -1: state = END; break;
                        case '(': state = LPAR; break;
                        case ')': state = RPAR; break;
                        case '+': state = PLUS; break;
                        default:
                            if(isJavaIdentifierStart(lookahead)) {
                                state = IDENT;
                                break;
                            }
                            if(!isWhitespace(lookahead)) {
                                throw new IllegalStateException("Unexpected character: " + (char) lookahead);
                            }
                    }
                    break;
                case END: return Token.END;
                case LPAR: return Token.LPAR;
                case RPAR: return Token.RPAR;
                case PLUS: return Token.PLUS;
                case IDENT:
                    if(!isJavaIdentifierPart(lookahead))
                        return Token.ident(builder.toString(), line, pos);
            }
            builder.append((char) lookahead);
            move();
        } while (true);
    }
}
