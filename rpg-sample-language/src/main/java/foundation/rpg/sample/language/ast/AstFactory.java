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

import foundation.rpg.StartSymbol;
import foundation.rpg.SymbolPart;
import foundation.rpg.common.*;

import java.util.List;

public class AstFactory implements WhiteSpaceRules, ListRules {

    @StartSymbol
    Program        is  (@List1 List<Statement> s)                                 { return new Program(s); }
    Statement      is  (Expression e, Dot d)                                      { return new ExpressionStatement(e); }
    Expression     is  (@P1 Expression e)                                         { return e; }
    @P1 Expression is2 (@P2 Expression e)                                         { return e; }
    @P1 Expression is  (@P1 Expression l, Plus o, @P2 Expression r)               { return new BinaryExpression(l, r); }
    @P2 Expression is3 (@P3 Expression e)                                         { return e; }
    @P2 Expression is  (@P2 Expression l, Times o, @P3 Expression r)              { return new BinaryExpression(l, r); }
    @P3 Expression is  (Identifier i)                                             { return i; }
    @P3 Expression is  (LPar l, Expression e, RPar r)                             { return e; }
    @P3 Expression is  (Identifier i, LPar l, @List3 List<Expression> e, RPar r)  { return null; }

    @SymbolPart @interface P1 {}
    @SymbolPart @interface P2 {}
    @SymbolPart @interface P3 {}

}
