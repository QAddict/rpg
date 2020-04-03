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

package foundation.fluent.jast.parser;

import java.io.IOException;

import static java.lang.Character.*;

public class KeywordLexerBase<S> {

    private final LiteralFactory<S> literalFactory;

    public KeywordLexerBase(LiteralFactory<S> literalFactory) {
        this.literalFactory = literalFactory;
    }

    public Token<S> kwOrId(Position mark, Input input, Token<S> token) throws IOException {
        String s = token.toString();
        for(int i = input.position().getTotal() - mark.getTotal(); i < s.length(); i++, input.move()) {
            if(input.lookahead() != s.charAt(i)) return id(mark, s.substring(0, i), input);
        }
        if(isJavaIdentifierPart(input.lookahead()))
            return id(mark, s, input);
        return token;
    }

    public Token<S> id(Position mark, String prefix, Input input) throws IOException {
        StringBuilder b = new StringBuilder(prefix);
        while (isJavaIdentifierPart(input.lookahead())) {
            b.append((char) input.lookahead());
            input.move();
        }
        return literalFactory.newIdentifier(mark, b.toString());
    }

    public Token<S> string(Position mark, Input input) throws IOException {
        StringBuilder b = new StringBuilder();
        while(true) {
            switch (input.lookahead()) {
                case '"': input.move(); return literalFactory.newString(mark, b.toString());
                case -1: throw new IOException("Unexpected end of input. Expected: <\">");
                case '\\': input.move();
                default: b.append((char)input.lookahead());
            }
            input.move();
        }
    }

    public Token<S> groups(Position mark, char lookahead, Input input) throws IOException, ParseErrorException {
        if(isDigit(lookahead)) {
            StringBuilder b = new StringBuilder().append(lookahead);
            while(isDigit(input.move().lookahead())) b.append(lookahead);
            switch (input.lookahead()) {
                case '.':
                case 'e':
                case 'E':
                    b.append(input.lookahead());
                    while (isDigit(input.move().lookahead())) b.append(lookahead);
                    return literalFactory.newDouble(mark, b.toString());
                default: return literalFactory.newInteger(mark, b.toString());
            }
        }
        if(isJavaIdentifierStart(lookahead))
            return id(mark, "" + lookahead, input);
        if(isWhitespace(lookahead)) {
            StringBuilder b = new StringBuilder().append(lookahead);
            while (isWhitespace(input.lookahead())) {
                b.append(input.lookahead());
                input.move();
            }
            return literalFactory.newWhitespace(mark, b.toString());
        }
        throw new ParseErrorException(input.position(), "Illegal input character: " + lookahead);
    }


    public interface LiteralFactory<S> {

        Token<S> newIdentifier(Position position, String name);
        Token<S> newString(Position position, String value);
        Token<S> newDouble(Position position, String value);
        Token<S> newInteger(Position position, String value);
        Token<S> newWhitespace(Position position, String value);

    }

}
