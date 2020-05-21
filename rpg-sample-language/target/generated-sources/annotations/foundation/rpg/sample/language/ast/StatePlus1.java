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

Plus1: {
	P1Expression -> P1Expression Plus • P2Expression$ [Dot, Plus]
	P2Expression$ -> • Expression$$ [Dot, Plus, Times]
	P2Expression$ -> • P2Expression$ Times Expression$$ [Dot, Plus, Times]
	Expression$$ -> • Identifier [Dot, Plus, Times]
	Expression$$ -> • LPar Expression$$ RPar [Dot, Plus, Times]
	Expression$$ -> • Identifier LPar List3ListOfExpression RPar [Dot, Plus, Times]
}

*/

import foundation.rpg.parser.UnexpectedInputException;

// Generated visitor pattern based state for grammar parser.
public class StatePlus1 extends StackState<foundation.rpg.common.Plus, StackState<foundation.rpg.sample.language.ast.Expression, ? extends State>> {
// Stack:
    public StatePlus1(foundation.rpg.common.Plus node, StackState<foundation.rpg.sample.language.ast.Expression, ? extends State> prev) {
        super(node, prev);
    }


// Reduce:
// Shift:
    @Override
    public State visitP2Expression$(foundation.rpg.sample.language.ast.Expression symbol) {
        return new StateP2Expression$2(symbol, this);
    }

    @Override
    public State visitExpression$$(foundation.rpg.sample.language.ast.Expression symbol) {
        return new StateExpression$$1(symbol, this);
    }

    @Override
    public State visitIdentifier(foundation.rpg.sample.language.ast.Identifier symbol) {
        return new StateIdentifier1(symbol, this);
    }

    @Override
    public State visitLPar(foundation.rpg.common.LPar symbol) {
        return new StateLPar1(symbol, this);
    }


// Accept:
}
