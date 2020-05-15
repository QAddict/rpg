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

RBr4: {
	Node -> LBr Tilda ListOfItem RBr • [RPar, LPar, Character, Bs, Dot, LBr, Pipe, Times, Plus]
}

*/

import foundation.rpg.parser.UnexpectedInputException;
import javax.annotation.Generated;

@Generated("Generated visitor pattern based state for grammar parser.")
public class StateRBr4 extends StackState<foundation.rpg.common.RBr, StackState<java.util.List<foundation.rpg.lexer.regular.ast.Item>, StackState<foundation.rpg.common.Tilda, StackState<foundation.rpg.common.LBr, ? extends State>>>> {
// Stack:
    public StateRBr4(foundation.rpg.common.RBr node, StackState<java.util.List<foundation.rpg.lexer.regular.ast.Item>, StackState<foundation.rpg.common.Tilda, StackState<foundation.rpg.common.LBr, ? extends State>>> prev) {
        super(node, prev);
    }


// Reduce:
    @Override
    public State visitRPar(foundation.rpg.common.RPar symbol) throws UnexpectedInputException {
        
		StackState<java.util.List<foundation.rpg.lexer.regular.ast.Item>, StackState<foundation.rpg.common.Tilda, StackState<foundation.rpg.common.LBr, ? extends State>>> stack1 = this.getPrev();
		StackState<foundation.rpg.common.Tilda, StackState<foundation.rpg.common.LBr, ? extends State>> stack2 = stack1.getPrev();
		StackState<foundation.rpg.common.LBr, ? extends State> stack3 = stack2.getPrev();
		State stack4 = stack3.getPrev();
        return stack4.visitNode(foundation.rpg.lexer.regular.RegularExpressionFactory.is(stack3.getNode(), stack2.getNode(), stack1.getNode(), this.getNode())).visitRPar(symbol);
    }

    @Override
    public State visitLPar(foundation.rpg.common.LPar symbol) throws UnexpectedInputException {
        
		StackState<java.util.List<foundation.rpg.lexer.regular.ast.Item>, StackState<foundation.rpg.common.Tilda, StackState<foundation.rpg.common.LBr, ? extends State>>> stack1 = this.getPrev();
		StackState<foundation.rpg.common.Tilda, StackState<foundation.rpg.common.LBr, ? extends State>> stack2 = stack1.getPrev();
		StackState<foundation.rpg.common.LBr, ? extends State> stack3 = stack2.getPrev();
		State stack4 = stack3.getPrev();
        return stack4.visitNode(foundation.rpg.lexer.regular.RegularExpressionFactory.is(stack3.getNode(), stack2.getNode(), stack1.getNode(), this.getNode())).visitLPar(symbol);
    }

    @Override
    public State visitCharacter(java.lang.Character symbol) throws UnexpectedInputException {
        
		StackState<java.util.List<foundation.rpg.lexer.regular.ast.Item>, StackState<foundation.rpg.common.Tilda, StackState<foundation.rpg.common.LBr, ? extends State>>> stack1 = this.getPrev();
		StackState<foundation.rpg.common.Tilda, StackState<foundation.rpg.common.LBr, ? extends State>> stack2 = stack1.getPrev();
		StackState<foundation.rpg.common.LBr, ? extends State> stack3 = stack2.getPrev();
		State stack4 = stack3.getPrev();
        return stack4.visitNode(foundation.rpg.lexer.regular.RegularExpressionFactory.is(stack3.getNode(), stack2.getNode(), stack1.getNode(), this.getNode())).visitCharacter(symbol);
    }

    @Override
    public State visitBs(foundation.rpg.common.Bs symbol) throws UnexpectedInputException {
        
		StackState<java.util.List<foundation.rpg.lexer.regular.ast.Item>, StackState<foundation.rpg.common.Tilda, StackState<foundation.rpg.common.LBr, ? extends State>>> stack1 = this.getPrev();
		StackState<foundation.rpg.common.Tilda, StackState<foundation.rpg.common.LBr, ? extends State>> stack2 = stack1.getPrev();
		StackState<foundation.rpg.common.LBr, ? extends State> stack3 = stack2.getPrev();
		State stack4 = stack3.getPrev();
        return stack4.visitNode(foundation.rpg.lexer.regular.RegularExpressionFactory.is(stack3.getNode(), stack2.getNode(), stack1.getNode(), this.getNode())).visitBs(symbol);
    }

    @Override
    public State visitDot(foundation.rpg.common.Dot symbol) throws UnexpectedInputException {
        
		StackState<java.util.List<foundation.rpg.lexer.regular.ast.Item>, StackState<foundation.rpg.common.Tilda, StackState<foundation.rpg.common.LBr, ? extends State>>> stack1 = this.getPrev();
		StackState<foundation.rpg.common.Tilda, StackState<foundation.rpg.common.LBr, ? extends State>> stack2 = stack1.getPrev();
		StackState<foundation.rpg.common.LBr, ? extends State> stack3 = stack2.getPrev();
		State stack4 = stack3.getPrev();
        return stack4.visitNode(foundation.rpg.lexer.regular.RegularExpressionFactory.is(stack3.getNode(), stack2.getNode(), stack1.getNode(), this.getNode())).visitDot(symbol);
    }

    @Override
    public State visitLBr(foundation.rpg.common.LBr symbol) throws UnexpectedInputException {
        
		StackState<java.util.List<foundation.rpg.lexer.regular.ast.Item>, StackState<foundation.rpg.common.Tilda, StackState<foundation.rpg.common.LBr, ? extends State>>> stack1 = this.getPrev();
		StackState<foundation.rpg.common.Tilda, StackState<foundation.rpg.common.LBr, ? extends State>> stack2 = stack1.getPrev();
		StackState<foundation.rpg.common.LBr, ? extends State> stack3 = stack2.getPrev();
		State stack4 = stack3.getPrev();
        return stack4.visitNode(foundation.rpg.lexer.regular.RegularExpressionFactory.is(stack3.getNode(), stack2.getNode(), stack1.getNode(), this.getNode())).visitLBr(symbol);
    }

    @Override
    public State visitPipe(foundation.rpg.common.Pipe symbol) throws UnexpectedInputException {
        
		StackState<java.util.List<foundation.rpg.lexer.regular.ast.Item>, StackState<foundation.rpg.common.Tilda, StackState<foundation.rpg.common.LBr, ? extends State>>> stack1 = this.getPrev();
		StackState<foundation.rpg.common.Tilda, StackState<foundation.rpg.common.LBr, ? extends State>> stack2 = stack1.getPrev();
		StackState<foundation.rpg.common.LBr, ? extends State> stack3 = stack2.getPrev();
		State stack4 = stack3.getPrev();
        return stack4.visitNode(foundation.rpg.lexer.regular.RegularExpressionFactory.is(stack3.getNode(), stack2.getNode(), stack1.getNode(), this.getNode())).visitPipe(symbol);
    }

    @Override
    public State visitTimes(foundation.rpg.common.Times symbol) throws UnexpectedInputException {
        
		StackState<java.util.List<foundation.rpg.lexer.regular.ast.Item>, StackState<foundation.rpg.common.Tilda, StackState<foundation.rpg.common.LBr, ? extends State>>> stack1 = this.getPrev();
		StackState<foundation.rpg.common.Tilda, StackState<foundation.rpg.common.LBr, ? extends State>> stack2 = stack1.getPrev();
		StackState<foundation.rpg.common.LBr, ? extends State> stack3 = stack2.getPrev();
		State stack4 = stack3.getPrev();
        return stack4.visitNode(foundation.rpg.lexer.regular.RegularExpressionFactory.is(stack3.getNode(), stack2.getNode(), stack1.getNode(), this.getNode())).visitTimes(symbol);
    }

    @Override
    public State visitPlus(foundation.rpg.common.Plus symbol) throws UnexpectedInputException {
        
		StackState<java.util.List<foundation.rpg.lexer.regular.ast.Item>, StackState<foundation.rpg.common.Tilda, StackState<foundation.rpg.common.LBr, ? extends State>>> stack1 = this.getPrev();
		StackState<foundation.rpg.common.Tilda, StackState<foundation.rpg.common.LBr, ? extends State>> stack2 = stack1.getPrev();
		StackState<foundation.rpg.common.LBr, ? extends State> stack3 = stack2.getPrev();
		State stack4 = stack3.getPrev();
        return stack4.visitNode(foundation.rpg.lexer.regular.RegularExpressionFactory.is(stack3.getNode(), stack2.getNode(), stack1.getNode(), this.getNode())).visitPlus(symbol);
    }


// Shift:
// Accept:
}
