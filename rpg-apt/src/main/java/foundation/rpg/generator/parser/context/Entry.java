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

import foundation.rpg.Name;
import foundation.rpg.generator.parser.TypeUtils;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static foundation.rpg.generator.parser.TypeUtils.includeInName;
import static java.util.stream.Collectors.*;
import static java.util.stream.Stream.concat;

public class Entry {
    private final TypeMirror typeMirror;
    private final String typeName;
    private final Set<AnnotationMirror> entryIdentifierAnnotations;
    private final Set<AnnotationMirror> symbolPartAnnotations;
    private final String uniqueString;

    private Entry(TypeMirror typeMirror, List<? extends AnnotationMirror> annotationMirrors) {
        this.typeMirror = typeMirror;
        this.typeName =TypeUtils.typeName(typeMirror);
        this.entryIdentifierAnnotations = annotationMirrors.stream().filter(a -> a.getAnnotationType().toString().equals(Name.class.getName()) || includeInName(a.getAnnotationType().asElement())).collect(toCollection(LinkedHashSet::new));
        this.symbolPartAnnotations = annotationMirrors.stream().filter(a -> includeInName(a.getAnnotationType().asElement())).collect(toCollection(LinkedHashSet::new));
        uniqueString = concat(entryIdentifierAnnotations.stream().map(Objects::toString), Stream.of(typeName)).collect(joining(" "));
    }

    public static Entry typeEntry(TypeMirror typeMirror) {
        return new Entry(typeMirror, typeMirror.getAnnotationMirrors());
    }

    private static Entry elementEntry(Element element, TypeMirror type) {
        return new Entry(type, concat(element.getAnnotationMirrors().stream(), type.getAnnotationMirrors().stream()).collect(toList()));
    }

    public static Entry methodEntry(ExecutableElement method) {
        return elementEntry(method, method.getReturnType());
    }

    public static Entry parameterEntry(VariableElement parameter) {
        return elementEntry(parameter, parameter.asType());
    }

    public static Entry entry(Entry entry, List<? extends AnnotationMirror> additional) {
        if(additional.isEmpty())
            return entry;
        List<AnnotationMirror> annotationMirrors = new ArrayList<>(entry.getEntryIdentifierAnnotations().size() + additional.size());
        annotationMirrors.addAll(entry.getEntryIdentifierAnnotations());
        annotationMirrors.addAll(additional);
        return new Entry(entry.getTypeMirror(), annotationMirrors);
    }

    public TypeMirror getTypeMirror() {
        return typeMirror;
    }

    public String getTypeName() {
        return typeName;
    }

    public Set<AnnotationMirror> getEntryIdentifierAnnotations() {
        return entryIdentifierAnnotations;
    }

    public Set<AnnotationMirror> getSymbolPartAnnotations() {
        return symbolPartAnnotations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry entry = (Entry) o;
        return uniqueString.equals(entry.uniqueString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueString);
    }

    @Override
    public String toString() {
        return uniqueString;
    }

}
