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

package foundation.rpg.sample.language.ast;

/*

Identifier1: {
	AtomicExpression -> Identifier • [Dot, Plus, Times, Gt]
	AtomicExpression -> Identifier • LPar List3ListOfExpression RPar [Dot, Plus, Times, Gt]
}

*/

import foundation.rpg.parser.UnexpectedInputException;

// Generated visitor pattern based state for grammar parser.
public class StateIdentifier1 extends StackState<foundation.rpg.sample.language.ast.Identifier, State> {

// NoStack:
// Stack:
    public StateIdentifier1(foundation.rpg.sample.language.ast.AstFactory factory, foundation.rpg.sample.language.ast.Identifier node, State prev) {
        super(factory, node, prev);
    }


// Reduce:
    @Override
    public State visitDot(foundation.rpg.common.Dot symbol) throws UnexpectedInputException {
        
		State stack1 = this.getPrev();
        return stack1.visitAtomicExpression(getFactory().is(this.getNode())).visitDot(symbol);
    }

    @Override
    public State visitPlus(foundation.rpg.common.Plus symbol) throws UnexpectedInputException {
        
		State stack1 = this.getPrev();
        return stack1.visitAtomicExpression(getFactory().is(this.getNode())).visitPlus(symbol);
    }

    @Override
    public State visitTimes(foundation.rpg.common.Times symbol) throws UnexpectedInputException {
        
		State stack1 = this.getPrev();
        return stack1.visitAtomicExpression(getFactory().is(this.getNode())).visitTimes(symbol);
    }

    @Override
    public State visitGt(foundation.rpg.common.Gt symbol) throws UnexpectedInputException {
        
		State stack1 = this.getPrev();
        return stack1.visitAtomicExpression(getFactory().is(this.getNode())).visitGt(symbol);
    }


// Shift:
    @Override
    public State visitLPar(foundation.rpg.common.LPar symbol) {
        return new StateLPar2(getFactory(), symbol, this);
    }


// Accept:
}
