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

Character8: {
	Item -> Character Minus Character • [RBr, Character, Bs, Dot]
}

*/

import foundation.rpg.parser.UnexpectedInputException;
import javax.annotation.Generated;

@Generated("Generated visitor pattern based state for grammar parser.")
public class StateCharacter8 extends StackState<java.lang.Character, StackState<foundation.rpg.common.Minus, StackState<java.lang.Character, ? extends State>>> {
// Stack:
    public StateCharacter8(java.lang.Character node, StackState<foundation.rpg.common.Minus, StackState<java.lang.Character, ? extends State>> prev) {
        super(node, prev);
    }


// Reduce:
    @Override
    public State visitRBr(foundation.rpg.common.RBr symbol) throws UnexpectedInputException {
        
		StackState<foundation.rpg.common.Minus, StackState<java.lang.Character, ? extends State>> stack1 = this.getPrev();
		StackState<java.lang.Character, ? extends State> stack2 = stack1.getPrev();
		State stack3 = stack2.getPrev();
        return stack3.visitItem(foundation.rpg.lexer.regular.RegularExpressionFactory.is(stack2.getNode(), stack1.getNode(), this.getNode())).visitRBr(symbol);
    }

    @Override
    public State visitCharacter(java.lang.Character symbol) throws UnexpectedInputException {
        
		StackState<foundation.rpg.common.Minus, StackState<java.lang.Character, ? extends State>> stack1 = this.getPrev();
		StackState<java.lang.Character, ? extends State> stack2 = stack1.getPrev();
		State stack3 = stack2.getPrev();
        return stack3.visitItem(foundation.rpg.lexer.regular.RegularExpressionFactory.is(stack2.getNode(), stack1.getNode(), this.getNode())).visitCharacter(symbol);
    }

    @Override
    public State visitBs(foundation.rpg.common.Bs symbol) throws UnexpectedInputException {
        
		StackState<foundation.rpg.common.Minus, StackState<java.lang.Character, ? extends State>> stack1 = this.getPrev();
		StackState<java.lang.Character, ? extends State> stack2 = stack1.getPrev();
		State stack3 = stack2.getPrev();
        return stack3.visitItem(foundation.rpg.lexer.regular.RegularExpressionFactory.is(stack2.getNode(), stack1.getNode(), this.getNode())).visitBs(symbol);
    }

    @Override
    public State visitDot(foundation.rpg.common.Dot symbol) throws UnexpectedInputException {
        
		StackState<foundation.rpg.common.Minus, StackState<java.lang.Character, ? extends State>> stack1 = this.getPrev();
		StackState<java.lang.Character, ? extends State> stack2 = stack1.getPrev();
		State stack3 = stack2.getPrev();
        return stack3.visitItem(foundation.rpg.lexer.regular.RegularExpressionFactory.is(stack2.getNode(), stack1.getNode(), this.getNode())).visitDot(symbol);
    }


// Shift:
// Accept:
}