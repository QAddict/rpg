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

Comma1: {
	List2ListOfExpression -> List2ListOfExpression Comma • Expression [RPar, Comma]
	Expression -> • RelationalExpression [RPar, Comma]
	RelationalExpression -> • AdditionalExpression [RPar, Comma, Gt]
	RelationalExpression -> • RelationalExpression Gt AdditionalExpression [RPar, Comma, Gt]
	AdditionalExpression -> • AdditionalExpression Plus MultiplicativeExpression [RPar, Comma, Plus, Gt]
	AdditionalExpression -> • MultiplicativeExpression [RPar, Comma, Plus, Gt]
	MultiplicativeExpression -> • MultiplicativeExpression Times AtomicExpression [RPar, Comma, Plus, Times, Gt]
	MultiplicativeExpression -> • AtomicExpression [RPar, Comma, Plus, Times, Gt]
	AtomicExpression -> • Identifier [RPar, Comma, Plus, Times, Gt]
	AtomicExpression -> • LPar Expression RPar [RPar, Comma, Plus, Times, Gt]
	AtomicExpression -> • Identifier LPar List3ListOfExpression RPar [RPar, Comma, Plus, Times, Gt]
}

*/

import foundation.rpg.parser.UnexpectedInputException;

// Generated visitor pattern based state for grammar parser.
public class StateComma1 extends StackState<foundation.rpg.common.Comma, StackState<java.util.List<foundation.rpg.sample.language.ast.Expression>, ? extends State>> {

// NoStack:
// Stack:
    public StateComma1(foundation.rpg.sample.language.ast.AstFactory factory, foundation.rpg.common.Comma node, StackState<java.util.List<foundation.rpg.sample.language.ast.Expression>, ? extends State> prev) {
        super(factory, node, prev);
    }


// Reduce:
// Shift:
    @Override
    public State visitExpression(foundation.rpg.sample.language.ast.Expression symbol) {
        return new StateExpression7(getFactory(), symbol, this);
    }

    @Override
    public State visitRelationalExpression(foundation.rpg.sample.language.ast.Expression symbol) {
        return new StateRelationalExpression3(getFactory(), symbol, this);
    }

    @Override
    public State visitAdditionalExpression(foundation.rpg.sample.language.ast.Expression symbol) {
        return new StateAdditionalExpression4(getFactory(), symbol, this);
    }

    @Override
    public State visitMultiplicativeExpression(foundation.rpg.sample.language.ast.Expression symbol) {
        return new StateMultiplicativeExpression5(getFactory(), symbol, this);
    }

    @Override
    public State visitAtomicExpression(foundation.rpg.sample.language.ast.Expression symbol) {
        return new StateAtomicExpression6(getFactory(), symbol, this);
    }

    @Override
    public State visitIdentifier(foundation.rpg.sample.language.ast.Identifier symbol) {
        return new StateIdentifier6(getFactory(), symbol, this);
    }

    @Override
    public State visitLPar(foundation.rpg.common.LPar symbol) {
        return new StateLPar7(getFactory(), symbol, this);
    }


// Accept:
}
