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

import foundation.fluent.jast.MetaRule;
import foundation.fluent.jast.Priority;
import foundation.fluent.jast.StartSymbol;
import foundation.fluent.jast.common.*;

import java.util.Collections;
import java.util.List;

import static foundation.fluent.jast.parser.AstUtils.addTo;
import static foundation.fluent.jast.parser.AstUtils.list;

@Priority(1)
public interface AstFactory {

    @StartSymbol
    static Program         is  (@SimpleList List<Statement> s, End e)            { return new Program(s); }
    static Statement       is  (Expression e, Dot d)                 { return new ExpressionStatement(e); }
    static Expression      is  (Expression l, Plus p, Expression r)  { return new BinaryExpression(l, r); }
    static Expression      is  (Identifier i)                        { return i; }
    static Expression      is  (LPar l, Expression e, RPar r)        { return e; }
    static Expression      is  (Identifier i, LPar l, @EmptyCommaList N<List<Expression>> e, RPar r) { return null; }

    static void ignore(WhiteSpace w) {}
    static void ignore(Comment c) {}

    @SimpleList static <T> List<T> simpleList () { return list(); }
    @SimpleList static <T> List<T> simpleList (List<T> l, T t) { return addTo(l, t); }

    @EmptyCommaList static <T> N<List<T>> l() { return new N<>(Collections.emptyList()); }
    @EmptyCommaList static <T> N<List<T>> l(@CommaList List<T> l) {return new N<>(l); }
    @CommaList static <T> List<T> commaList (T t) { return list(t); }
    @CommaList static <T> List<T> commaList (List<T> l, Comma c, T t) { return addTo(l, t); }

    @MetaRule @interface SimpleList {}
    @MetaRule @interface CommaList {}
    @MetaRule @interface EmptyCommaList {}
}
