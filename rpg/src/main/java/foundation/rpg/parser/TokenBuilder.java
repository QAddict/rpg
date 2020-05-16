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

import java.io.IOException;

/**
 * Builder of the token description.
 */
public final class TokenBuilder {

    /**
     * Input, on top of which the token is being built.
     */
    private final Input input;

    /**
     * Start position in the input stream.
     */
    private final Position start;

    /**
     * Content builder.
     */
    private final StringBuilder contentBuilder = new StringBuilder();

    public TokenBuilder(Input input) {
        this.input = input;
        this.start = input.position();
    }

    TokenBuilder move() throws IOException {
        contentBuilder.append((char) input.lookahead());
        input.move();
        return this;
    }

    public int next() throws IOException {
        return move().input.lookahead();
    }

    public TokenDescription build() {
        Position end = input.position();
        return new TokenDescription(
                start.getFileName(),
                start.getLine(),
                start.getCharacter(),
                start.getTotal(),
                end.getLine(),
                end.getCharacter(),
                end.getTotal(),
                contentBuilder.toString()
        );
    }
}
