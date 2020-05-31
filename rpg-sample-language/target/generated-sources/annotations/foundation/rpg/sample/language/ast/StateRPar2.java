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

import foundation.rpg.parser.UnexpectedInputException;

// Generated visitor pattern based state for grammar parser.
public class StateRPar2 extends StackState<foundation.rpg.common.symbols.RPar, StackState<java.util.List<foundation.rpg.sample.language.ast.Expression>, StackState<foundation.rpg.common.symbols.LPar, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State>>>> {

// NoStack:
// Stack:
    public StateRPar2(foundation.rpg.sample.language.ast.AstFactory factory, foundation.rpg.common.symbols.RPar node, StackState<java.util.List<foundation.rpg.sample.language.ast.Expression>, StackState<foundation.rpg.common.symbols.LPar, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State>>> prev) {
        super(factory, node, prev);
    }


// Reduce:
    @Override
    public State visitSemicolon(foundation.rpg.common.symbols.Semicolon symbol) throws UnexpectedInputException {
        StackState<java.util.List<foundation.rpg.sample.language.ast.Expression>, StackState<foundation.rpg.common.symbols.LPar, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State>>> stack1 = this.getPrev();
		StackState<foundation.rpg.common.symbols.LPar, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State>> stack2 = stack1.getPrev();
		StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State> stack3 = stack2.getPrev();
		State stack4 = stack3.getPrev();
        return stack4.visitAtomicExpression(getFactory().is(stack3.getNode(), stack2.getNode(), stack1.getNode(), this.getNode())).visitSemicolon(symbol);
    }

    @Override
    public State visitGt(foundation.rpg.common.symbols.Gt symbol) throws UnexpectedInputException {
        StackState<java.util.List<foundation.rpg.sample.language.ast.Expression>, StackState<foundation.rpg.common.symbols.LPar, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State>>> stack1 = this.getPrev();
		StackState<foundation.rpg.common.symbols.LPar, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State>> stack2 = stack1.getPrev();
		StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State> stack3 = stack2.getPrev();
		State stack4 = stack3.getPrev();
        return stack4.visitAtomicExpression(getFactory().is(stack3.getNode(), stack2.getNode(), stack1.getNode(), this.getNode())).visitGt(symbol);
    }

    @Override
    public State visitPlus(foundation.rpg.common.symbols.Plus symbol) throws UnexpectedInputException {
        StackState<java.util.List<foundation.rpg.sample.language.ast.Expression>, StackState<foundation.rpg.common.symbols.LPar, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State>>> stack1 = this.getPrev();
		StackState<foundation.rpg.common.symbols.LPar, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State>> stack2 = stack1.getPrev();
		StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State> stack3 = stack2.getPrev();
		State stack4 = stack3.getPrev();
        return stack4.visitAtomicExpression(getFactory().is(stack3.getNode(), stack2.getNode(), stack1.getNode(), this.getNode())).visitPlus(symbol);
    }

    @Override
    public State visitTimes(foundation.rpg.common.symbols.Times symbol) throws UnexpectedInputException {
        StackState<java.util.List<foundation.rpg.sample.language.ast.Expression>, StackState<foundation.rpg.common.symbols.LPar, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State>>> stack1 = this.getPrev();
		StackState<foundation.rpg.common.symbols.LPar, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State>> stack2 = stack1.getPrev();
		StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State> stack3 = stack2.getPrev();
		State stack4 = stack3.getPrev();
        return stack4.visitAtomicExpression(getFactory().is(stack3.getNode(), stack2.getNode(), stack1.getNode(), this.getNode())).visitTimes(symbol);
    }


// Shift:
// Accept:
}
