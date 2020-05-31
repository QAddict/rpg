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

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.StateBase;

// Generated visitor pattern based state for grammar parser.
public class State extends StateBase<java.lang.Object> {
    private final foundation.rpg.sample.json.JsonFactory factory;

    public State(foundation.rpg.sample.json.JsonFactory factory) {
        this.factory = factory;
    }

    public foundation.rpg.sample.json.JsonFactory getFactory() {
        return factory;
    }

// Ignored:
    public State visitWhiteSpace(foundation.rpg.common.symbols.WhiteSpace symbol) {
        return this;
    }


// Symbols:
    public State visitEnd(foundation.rpg.parser.End symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitString(java.lang.String symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitInteger(java.lang.Integer symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitDouble(java.lang.Double symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitLBr(foundation.rpg.common.symbols.LBr symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitRBr(foundation.rpg.common.symbols.RBr symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitLCurl(foundation.rpg.common.symbols.LCurl symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitRCurl(foundation.rpg.common.symbols.RCurl symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitComma(foundation.rpg.common.symbols.Comma symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitColon(foundation.rpg.common.symbols.Colon symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitObject(java.lang.Object symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitListOfObject(java.util.List<java.lang.Object> symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitMapOfObject(java.util.Map<java.lang.String,java.lang.Object> symbol) throws UnexpectedInputException {
        return error(symbol);
    }


}
