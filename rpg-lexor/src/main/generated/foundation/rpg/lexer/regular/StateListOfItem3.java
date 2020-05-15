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

package foundation.rpg.lexer.regular;

/*

ListOfItem3: {
	Node -> LBr ListOfItem • RBr [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	ListOfItem -> ListOfItem • Item [RBr, Character, Bs, Dot]
	Item -> • Character [RBr, Character, Bs, Dot]
	Item -> • Bs [RBr, Character, Bs, Dot]
	Item -> • Dot [RBr, Character, Bs, Dot]
	Item -> • Character Minus Character [RBr, Character, Bs, Dot]
}

*/

import foundation.rpg.parser.UnexpectedInputException;
import javax.annotation.Generated;

@Generated("Generated visitor pattern based state for grammar parser.")
public class StateListOfItem3 extends StackState<java.util.List<foundation.rpg.lexer.regular.ast.Item>, StackState<foundation.rpg.common.LBr, ? extends State>> {
// Stack:
    public StateListOfItem3(java.util.List<foundation.rpg.lexer.regular.ast.Item> node, StackState<foundation.rpg.common.LBr, ? extends State> prev) {
        super(node, prev);
    }


// Reduce:
// Shift:
    @Override
    public State visitRBr(foundation.rpg.common.RBr symbol) {
        return new StateRBr3(symbol, this);
    }

    @Override
    public State visitItem(foundation.rpg.lexer.regular.ast.Item symbol) {
        return new StateItem1(symbol, this);
    }

    @Override
    public State visitCharacter(java.lang.Character symbol) {
        return new StateCharacter4(symbol, this);
    }

    @Override
    public State visitBs(foundation.rpg.common.Bs symbol) {
        return new StateBs4(symbol, this);
    }

    @Override
    public State visitDot(foundation.rpg.common.Dot symbol) {
        return new StateDot4(symbol, this);
    }


// Accept:
}
