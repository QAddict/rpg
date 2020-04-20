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

ListOfStatement1: {
	Program -> ListOfStatement • End []
	ListOfStatement -> ListOfStatement • Statement [End, Dot, Plus, Identifier, LPar]
	Statement -> • Expression Dot [Dot, Plus, End, Identifier, LPar]
	Expression -> • Expression Plus Expression [Dot, Plus]
	Expression -> • Identifier [Dot, Plus]
	Expression -> • LPar Expression RPar [Dot, Plus]
	Expression -> • Identifier LPar NOfListOfExpression RPar [Dot, Plus]
}

*/

import foundation.fluent.jast.parser.UnexpectedInputException;
import javax.annotation.Generated;

@Generated("Generated visitor pattern based state for grammar parser.")
public class StateListOfStatement1 extends StackState<java.util.List<foundation.fluent.jast.sample.language.ast.Statement>, State> {
    // Stack:
    public StateListOfStatement1(java.util.List<foundation.fluent.jast.sample.language.ast.Statement> node, State prev) {
        super(node, prev);
    }


    // Reduce:
    // Shift:
    @Override
    public State visitEnd(foundation.fluent.jast.common.End symbol) {
        return new StateEnd1(symbol, this);
    }

    @Override
    public State visitStatement(foundation.fluent.jast.sample.language.ast.Statement symbol) {
        return new StateStatement1(symbol, this);
    }

    @Override
    public State visitIdentifier(foundation.fluent.jast.sample.language.ast.Identifier symbol) {
        return new StateIdentifier1(symbol, this);
    }

    @Override
    public State visitExpression(foundation.fluent.jast.sample.language.ast.Expression symbol) {
        return new StateExpression1(symbol, this);
    }

    @Override
    public State visitLPar(foundation.fluent.jast.common.LPar symbol) {
        return new StateLPar1(symbol, this);
    }


    // Accept:
}
