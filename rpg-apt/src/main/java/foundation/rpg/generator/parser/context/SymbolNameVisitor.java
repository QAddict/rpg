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

package foundation.rpg.generator.parser.context;

import javax.lang.model.type.*;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.joining;

public class SymbolNameVisitor implements TypeVisitor<String, Void> {
    @Override
    public String visit(TypeMirror t, Void unused) {
        return t == null ? "" : t.accept(this, unused);
    }

    @Override
    public String visit(TypeMirror t) {
        return visit(t, null);
    }

    @Override
    public String visitPrimitive(PrimitiveType t, Void unused) {
        return t.toString();
    }

    @Override
    public String visitNull(NullType t, Void unused) {
        return "null";
    }

    @Override
    public String visitArray(ArrayType t, Void unused) {
        return "ArrayOf" + visit(t.getComponentType());
    }

    @Override
    public String visitDeclared(DeclaredType t, Void unused) {
        return t.asElement().getSimpleName() + (t.getTypeArguments().isEmpty() ? "" : "Of" + t.getTypeArguments().stream().map(a -> a.accept(this, unused)).collect(joining("")));
    }

    @Override
    public String visitError(ErrorType t, Void unused) {
        return "error";
    }

    @Override
    public String visitTypeVariable(TypeVariable t, Void unused) {
        return t.toString();
    }

    @Override
    public String visitWildcard(WildcardType t, Void unused) {
        if(nonNull(t.getExtendsBound()))
            return "Out" + visit(t.getExtendsBound());
        if(nonNull(t.getSuperBound()))
            return "In" + visit(t.getSuperBound());
        return "Any";
    }

    @Override
    public String visitExecutable(ExecutableType t, Void unused) {
        return "";
    }

    @Override
    public String visitNoType(NoType t, Void unused) {
        return "";
    }

    @Override
    public String visitUnknown(TypeMirror t, Void unused) {
        return "";
    }

    @Override
    public String visitUnion(UnionType t, Void unused) {
        return "";
    }

    @Override
    public String visitIntersection(IntersectionType t, Void unused) {
        return "";
    }

}
