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
import foundation.rpg.parser.StateBase;

// Generated visitor pattern based state for grammar parser.
public class State extends StateBase<foundation.rpg.sample.language.ast.Program> {
    private final foundation.rpg.sample.language.ast.AstFactory factory;

    public State(foundation.rpg.sample.language.ast.AstFactory factory) {
        this.factory = factory;
    }

    public foundation.rpg.sample.language.ast.AstFactory getFactory() {
        return factory;
    }

// Ignored:
    public State visitWhiteSpace(foundation.rpg.common.symbols.WhiteSpace symbol) {
        return this;
    }

    public State visitComment(foundation.rpg.common.symbols.Comment symbol) {
        return this;
    }


// Symbols:
    public State visitEnd(foundation.rpg.parser.End symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitSemicolon(foundation.rpg.common.symbols.Semicolon symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitGt(foundation.rpg.common.symbols.Gt symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitPlus(foundation.rpg.common.symbols.Plus symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitTimes(foundation.rpg.common.symbols.Times symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitIdentifier(foundation.rpg.sample.language.ast.Identifier symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitLong(java.lang.Long symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitString(java.lang.String symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitLPar(foundation.rpg.common.symbols.LPar symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitRPar(foundation.rpg.common.symbols.RPar symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitComma(foundation.rpg.common.symbols.Comma symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitProgram(foundation.rpg.sample.language.ast.Program symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitList1ListOfStatement(java.util.List<foundation.rpg.sample.language.ast.Statement> symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitStatement(foundation.rpg.sample.language.ast.Statement symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitExpression(foundation.rpg.sample.language.ast.Expression symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitRelationalExpression(foundation.rpg.sample.language.ast.Expression symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitAdditiveExpression(foundation.rpg.sample.language.ast.Expression symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitMultiplicativeExpression(foundation.rpg.sample.language.ast.Expression symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitAtomicExpression(foundation.rpg.sample.language.ast.Expression symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitList3ListOfExpression(java.util.List<foundation.rpg.sample.language.ast.Expression> symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitList2ListOfExpression(java.util.List<foundation.rpg.sample.language.ast.Expression> symbol) throws UnexpectedInputException {
        return error(symbol);
    }


}
