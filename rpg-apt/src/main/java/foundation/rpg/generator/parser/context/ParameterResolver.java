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

import foundation.rpg.generator.parser.TypeUtils;

import javax.lang.model.element.VariableElement;
import javax.lang.model.type.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ParameterResolver implements TypeVisitor<Void, Entry> {

    private final Map<String, Entry> entryMap = new HashMap<>();

    private Entry resolve(Entry entry) {
        return entryMap.computeIfAbsent(entry.getTypeName(), key -> entry);
    }

    public Entry resolve(VariableElement parameter) {
        return resolve(Entry.parameterEntry(parameter));
    }

    @Override
    public Void visit(TypeMirror t, Entry entry) {
        entryMap.put(TypeUtils.typeName(t), entry);
        return t.accept(this, entry);
    }

    @Override
    public Void visit(TypeMirror t) {
        return null;
    }

    @Override
    public Void visitPrimitive(PrimitiveType t, Entry entry) {
        return null;
    }

    @Override
    public Void visitNull(NullType t, Entry entry) {
        return null;
    }

    @Override
    public Void visitArray(ArrayType t, Entry entry) {
        visit(t.getComponentType(), Entry.typeEntry(((ArrayType) entry.getTypeMirror()).getComponentType()));
        return null;
    }

    @Override
    public Void visitDeclared(DeclaredType t, Entry entry) {
        Iterator<? extends TypeMirror> i1 = t.getTypeArguments().iterator();
        Iterator<? extends TypeMirror> i2 = ((DeclaredType) entry.getTypeMirror()).getTypeArguments().iterator();
        while(i1.hasNext() && i2.hasNext()) {
            visit(i1.next(), Entry.typeEntry(i2.next()));
        }
        return null;
    }

    @Override
    public Void visitError(ErrorType t, Entry entry) {
        return null;
    }

    @Override
    public Void visitTypeVariable(TypeVariable t, Entry entry) {
        return null;
    }

    @Override
    public Void visitWildcard(WildcardType t, Entry entry) {
        return null;
    }

    @Override
    public Void visitExecutable(ExecutableType t, Entry entry) {
        return null;
    }

    @Override
    public Void visitNoType(NoType t, Entry entry) {
        return null;
    }

    @Override
    public Void visitUnknown(TypeMirror t, Entry entry) {
        return null;
    }

    @Override
    public Void visitUnion(UnionType t, Entry entry) {
        return null;
    }

    @Override
    public Void visitIntersection(IntersectionType t, Entry entry) {
        return null;
    }

}
