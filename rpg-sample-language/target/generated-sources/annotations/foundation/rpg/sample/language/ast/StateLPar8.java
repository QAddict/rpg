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

LPar8: {
	AtomicExpression -> Identifier LPar • List3ListOfExpression RPar [RPar, Plus, Times, Gt]
	List3ListOfExpression -> • [RPar]
	List3ListOfExpression -> • List2ListOfExpression [RPar]
	List2ListOfExpression -> • Expression [RPar, Comma]
	List2ListOfExpression -> • List2ListOfExpression Comma Expression [RPar, Comma]
	Expression -> • RelationalExpression [RPar, Comma]
	RelationalExpression -> • AdditionalExpression [RPar, Gt, Comma]
	RelationalExpression -> • RelationalExpression Gt AdditionalExpression [RPar, Gt, Comma]
	AdditionalExpression -> • AdditionalExpression Plus MultiplicativeExpression [RPar, Plus, Gt, Comma]
	AdditionalExpression -> • MultiplicativeExpression [RPar, Plus, Gt, Comma]
	MultiplicativeExpression -> • MultiplicativeExpression Times AtomicExpression [RPar, Plus, Times, Gt, Comma]
	MultiplicativeExpression -> • AtomicExpression [RPar, Plus, Times, Gt, Comma]
	AtomicExpression -> • Identifier [RPar, Plus, Times, Gt, Comma]
	AtomicExpression -> • LPar Expression RPar [RPar, Plus, Times, Gt, Comma]
	AtomicExpression -> • Identifier LPar List3ListOfExpression RPar [RPar, Plus, Times, Gt, Comma]
}

*/

import foundation.rpg.parser.UnexpectedInputException;

// Generated visitor pattern based state for grammar parser.
public class StateLPar8 extends StackState<foundation.rpg.common.LPar, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State>> {

// NoStack:
// Stack:
    public StateLPar8(foundation.rpg.sample.language.ast.AstFactory factory, foundation.rpg.common.LPar node, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State> prev) {
        super(factory, node, prev);
    }


// Reduce:
    @Override
    public State visitRPar(foundation.rpg.common.RPar symbol) throws UnexpectedInputException {
        
        return this.visitList3ListOfExpression(foundation.rpg.common.ListRules.isList3()).visitRPar(symbol);
    }


// Shift:
    @Override
    public State visitList3ListOfExpression(java.util.List<foundation.rpg.sample.language.ast.Expression> symbol) {
        return new StateList3ListOfExpression2(getFactory(), symbol, this);
    }

    @Override
    public State visitList2ListOfExpression(java.util.List<foundation.rpg.sample.language.ast.Expression> symbol) {
        return new StateList2ListOfExpression1(getFactory(), symbol, this);
    }

    @Override
    public State visitExpression(foundation.rpg.sample.language.ast.Expression symbol) {
        return new StateExpression3(getFactory(), symbol, this);
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
