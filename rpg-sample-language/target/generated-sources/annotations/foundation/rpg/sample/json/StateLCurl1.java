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

/*

LCurl1: {
	Object -> LCurl • MapOfObject RCurl [End]
	Object -> LCurl • RCurl [End]
	MapOfObject -> • String Colon Object [RCurl, Comma]
	MapOfObject -> • MapOfObject Comma String Colon Object [RCurl, Comma]
}

*/

import foundation.rpg.common.symbols.LCurl;
import foundation.rpg.common.symbols.RCurl;

// Generated visitor pattern based state for grammar parser.
public class StateLCurl1 extends StackState<LCurl, State> {

// NoStack:
// Stack:
    public StateLCurl1(foundation.rpg.sample.json.JsonFactory factory, LCurl node, State prev) {
        super(factory, node, prev);
    }


// Reduce:
// Shift:
    @Override
    public State visitMapOfObject(java.util.Map<java.lang.String,java.lang.Object> symbol) {
        return new StateMapOfObject1(getFactory(), symbol, this);
    }

    @Override
    public State visitRCurl(RCurl symbol) {
        return new StateRCurl1(getFactory(), symbol, this);
    }

    @Override
    public State visitString(java.lang.String symbol) {
        return new StateString3(getFactory(), symbol, this);
    }


// Accept:
}
