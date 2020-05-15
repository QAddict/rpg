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

import foundation.rpg.StartSymbol;
import foundation.rpg.automata.LrParserAutomata;
import foundation.rpg.generator.CodeGenerator;
import foundation.rpg.automata.LrParserConstructor;
import foundation.rpg.lexer.LexerGenerator;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.function.Consumer;

import static java.util.stream.Collectors.toList;

@SupportedAnnotationTypes("foundation.rpg.StartSymbol")
public class StartSymbolProcessor extends AbstractProcessor implements Consumer<ExecutableElement> {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        roundEnv.getElementsAnnotatedWith(StartSymbol.class).stream().map(ExecutableElement.class::cast).forEach(this);
        return true;
    }

    @Override
    public void accept(ExecutableElement element) {
        try {
            ClassToTokenContext tokenContext = new ClassToTokenContext();
            ClassToGrammarContext context = new ClassToGrammarContext(element, processingEnv.getElementUtils(), tokenContext);
            System.out.println("Grammar generated from class: " + element.getEnclosingElement());
            System.out.println(context.getGrammar());
            System.out.println();
            System.out.println();
            LrParserAutomata parser = LrParserConstructor.generateParser(context.getGrammar());
            System.out.println("Parser description generated from grammar:\n\n");
            System.out.println(parser);
            System.out.println();
            System.out.println();
            new CodeGenerator(processingEnv.getFiler(), context).generateSources(parser);
            /*
            new LexerGenerator().generateLexer(context.getPackageName(), "GeneratedLexer", context.getGrammar().getTerminals().stream().map(symbol -> {
                TypeMirror type = context.symbolType(symbol);
                return tokenContext.tokenInfoFor(type);
            }).collect(toList()), new PrintWriter(processingEnv.getFiler().createSourceFile(context.getPackageName() + ".GeneratedLexer").openWriter()));

             */
        } catch (RuntimeException | Error e) {
            e.printStackTrace();
        }
    }

}
