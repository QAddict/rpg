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

package foundation.fluent.jast.sample.tokens;

import foundation.fluent.jast.common.End;
import foundation.fluent.jast.common.LPar;
import foundation.fluent.jast.common.Plus;
import foundation.fluent.jast.common.RPar;
import foundation.fluent.jast.parser.Position;
import foundation.fluent.jast.sample.ast.Identifier;
import foundation.fluent.jast.sample.states.StateVisitor;

public interface Token extends foundation.fluent.jast.parser.Token<StateVisitor> {
    Position p = new Position("");
    Token END = current -> current.visit(new End(p));
    Token LPAR = current -> current.visit(new LPar(p));
    Token RPAR = current -> current.visit(new RPar(p));
    Token PLUS = current -> current.visit(new Plus(p));
    Token IDENT = current -> current.visit(new Identifier("name", 0, 0));
    static Token ident(String name, int line, int pos) {return current -> current.visit(new Identifier(name, line, pos));}
}
