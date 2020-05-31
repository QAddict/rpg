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

import foundation.rpg.lr1.LrAction;
import foundation.rpg.lr1.LrItem;
import foundation.rpg.lr1.LrItemSet;
import foundation.rpg.lr1.LrParserAutomata;
import foundation.rpg.grammar.Grammar;
import foundation.rpg.grammar.Rule;
import foundation.rpg.grammar.Symbol;
import foundation.rpg.generator.parser.context.ClassToGrammarContext;

import javax.annotation.processing.Filer;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.stream.Stream;

import static foundation.rpg.generator.parser.SourceModel.Names.*;
import static java.lang.String.join;
import static java.util.Collections.max;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.*;
import static java.util.stream.Stream.concat;
import static javax.lang.model.element.Modifier.STATIC;

public class CodeGenerator {

    private final Filer filer;
    private final ClassToGrammarContext context;

    public CodeGenerator(Filer filer, ClassToGrammarContext context) {
        this.filer = filer;
        this.context = context;
    }

    public void generateSources(LrParserAutomata lrParser) {
        generateState(lrParser);
        write(source("StackState"));
        for(LrItemSet set : lrParser.getSets())
            generateState(lrParser, set);
        Stream.of(lrParser.getGrammar().getTerminals(), lrParser.getGrammar().getIgnored()).flatMap(Collection::stream)
                .forEach(s -> write(source("Element$name$").set(name, s).set(type, typeOf(s))));
        SourceModel parserSource = source("$parser$").set("parser", context.getParserClass()).set(result, typeOf(lrParser.getGrammar().getStart()));
        write(parserSource);
    }

    private String chainedType(List<TypeMirror> parameters, int length, int noWildcard) {
        return length > 0 ? "StackState<" + parameters.get(length - 1) + ", " + chainedType(parameters, length - 1, noWildcard - 1) + ">" : noWildcard > 0 ? "State" : "? extends State";
    }

    private String chainedVar(List<TypeMirror> parameters, int length) {
        return chainedType(parameters, length, 1);
    }
    private String chainedType(List<TypeMirror> parameters, int length) {
        return chainedType(parameters, length, 2);
    }

    private void generateState(LrParserAutomata parser) {
        Grammar g = parser.getGrammar();
        SourceModel code = source("State").set(SourceModel.Names.grammar, g).set(automata, parser).set(result, typeOf(g.getStart()));
        g.getIgnored().forEach(s -> code.with(Ignored).set(name, s).set(type, typeOf(s)));
        concat(g.getTerminals().stream(), g.getNonTerminals().stream()).filter(s -> !s.equals(g.getStart())).forEach(s -> code.with(Symbols).set(name, s).set(type, typeOf(s)));
        write(code);
    }

    private void write(SourceModel source) {
        String string = source.toString();
        try(Writer writer = filer.createSourceFile(source.get("package") + "." + source.get("class")).openWriter()) {
            writer.write(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private SourceModel source(String name) {
        return SourceModel.source(context.getPackageName(), context.isStaticFactory(), name).set(factory, context.getFactoryClass());
    }

    private String methodName(ExecutableElement method) {
        return isNull(method) ? "" : (method.getModifiers().contains(STATIC) ? method.getEnclosingElement() : "getFactory()") + "." + method.getSimpleName();
    }

    private TypeMirror typeOf(Symbol symbol) {
        return context.symbolType(symbol);
    }

    private void generateState(LrParserAutomata parser, LrItemSet set) {
        LrItem longest = max(set.getItems());
        int dot = longest.getDot();
        List<TypeMirror> longestParameters = longest.getRule().getRight().stream().map(context::symbolType).collect(toList());
        String superClass = dot > 0 ? chainedType(longestParameters, dot) : "State";
        SourceModel code = source("State$item$").set(item, set.getName()).set(lrItem, set).set(parent, superClass);
        if (dot > 0)
            code.with(Stack).set(node, longestParameters.get(dot - 1)).set(prev, chainedVar(longestParameters, dot - 1));
        else
            code.with(NoStack);

        parser.actionsFor(set).forEach((symbol, action) -> action.accept(new LrAction.LrActionVisitor() {
            @Override public void visitGoto(LrItemSet set) {
                code.with(Shift).set(name, symbol).set(type, typeOf(symbol)).set(next, set.getName());
            }

            @Override public void visitReduction(LrItem item) {
                Rule rule = item.getRule();
                SourceModel fragment = code.with(Reduce).set(name, symbol).set(type, typeOf(symbol)).set(result, rule.getLeft());
                String state = visitCall(item, fragment);
                fragment.set(start, state);
            }

            private String visitCall(LrItem item, SourceModel fragment) {
                Rule rule = item.getRule();
                ExecutableElement method = context.methodOf(rule);
                int size = isNull(method) ? 1 : method.getParameters().size();
                String state = "this";
                StringJoiner b = new StringJoiner("\n\t\t");
                String[] p = new String[size];
                for(int i = 1; i <= size; i++) {
                    p[size - i] = state + ".getNode()";
                    b.add(chainedVar(longestParameters, dot - i) + " stack" + i + " = " + state + ".getPrev();");
                    state = "stack" + i;
                }
                String call = methodName(method) + "(" + join(", ", p) + ")";
                fragment.set(factoryCall, call).set(parameters, b);
                return state;
            }

            @Override public void visitAccept(LrItem item) {
                code.with(Accept).set(result, typeOf(parser.getGrammar().getStart()));
            }
        }));
        write(code);
    }

}
