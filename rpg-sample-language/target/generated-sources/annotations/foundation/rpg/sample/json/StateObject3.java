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

Object3: {
	ListOfObject -> ListOfObject Object • [RBr, String, Integer, Double, LBr, LCurl]
}

*/

import foundation.rpg.parser.UnexpectedInputException;
import javax.annotation.Generated;

@Generated("Generated visitor pattern based state for grammar parser.")
public class StateObject3 extends StackState<java.lang.Object, StackState<java.util.List<java.lang.Object>, ? extends State>> {
// Stack:
    public StateObject3(java.lang.Object node, StackState<java.util.List<java.lang.Object>, ? extends State> prev) {
        super(node, prev);
    }


// Reduce:
    @Override
    public State visitRBr(foundation.rpg.common.RBr symbol) throws UnexpectedInputException {
        
		StackState<java.util.List<java.lang.Object>, ? extends State> stack1 = this.getPrev();
		State stack2 = stack1.getPrev();
        return stack2.visitListOfObject(foundation.rpg.sample.json.JsonFactory.is(stack1.getNode(), this.getNode())).visitRBr(symbol);
    }

    @Override
    public State visitString(java.lang.String symbol) throws UnexpectedInputException {
        
		StackState<java.util.List<java.lang.Object>, ? extends State> stack1 = this.getPrev();
		State stack2 = stack1.getPrev();
        return stack2.visitListOfObject(foundation.rpg.sample.json.JsonFactory.is(stack1.getNode(), this.getNode())).visitString(symbol);
    }

    @Override
    public State visitInteger(java.lang.Integer symbol) throws UnexpectedInputException {
        
		StackState<java.util.List<java.lang.Object>, ? extends State> stack1 = this.getPrev();
		State stack2 = stack1.getPrev();
        return stack2.visitListOfObject(foundation.rpg.sample.json.JsonFactory.is(stack1.getNode(), this.getNode())).visitInteger(symbol);
    }

    @Override
    public State visitDouble(java.lang.Double symbol) throws UnexpectedInputException {
        
		StackState<java.util.List<java.lang.Object>, ? extends State> stack1 = this.getPrev();
		State stack2 = stack1.getPrev();
        return stack2.visitListOfObject(foundation.rpg.sample.json.JsonFactory.is(stack1.getNode(), this.getNode())).visitDouble(symbol);
    }

    @Override
    public State visitLBr(foundation.rpg.common.LBr symbol) throws UnexpectedInputException {
        
		StackState<java.util.List<java.lang.Object>, ? extends State> stack1 = this.getPrev();
		State stack2 = stack1.getPrev();
        return stack2.visitListOfObject(foundation.rpg.sample.json.JsonFactory.is(stack1.getNode(), this.getNode())).visitLBr(symbol);
    }

    @Override
    public State visitLCurl(foundation.rpg.common.LCurl symbol) throws UnexpectedInputException {
        
		StackState<java.util.List<java.lang.Object>, ? extends State> stack1 = this.getPrev();
		State stack2 = stack1.getPrev();
        return stack2.visitListOfObject(foundation.rpg.sample.json.JsonFactory.is(stack1.getNode(), this.getNode())).visitLCurl(symbol);
    }


// Shift:
// Accept:
}
