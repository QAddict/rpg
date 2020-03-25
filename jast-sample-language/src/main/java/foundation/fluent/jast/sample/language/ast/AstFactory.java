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

import foundation.fluent.jast.RulePriority;
import foundation.fluent.jast.StartSymbol;
import foundation.fluent.jast.common.*;

import java.util.ArrayList;
import java.util.List;

@RulePriority(1)
public class AstFactory {

    @StartSymbol
    public static Program program(List<Statement> statements, End end) {
        return new Program(statements);
    }

    public static List<Statement> statements() {
        return new ArrayList<>();
    }

    public static List<Statement> statements(List<Statement> statements, Statement statement) {
        statements.add(statement);
        return statements;
    }

    public static Statement statement(Expression expression, Dot dot) {
        return new ExpressionStatement(expression);
    }

    public static Expression expression(Operand operand) {
        return operand.getExpression();
    }

    public static Expression expression(Expression left, Plus plus, Expression right) {
        return new BinaryExpression(left, right);
    }

    public static Expression operand(Identifier identifier) {
        return identifier;
    }

    public static Expression expression(LPar l, Expression expression, RPar r) {
        return expression;
    }

    public void ignore(WhiteSpace w) {}

    public void ignore(Comment c) {}

}
