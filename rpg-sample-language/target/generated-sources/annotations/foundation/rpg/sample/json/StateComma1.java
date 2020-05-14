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

Comma1: {
	ListOfObject -> ListOfObject Comma • Object [RBr, Comma]
	Object -> • String [RBr, Comma]
	Object -> • Integer [RBr, Comma]
	Object -> • Double [RBr, Comma]
	Object -> • LBr ListOfObject RBr [RBr, Comma]
	Object -> • LBr RBr [RBr, Comma]
	Object -> • LCurl MapOfObject RCurl [RBr, Comma]
	Object -> • LCurl RCurl [RBr, Comma]
}

*/

import foundation.rpg.parser.UnexpectedInputException;

// Generated visitor pattern based state for grammar parser.
public class StateComma1 extends StackState<foundation.rpg.common.Comma, StackState<java.util.List<java.lang.Object>, ? extends State>> {

// NoStack:
// Stack:
    public StateComma1(foundation.rpg.sample.json.JsonFactory factory, foundation.rpg.common.Comma node, StackState<java.util.List<java.lang.Object>, ? extends State> prev) {
        super(factory, node, prev);
    }


// Reduce:
// Shift:
    @Override
    public State visitObject(java.lang.Object symbol) {
        return new StateObject4(getFactory(), symbol, this);
    }

    @Override
    public State visitString(java.lang.String symbol) {
        return new StateString2(getFactory(), symbol, this);
    }

    @Override
    public State visitInteger(java.lang.Integer symbol) {
        return new StateInteger2(getFactory(), symbol, this);
    }

    @Override
    public State visitDouble(java.lang.Double symbol) {
        return new StateDouble2(getFactory(), symbol, this);
    }

    @Override
    public State visitLBr(foundation.rpg.common.LBr symbol) {
        return new StateLBr2(getFactory(), symbol, this);
    }

    @Override
    public State visitLCurl(foundation.rpg.common.LCurl symbol) {
        return new StateLCurl2(getFactory(), symbol, this);
    }


// Accept:
}
