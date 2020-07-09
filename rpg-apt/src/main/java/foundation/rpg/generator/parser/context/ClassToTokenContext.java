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

import foundation.rpg.Match;
import foundation.rpg.Name;
import foundation.rpg.generator.lexer.LexerGenerator;
import foundation.rpg.generator.lexer.LexerGenerator.TokenInfo;
import foundation.rpg.generator.parser.TypeUtils;
import foundation.rpg.grammar.Symbol;
import foundation.rpg.regular.RegularExpressionParser;
import foundation.rpg.parser.Token;

import javax.annotation.processing.Filer;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;
import static javax.lang.model.util.ElementFilter.constructorsIn;

public class ClassToTokenContext {

    private final RegularExpressionParser parser = new RegularExpressionParser();
    private boolean isStatic = true;

    public void generate(Context context, Filer filer) throws IOException {
        new LexerGenerator().generateLexer(
                context.getPackageName(), "GeneratedLexer", concat(context.getGrammar().getTerminals().stream(), context.getGrammar().getIgnored().stream()).map(symbol -> tokenInfoFor(symbol, context.symbolEntryOf(symbol))).collect(toList()),
                new PrintWriter(filer.createSourceFile(context.getPackageName() + ".GeneratedLexer").openWriter()), context.isFactoryStatic() ? null : context.getFactoryClass().asType()
        );
    }

    private Optional<String> annotationValue(List<? extends AnnotationMirror> list, Class<?> t) {
        return list.stream().filter(a -> a.getAnnotationType().toString().equals(t.getName())).map(TypeUtils::getAnnotationValue).findFirst();
    }

    public TokenInfo tokenInfoFor(Symbol symbol, SymbolEntry entry) {
        Element typeElement = ((DeclaredType) entry.typeMirror()).asElement();
        String p = Token.class.getName().equals(entry.type())
                ? "builder.build()"
                : "new " + entry.type() + "(" + (constructorsIn(typeElement.getEnclosedElements()).stream().anyMatch(c -> c.getParameters().size() == 1 && c.getParameters().get(0).asType().toString().equals(Token.class.getCanonicalName()))
                ?  "builder.build()" : "builder.build().getContent()") + ")";
        String call = "Element" + symbol + "(" + p + ")";

        return annotationValue(entry.getAnnotations(), Match.class).map(m -> new TokenInfo(typeElement, call, parser.parsePattern(m), 0))
                .or(() -> annotationValue(entry.getAnnotations(), Name.class).map(m -> new TokenInfo(typeElement, call, parser.parseText(m), 1)))
                .or(() -> annotationValue(typeElement.getAnnotationMirrors(), Match.class).map(m -> new TokenInfo(typeElement, call, parser.parsePattern(m), 0)))
                .or(() -> annotationValue(typeElement.getAnnotationMirrors(), Name.class).map(m -> new TokenInfo(typeElement, call, parser.parseText(m), 1)))
                .orElseThrow(() -> new IllegalArgumentException("No token defined for " + symbol + ". Use @Name or @Match annotation on " + symbol + " or in factory."));

    }

    public boolean isStatic() {
        return isStatic;
    }
}
