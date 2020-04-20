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

package foundation.fluent.jast.generator;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.*;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class TypeUtils {

    public static Map<String, TypeMirror> resolveParameters(ExecutableElement genericMethod, TypeMirror returnType) {
        return new TypeResolver().visit(genericMethod.getReturnType(), returnType);
    }


    private static class TypeResolver implements TypeVisitor<Map<String, TypeMirror>, TypeMirror> {
        private final Map<String, TypeMirror> map = new LinkedHashMap<>();

        @Override
        public Map<String, TypeMirror> visit(TypeMirror t, TypeMirror mirror) {
            return t.accept(this, mirror);
        }

        @Override
        public Map<String, TypeMirror> visit(TypeMirror t) {
            return map;
        }

        @Override
        public Map<String, TypeMirror> visitPrimitive(PrimitiveType t, TypeMirror mirror) {
            return map;
        }

        @Override
        public Map<String, TypeMirror> visitNull(NullType t, TypeMirror mirror) {
            return map;
        }

        @Override
        public Map<String, TypeMirror> visitArray(ArrayType t, TypeMirror mirror) {
            return visit(t.getComponentType(), ((ArrayType) mirror).getComponentType());
        }

        @Override
        public Map<String, TypeMirror> visitDeclared(DeclaredType t, TypeMirror mirror) {
            map.put(t.toString(), mirror);
            Iterator<? extends TypeMirror> i1 = t.getTypeArguments().iterator();
            Iterator<? extends TypeMirror> i2 = ((DeclaredType) mirror).getTypeArguments().iterator();
            while(i1.hasNext() && i2.hasNext()) {
                visit(i1.next(), i2.next());
            }
            return map;
        }

        @Override
        public Map<String, TypeMirror> visitError(ErrorType t, TypeMirror mirror) {
            return map;
        }

        @Override
        public Map<String, TypeMirror> visitTypeVariable(TypeVariable t, TypeMirror mirror) {
            map.put(t.toString(), mirror);
            return map;
        }

        @Override
        public Map<String, TypeMirror> visitWildcard(WildcardType t, TypeMirror mirror) {
            return map;
        }

        @Override
        public Map<String, TypeMirror> visitExecutable(ExecutableType t, TypeMirror mirror) {
            return map;
        }

        @Override
        public Map<String, TypeMirror> visitNoType(NoType t, TypeMirror mirror) {
            return map;
        }

        @Override
        public Map<String, TypeMirror> visitUnknown(TypeMirror t, TypeMirror mirror) {
            return map;
        }

        @Override
        public Map<String, TypeMirror> visitUnion(UnionType t, TypeMirror mirror) {
            return map;
        }

        @Override
        public Map<String, TypeMirror> visitIntersection(IntersectionType t, TypeMirror mirror) {
            return map;
        }
    }

}
