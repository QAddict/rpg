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

package foundation.fluent.jast.sample.states;

import foundation.fluent.jast.common.End;
import foundation.fluent.jast.common.LPar;
import foundation.fluent.jast.common.Plus;
import foundation.fluent.jast.common.RPar;
import foundation.fluent.jast.parser.StateBase;
import foundation.fluent.jast.sample.ast.Expression;
import foundation.fluent.jast.sample.ast.Identifier;

public class StateVisitor extends StateBase {

    public StateVisitor visitAny(Object object) {
        throw new IllegalStateException(getClass().getSimpleName() + ": Unexpected " + object.getClass().getSimpleName());
    }

    public StateVisitor visit(End symbol) {
        return visitAny(symbol).visit(symbol);
    }

    public StateVisitor visit(LPar symbol) {
        return visitAny(symbol).visit(symbol);
    }

    public StateVisitor visit(RPar symbol) {
        return visitAny(symbol).visit(symbol);
    }

    public StateVisitor visit(Plus symbol) {
        return visitAny(symbol).visit(symbol);
    }

    public StateVisitor visit(Identifier symbol) {
        return visitAny(symbol).visit(symbol);
    }

    public StateVisitor visit(Expression symbol) {
        return visitAny(symbol).visit(symbol);
    }

    public boolean accepted() {
        return false;
    }

    public Expression result() {
        throw new IllegalStateException(getClass().getSimpleName() + ": Result not available.");
    }

}
