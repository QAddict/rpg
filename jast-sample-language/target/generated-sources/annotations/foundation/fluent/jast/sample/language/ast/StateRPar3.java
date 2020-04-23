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

package foundation.fluent.jast.sample.language.ast;

/*

RPar3: {
	Expression -> LPar Expression RPar • [RPar, Plus]
}

*/

import foundation.fluent.jast.parser.UnexpectedInputException;
import javax.annotation.Generated;

@Generated("Generated visitor pattern based state for grammar parser.")
public class StateRPar3 extends StackState<foundation.fluent.jast.common.RPar, StackState<foundation.fluent.jast.sample.language.ast.Expression, StackState<foundation.fluent.jast.common.LPar, ? extends State>>> {
    // Stack:
    public StateRPar3(foundation.fluent.jast.common.RPar node, StackState<foundation.fluent.jast.sample.language.ast.Expression, StackState<foundation.fluent.jast.common.LPar, ? extends State>> prev) {
        super(node, prev);
    }


    // Reduce:
    @Override
    public State visitRPar(foundation.fluent.jast.common.RPar symbol) throws UnexpectedInputException {
        
		StackState<foundation.fluent.jast.sample.language.ast.Expression, StackState<foundation.fluent.jast.common.LPar, ? extends State>> stack1 = this.getPrev();
		StackState<foundation.fluent.jast.common.LPar, ? extends State> stack2 = stack1.getPrev();
		State stack3 = stack2.getPrev();
        return stack3.visitExpression(foundation.fluent.jast.sample.language.ast.AstFactory.is(stack2.getNode(), stack1.getNode(), this.getNode())).visitRPar(symbol);
    }

    @Override
    public State visitPlus(foundation.fluent.jast.common.Plus symbol) throws UnexpectedInputException {
        
		StackState<foundation.fluent.jast.sample.language.ast.Expression, StackState<foundation.fluent.jast.common.LPar, ? extends State>> stack1 = this.getPrev();
		StackState<foundation.fluent.jast.common.LPar, ? extends State> stack2 = stack1.getPrev();
		State stack3 = stack2.getPrev();
        return stack3.visitExpression(foundation.fluent.jast.sample.language.ast.AstFactory.is(stack2.getNode(), stack1.getNode(), this.getNode())).visitPlus(symbol);
    }


    // Shift:
    // Accept:
}