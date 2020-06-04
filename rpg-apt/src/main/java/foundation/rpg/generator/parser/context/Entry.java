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

import foundation.rpg.MetaRule;
import foundation.rpg.Precedence;
import foundation.rpg.SymbolPart;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.joining;

public final class Entry {

    private final Element element;
    private final TypeMirror type;

    public Entry(Element element, TypeMirror type) {
        this.element = element;
        this.type = type;
    }

    public static Entry entry(ExecutableElement method) {
        return new Entry(method, method.getReturnType());
    }

    public static Entry entry(VariableElement parameter) {
        return entry(parameter, parameter.asType());
    }

    public static Entry entry(Element parameter, TypeMirror type) {
        return new Entry(parameter, type);
    }

    public static Entry typeEntry(Element type) {
        return new Entry(type, type.asType());
    }

    public Element getElement() {
        return element;
    }

    public TypeMirror getType() {
        return type;
    }

    private boolean includeAnnotation(Element e) {
        return nonNull(e.getAnnotation(MetaRule.class)) || nonNull(e.getAnnotation(SymbolPart.class)) || nonNull(e.getAnnotation(Precedence.class));
    }

    public Stream<Element> includedAnnotations() {
        return getElement().getAnnotationMirrors().stream().map(AnnotationMirror::getAnnotationType).map(DeclaredType::asElement).filter(this::includeAnnotation);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry entry = (Entry) o;
        return Objects.equals(element, entry.element) &&
                Objects.equals(type, entry.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(element, type);
    }

    @Override
    public String toString() {
        return includedAnnotations().map(a -> "@" + a).collect(joining(" ")) + " " + getType();
    }

    public String simpleName() {
        return includedAnnotations().map(a -> "@" + a.getSimpleName()).collect(joining(" ")) + " " + getType();
    }
}
