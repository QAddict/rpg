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

package foundation.rpg.processor;

import foundation.rpg.Match;
import foundation.rpg.Name;
import foundation.rpg.lexer.LexerGenerator;
import foundation.rpg.lexer.regular.RegularParser;
import foundation.rpg.parser.Position;
import foundation.rpg.parser.TokenDescription;

import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;

import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Objects.nonNull;
import static javax.lang.model.util.ElementFilter.constructorsIn;

public class ClassToTokenContext {

    private final RegularParser parser = new RegularParser();
    private final Map<TypeMirror, Element> tokenInfo = new LinkedHashMap<>();

    public void analyzeToken(VariableElement var) {
        Match match = var.getAnnotation(Match.class);
        Name name = var.getAnnotation(Name.class);
        TypeMirror type = var.asType();
        if(nonNull(match) || nonNull(name)) {
            if(tokenInfo.get(type) instanceof VariableElement)
                throw new IllegalStateException("Token info already defined at " + tokenInfo.get(type));
            tokenInfo.put(type, var);
            return;
        }
        if(type instanceof DeclaredType) {
            Element element = ((DeclaredType) type).asElement();
            match = element.getAnnotation(Match.class);
            name = element.getAnnotation(Name.class);
            if(nonNull(match) || nonNull(name)) {
                tokenInfo.put(type, element);
            }
        }
    }

    public Element elementFor(TypeMirror mirror) {
        return tokenInfo.computeIfAbsent(mirror, k -> {
            throw new IllegalArgumentException("No token exists for " + mirror);
        });
    }

    public LexerGenerator.TokenInfo tokenInfoFor(TypeMirror mirror) {
        Element element = elementFor(mirror);
        Match match = element.getAnnotation(Match.class);
        Name name = element.getAnnotation(Name.class);
        Element typeElement = ((DeclaredType) mirror).asElement();
        boolean acceptsPosition = constructorsIn(typeElement.getEnclosedElements()).stream().anyMatch(c -> c.getParameters().size() == 1 && c.getParameters().get(0).asType().toString().equals(TokenDescription.class.getCanonicalName()));
        String call = "visit" + typeElement.getSimpleName() + "(new " + mirror + "(" + (acceptsPosition ? "builder.build()" : "builder.build().getContent()") + "))";
        if(nonNull(match)) {
            return new LexerGenerator.TokenInfo(mirror, call, parser.parsePattern(match.value()), match.priority());
        }
        return new LexerGenerator.TokenInfo(mirror, call, parser.parseText(name.value()), name.priority());
    }
}
