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

LPar3: {
	Expression -> LPar • Expression RPar [RPar, Plus]
	Expression -> • Expression Plus Expression [RPar, Plus]
	Expression -> • Identifier [RPar, Plus]
	Expression -> • LPar Expression RPar [RPar, Plus]
	Expression -> • Identifier LPar NOfListOfExpression RPar [RPar, Plus]
}

*/

import foundation.fluent.jast.parser.UnexpectedInputException;
import javax.annotation.Generated;

@Generated("Generated visitor pattern based state for grammar parser.")
public class StateLPar3 extends StackState<foundation.fluent.jast.common.LPar, State> {
    // Stack:
    public StateLPar3(foundation.fluent.jast.common.LPar node, State prev) {
        super(node, prev);
    }


    // Reduce:
    // Shift:
    @Override
    public State visitIdentifier(foundation.fluent.jast.sample.language.ast.Identifier symbol) {
        return new StateIdentifier2(symbol, this);
    }

    @Override
    public State visitExpression(foundation.fluent.jast.sample.language.ast.Expression symbol) {
        return new StateExpression5(symbol, this);
    }

    @Override
    public State visitLPar(foundation.fluent.jast.common.LPar symbol) {
        return new StateLPar3(symbol, this);
    }


    // Accept:
}
