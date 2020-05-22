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

List3ListOfExpression1: {
	AtomicExpression -> Identifier LPar List3ListOfExpression • RPar [Dot, Gt, Plus, Times]
}

*/

import foundation.rpg.common.symbols.LPar;
import foundation.rpg.common.symbols.RPar;

// Generated visitor pattern based state for grammar parser.
public class StateList3ListOfExpression1 extends StackState<java.util.List<foundation.rpg.sample.language.ast.Expression>, StackState<LPar, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State>>> {

// NoStack:
// Stack:
    public StateList3ListOfExpression1(foundation.rpg.sample.language.ast.AstFactory factory, java.util.List<foundation.rpg.sample.language.ast.Expression> node, StackState<LPar, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State>> prev) {
        super(factory, node, prev);
    }


// Reduce:
// Shift:
    @Override
    public State visitRPar(RPar symbol) {
        return new StateRPar2(getFactory(), symbol, this);
    }


// Accept:
}
