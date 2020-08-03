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
import foundation.rpg.generator.parser.context.Context;
import foundation.rpg.generator.parser.context.ContextBuilder;
import foundation.rpg.generator.parser.context.ClassToTokenContext;
import foundation.rpg.lr1.LrParserAutomata;
import foundation.rpg.lr1.LrParserConstructor;
import foundation.rpg.generator.parser.CodeGenerator;
import foundation.rpg.parser.End;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.util.Set;
import java.util.function.Consumer;

import static java.lang.System.currentTimeMillis;
import static javax.tools.Diagnostic.Kind.ERROR;

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
            long start = currentTimeMillis();
            ClassToTokenContext environmentGenerator = new ClassToTokenContext(processingEnv.getElementUtils());
            Context context = ContextBuilder.createContext(element, processingEnv.getElementUtils().getTypeElement(End.class.getName()).asType());
            long gc = currentTimeMillis();
            System.out.println("Grammar generated from class: " + element.getEnclosingElement() + " in " + (gc - start) + "ms");
            System.out.println(context.getGrammar());
            System.out.println();
            System.out.println();
            LrParserAutomata parser = LrParserConstructor.generateParser(context.getGrammar());
            long gp = currentTimeMillis();
            System.out.println("Parser description generated from grammar in " + (gp - gc) + "ms:\n\n");
            System.out.println(parser);
            System.out.println();
            System.out.println();
            new CodeGenerator(processingEnv.getFiler(), context).generateSources(parser);
            long gs = currentTimeMillis();
            System.out.println("Source code generated in " + (gs - gp) + "ms");
            environmentGenerator.generate(context, processingEnv.getFiler());
            long gl = currentTimeMillis();
            System.out.println("Env code generated in " + (gl - gs) + "ms");
        } catch (RuntimeException | Error | IOException e) {
            processingEnv.getMessager().printMessage(ERROR, e.toString(), element);
        }
    }

}
