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

@FunctionalInterface
public interface Lexer<S> {
    Element<S> next(Input input) throws ParseErrorException, IOException;

    static boolean matchesGroup(char group, int c) {
        switch (group) {
            case '.': return true;
            case 'w': return Character.isJavaIdentifierPart(c);
            case 'd': return Character.isDigit(c);
            case 's': return Character.isWhitespace(c);
            case 'i': return Character.isJavaIdentifierStart(c);
            case 'u': return Character.isUnicodeIdentifierStart(c);
            case 'x': return Character.isUnicodeIdentifierPart(c);
            case 'W': return !Character.isJavaIdentifierPart(c);
            case 'D': return !Character.isDigit(c);
            case 'S': return !Character.isWhitespace(c);
            case 'I': return !Character.isJavaIdentifierStart(c);
            case 'U': return !Character.isUnicodeIdentifierStart(c);
            case 'X': return !Character.isUnicodeIdentifierPart(c);
            default: return false;
        }
    }

}
