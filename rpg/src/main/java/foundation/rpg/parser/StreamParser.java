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

package foundation.rpg.parser;

import java.io.*;

public class StreamParser<R, S extends StateBase<R>> {

    private final TokenInputParser<R, S> parser;
    private final Lexer<S> lexer;

    public StreamParser(S initialState, Lexer<S> lexer) {
        parser = new TokenInputParser<>(initialState);
        this.lexer = lexer;
    }

    public R parse(TokenInput<S> input) throws ParseErrorException {
        return parser.parse(input);
    }

    public R parse(String name, Reader reader) throws IOException, ParseErrorException {
        return parse(TokenInput.tokenInput(new Input(name, reader), lexer));
    }

    public R parseFile(String fileName) throws IOException, ParseErrorException {
        return parse(fileName, new FileReader(fileName));
    }

    public R parseString(String content) throws IOException, ParseErrorException {
        return parse("string", new StringReader(content));
    }

    public R parse(String name, InputStream stream) throws IOException, ParseErrorException {
        return parse(name, new InputStreamReader(stream));
    }

}
