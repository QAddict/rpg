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

ListOfNode1: {
	Chain -> ListOfNode • [End, Pipe]
	ListOfNode -> ListOfNode • Node [End, LPar, Character, Bs, Dot, LBr, Pipe]
	Node -> • LPar Pattern RPar [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	Node -> • Node Times [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	Node -> • Node Plus [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	Node -> • Character [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	Node -> • Bs Character [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	Node -> • Bs Bs [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	Node -> • Bs Dot [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	Node -> • Dot [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	Node -> • LBr ListOfItem RBr [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
	Node -> • LBr Tilda ListOfItem RBr [End, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
}

*/

import foundation.rpg.parser.UnexpectedInputException;
import javax.annotation.Generated;

@Generated("Generated visitor pattern based state for grammar parser.")
public class StateListOfNode1 extends StackState<java.util.List<foundation.rpg.lexer.regular.ast.Node>, State> {
// Stack:
    public StateListOfNode1(java.util.List<foundation.rpg.lexer.regular.ast.Node> node, State prev) {
        super(node, prev);
    }


// Reduce:
    @Override
    public State visitEnd(foundation.rpg.parser.End symbol) throws UnexpectedInputException {
        
		State stack1 = this.getPrev();
        return stack1.visitChain(foundation.rpg.lexer.regular.RegularExpressionFactory.is1(this.getNode())).visitEnd(symbol);
    }

    @Override
    public State visitPipe(foundation.rpg.common.Pipe symbol) throws UnexpectedInputException {
        
		State stack1 = this.getPrev();
        return stack1.visitChain(foundation.rpg.lexer.regular.RegularExpressionFactory.is1(this.getNode())).visitPipe(symbol);
    }


// Shift:
    @Override
    public State visitNode(foundation.rpg.lexer.regular.ast.Node symbol) {
        return new StateNode1(symbol, this);
    }

    @Override
    public State visitLPar(foundation.rpg.common.LPar symbol) {
        return new StateLPar1(symbol, this);
    }

    @Override
    public State visitCharacter(java.lang.Character symbol) {
        return new StateCharacter1(symbol, this);
    }

    @Override
    public State visitBs(foundation.rpg.common.Bs symbol) {
        return new StateBs1(symbol, this);
    }

    @Override
    public State visitDot(foundation.rpg.common.Dot symbol) {
        return new StateDot1(symbol, this);
    }

    @Override
    public State visitLBr(foundation.rpg.common.LBr symbol) {
        return new StateLBr1(symbol, this);
    }


// Accept:
}
