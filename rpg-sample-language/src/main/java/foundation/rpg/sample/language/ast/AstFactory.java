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

import foundation.rpg.Match;
import foundation.rpg.StartSymbol;
import foundation.rpg.common.Patterns;
import foundation.rpg.common.precedence.Additive;
import foundation.rpg.common.precedence.Atomic;
import foundation.rpg.common.precedence.Multiplicative;
import foundation.rpg.common.precedence.Relational;
import foundation.rpg.common.rules.*;
import foundation.rpg.common.symbols.*;

import java.util.List;

public class AstFactory implements WhiteSpaceRules {

    @StartSymbol
    Program                    is (@List1 List<@Dangling Statement> s)                           { return new Program(s); }
    @Dangling Statement        is (@Open Statement s) { return s; }
    @Dangling Statement        is2(Statement s ) { return s; }
    @Open Statement            is (If i, Expression c, Then t, @Open Statement s)                { return new IfStatement(c, s); }
    @Open Statement            is (If i, Expression c, Then t, Statement s, Else e, @Open Statement f)   { return new IfElseStatement(c, s, f); }
    Statement                  is2(If i, Expression c, Then t, Statement s, Else e, Statement f) { return new IfElseStatement(c, s, f); }
    Statement                  is (Expression e, Semicolon s)                                    { return new ExpressionStatement(e); }
    Statement                  is (Identifier i, Equal o, Expression e, Semicolon s)             { return new ExpressionStatement(e); }
    Expression                 is (@Relational Expression e)                                     { return e; }
    @Relational Expression     is (@Relational Expression l, Gt o, @Additive Expression r)       { return new BinaryExpression(l, r); }
    @Additive Expression       is (@Additive Expression l, Plus o, @Multiplicative Expression r) { return new BinaryExpression(l, r); }
    @Multiplicative Expression is (@Multiplicative Expression l, Times o, @Atomic Expression r)  { return new BinaryExpression(l, r); }
    @Atomic Expression         is (Identifier i)                                                 { return i; }
    @Atomic Expression         is (@Match(Patterns.INTEGER) Long v)                              { return new Literal<>(v); }
    @Atomic Expression         is (@Match(Patterns.DOUBLE_QUOTED_STRING) String v)               { return new Literal<>(v); }
    @Atomic Expression         is (LPar l, Expression e, RPar r)                                 { return e; }
    @Atomic Expression         is (Identifier i, LPar l, @List3 List<Expression> e, RPar r)      { return null; }
    //@List3 List<Expression> is3(Expression e) { return list(e); }

}
