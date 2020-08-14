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

package foundation.rpg.generator.parser;

import foundation.rpg.MetaRule;
import foundation.rpg.Precedence;
import foundation.rpg.SymbolPart;

import javax.lang.model.AnnotatedConstruct;
import javax.lang.model.element.*;
import javax.lang.model.type.*;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.type.TypeKind.VOID;
import static javax.lang.model.util.ElementFilter.methodsIn;

public class TypeUtils {

    public static String typeName(TypeMirror typeMirror) {
        return typeMirror.toString().replaceAll("@.*? ", "").replaceAll("[ :()]", "");
    }

    public static String getAnnotationValue(AnnotationMirror a) {
        return a.getElementValues().entrySet().stream().filter(k -> k.getKey().getSimpleName().toString().equals("value"))
                .map(e -> e.getValue().getValue().toString()).findFirst().get();
    }

    public static boolean hasAnnotation(AnnotationMirror annotationMirror, Class<? extends Annotation> annotationType) {
        return nonNull(annotationMirror.getAnnotationType().asElement().getAnnotation(annotationType));
    }

    public static boolean hasAnnotationAnnotatedWith(AnnotatedConstruct c, Class<? extends Annotation> annotationType) {
        return c.getAnnotationMirrors().stream().anyMatch(annotationMirror -> hasAnnotation(annotationMirror, annotationType));
    }

    public static boolean includeInName(Element annotationElement) {
        return nonNull(annotationElement.getAnnotation(MetaRule.class)) || nonNull(annotationElement.getAnnotation(SymbolPart.class)) || nonNull(annotationElement.getAnnotation(Precedence.class));
    }

    public static List<Element> getAnnotationAnnotatedWith(AnnotatedConstruct c, Class<? extends Annotation> annotationType) {
        return c.getAnnotationMirrors().stream().filter(annotationMirror -> hasAnnotation(annotationMirror, annotationType)).map(a -> a.getAnnotationType().asElement()).collect(toList());
    }

    public static Stream<ExecutableElement> methods(Element factory) {
        return concat(methodsIn(factory.getEnclosedElements()).stream(), ((TypeElement)factory).getInterfaces().stream().flatMap(i -> methods(((DeclaredType) i).asElement())).filter(m -> !m.getModifiers().contains(PRIVATE)));
    }

    public static boolean isVoid(ExecutableElement method) {
        return method.getReturnType().getKind() == VOID;
    }

    public static boolean notVoid(ExecutableElement method) {
        return !isVoid(method);
    }

}
