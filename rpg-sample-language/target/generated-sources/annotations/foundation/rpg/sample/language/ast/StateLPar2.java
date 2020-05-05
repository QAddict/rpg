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

LPar2: {
	Expression -> Identifier LPar • NOfListOfExpression RPar [Dot, Plus]
	NOfListOfExpression -> • [RPar]
	NOfListOfExpression -> • ListOfExpression [RPar]
	ListOfExpression -> • Expression [RPar, Comma]
	ListOfExpression -> • ListOfExpression Comma Expression [RPar, Comma]
	Expression -> • Expression Plus Expression [RPar, Plus, Comma]
	Expression -> • Identifier [RPar, Plus, Comma]
	Expression -> • LPar Expression RPar [RPar, Plus, Comma]
	Expression -> • Identifier LPar NOfListOfExpression RPar [RPar, Plus, Comma]
}

*/

import foundation.rpg.parser.UnexpectedInputException;
import javax.annotation.Generated;

@Generated("Generated visitor pattern based state for grammar parser.")
public class StateLPar2 extends StackState<foundation.rpg.common.LPar, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State>> {
    // Stack:
    public StateLPar2(foundation.rpg.common.LPar node, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State> prev) {
        super(node, prev);
    }


    // Reduce:
    @Override
    public State visitRPar(foundation.rpg.common.RPar symbol) throws UnexpectedInputException {
        
        return this.visitNOfListOfExpression(foundation.rpg.common.ListRules.isList3()).visitRPar(symbol);
    }


    // Shift:
    @Override
    public State visitNOfListOfExpression(foundation.rpg.common.N<java.util.List<foundation.rpg.sample.language.ast.Expression>> symbol) {
        return new StateNOfListOfExpression1(symbol, this);
    }

    @Override
    public State visitIdentifier(foundation.rpg.sample.language.ast.Identifier symbol) {
        return new StateIdentifier4(symbol, this);
    }

    @Override
    public State visitExpression(foundation.rpg.sample.language.ast.Expression symbol) {
        return new StateExpression4(symbol, this);
    }

    @Override
    public State visitLPar(foundation.rpg.common.LPar symbol) {
        return new StateLPar5(symbol, this);
    }

    @Override
    public State visitListOfExpression(java.util.List<foundation.rpg.sample.language.ast.Expression> symbol) {
        return new StateListOfExpression1(symbol, this);
    }


    // Accept:
}
