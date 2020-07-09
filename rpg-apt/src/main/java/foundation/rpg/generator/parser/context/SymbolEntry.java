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

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public final class SymbolEntry {

    private final TypeMirror typeMirror;
    private final String type;
    private final List<? extends AnnotationMirror> annotations;

    private SymbolEntry(TypeMirror typeMirror, String type, List<? extends AnnotationMirror> annotations) {
        this.typeMirror = typeMirror;
        this.type = type.replaceAll("@.*? ", "");
        this.annotations = annotations;
    }

    public static SymbolEntry symbolEntry(TypeMirror typeMirror, Element element) {
        return new SymbolEntry(typeMirror, typeMirror.toString(), Stream.concat(
                typeMirror.getAnnotationMirrors().stream(),
                element.getAnnotationMirrors().stream()).collect(Collectors.toList())
        );
    }

    public static SymbolEntry symbolEntry(TypeMirror typeMirror) {
        return new SymbolEntry(typeMirror, typeMirror.toString(), typeMirror.getAnnotationMirrors());
    }

    public static String symbolEntryName(TypeMirror typeMirror, Element element) {
        return Stream.concat(
                element.getAnnotationMirrors().stream().filter(a -> TypeUtils.includeInName(a.getAnnotationType().asElement())).map(Objects::toString),
                Stream.of(typeMirror.toString())
        ).collect(joining(" "));
    }

    public TypeMirror typeMirror() {
        return typeMirror;
    }

    public String type() {
        return type;
    }

    public List<? extends AnnotationMirror> getAnnotations() {
        return annotations;
    }

    public String toString() {
        return type;
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SymbolEntry that = (SymbolEntry) o;
        return type.equals(that.type);
    }

}
