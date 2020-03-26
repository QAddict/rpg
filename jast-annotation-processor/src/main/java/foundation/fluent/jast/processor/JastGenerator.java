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

package foundation.fluent.jast.processor;

import foundation.fluent.jast.parser.generator.LrAction;
import foundation.fluent.jast.parser.generator.LrItem;
import foundation.fluent.jast.parser.generator.LrItemSet;
import foundation.fluent.jast.parser.generator.LrParser;
import foundation.fluent.jast.parser.grammar.Symbol;

import javax.annotation.processing.Filer;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.joining;
import static java.util.stream.IntStream.range;

public class JastGenerator {

    private final Filer filer;
    private final ClassToGrammarContext context;

    public JastGenerator(Filer filer, ClassToGrammarContext context) {
        this.filer = filer;
        this.context = context;
    }

    public void generateSources(LrParser lrParser) {
        generateState();
        generateStackState();
        for(LrItemSet set : lrParser.getSets()) {
            if(set.equals(lrParser.getStart())) {
                generateState(lrParser, set);
            } else {
                generateState(lrParser, set);
            }
        }
        for(Symbol token : context.getGrammar().getTerminals()) {
            generateToken(token);
        }
        for(Symbol token : context.getGrammar().getIgnored()) {
            generateToken(token);
        }
    }

    private PrintWriter writer(String className) throws IOException {
        return new PrintWriter(filer.createSourceFile(context.getPackageName() + "." + className).openWriter());
    }

    private void pkg(PrintWriter w) {
        w.println("package " + context.getPackageName() + ";");
    }

    private void generateToken(Symbol symbol) {
        try(PrintWriter w = writer("Token" + symbol)) {
            pkg(w);
            w.println();
            w.println("import java.util.function.UnaryOperator;");
            w.println("import javax.annotation.Generated;");
            w.println();
            w.println("@Generated(\"Generated token element wrapper for grammar parser.\")");
            w.println("public class Token" + symbol + " implements UnaryOperator<State> {");
            w.println("    private final " + context.symbolType(symbol) + " symbol;");
            w.println();
            w.println("    public Token" + symbol + "(" + context.symbolType(symbol) + " symbol) {");
            w.println("        this.symbol = symbol;");
            w.println("    }");
            w.println();
            w.println("    @Override public State apply(State state) {");
            w.println("        return state.visit" + symbol + "(symbol);");
            w.println("    }");
            w.println("}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String chainedType(List<? extends VariableElement> parameters, int length, int noWildcard) {
        return length > 0 ? "StackState<" + parameters.get(length - 1).asType() + ", " + chainedType(parameters, length - 1, noWildcard - 1) + ">" : noWildcard > 0 ? "State" : "? extends State";
    }

    private String chainedVar(List<? extends VariableElement> parameters, int length) {
        return chainedType(parameters, length, 1);
    }
    private String chainedType(List<? extends VariableElement> parameters, int length) {
        return chainedType(parameters, length, 2);
    }

    private void generateState() {
        try(PrintWriter w = writer("State")) {
            pkg(w);
            w.println();
            w.println("import javax.annotation.Generated;");
            w.println();
            w.println("@Generated(\"Generated visitor pattern based state for grammar parser.\")");
            w.println("public class State extends foundation.fluent.jast.parser.StateBase {");
            for (Symbol symbol : context.getGrammar().getIgnored())
                w.println("\tpublic State visit" + symbol + "(" + context.symbolType(symbol) + " symbol) {\n\t\treturn this;\n\t}\n");
            for (Symbol symbol : context.getGrammar().getTerminals())
                w.println("\tpublic State visit" + symbol + "(" + context.symbolType(symbol) + " symbol) {\n\t\treturn error(symbol);\n\t}\n");
            for (Symbol symbol : context.getGrammar().getNonTerminals())
                w.println("\tpublic State visit" + symbol + "(" + context.symbolType(symbol) + " symbol) {\n\t\treturn error(symbol);\n\t}\n");
            w.println("\tpublic " + context.symbolType(context.getGrammar().getStart()) + " result() {\n\t\tthrow new IllegalStateException(\"End not reached.\");\n\t}");
            w.println("}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateStackState() {
        try(PrintWriter w = writer("StackState")) {
            pkg(w);
            w.println("public class StackState<T, P> extends State {");
            w.println();
            w.println("\tprivate final T node;");
            w.println("\tprivate final P prev;");
            w.println();
            w.println("\tpublic StackState(T node, P prev) {\n\t\tthis.node = node;\n\t\tthis.prev = prev;\n\t}");
            w.println();
            w.println("\tpublic final T getNode() {\n\t\treturn node;\n\t}");
            w.println();
            w.println("\tpublic final P getPrev() {\n\t\treturn prev;\n\t}");
            w.println();
            w.println("}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String methodName(ExecutableElement method) {
        return method.getEnclosingElement() + "." + method.getSimpleName();
    }

    private void generateState(LrParser parser, LrItemSet set)  {
        LrItem longest = Collections.max(set.getItems());
        try(PrintWriter w = writer("State" + context.stateClassName(set))) {
            pkg(w);
            w.println("/*\n" + parser + "\n*/\n\n");
            int dot = longest.getDot();
            w.println("import javax.annotation.Generated;");
            w.println();
            w.println("@Generated(\"Generated visitor pattern based state for grammar parser.\")");
            if(dot > 0) {
                List<? extends VariableElement> parameters = context.methodOf(longest.getRule()).getParameters();
                w.println("public class State" + set.getName() + " extends " + chainedType(parameters, dot) + " {");
                w.println("    public State" + set.getName() + "(" + parameters.get(dot - 1).asType() + " node, " + chainedVar(parameters, dot - 1) + " prev) {");
                w.println("        super(node, prev);");
                w.println("    }");
            } else {
                w.println("public class State" + set.getName() + " extends State {");
            }
            for(Map.Entry<Symbol, LrAction> entry : parser.actionsFor(set).entrySet()) {
                entry.getValue().accept(new LrAction.LrActionVisitor() {
                    @Override public void visitGoto(LrItemSet set) {
                        w.println("\t@Override public State visit" + entry.getKey() + "(" + context.symbolType(entry.getKey()) + " symbol) {");
                        w.println("\t\treturn new State" + context.stateClassName(set) + "(symbol, this);");
                        w.println("\t}");
                    }
                    @Override public void visitReduction(LrItem item) {
                        ExecutableElement method = context.methodOf(item.getRule());
                        List<? extends VariableElement> parameters = method.getParameters();
                        int size = parameters.size();
                        w.println("\t@Override public State visit" + entry.getKey() +"(" + context.symbolType(entry.getKey()) + " symbol) {");
                        if(parameters.isEmpty()) {
                            w.println("\t\treturn visit" + context.of(method.getReturnType()) + "(" + methodName(method) + "()).visit" + entry.getKey() + "(symbol);");
                        } else {
                            w.println("\t\t" + chainedVar(parameters, size) + " stack0 = this;");
                            for(int i = 1; i < size; i++) {
                                w.println("\t\t" + chainedVar(parameters, size - i) + " stack" + i + " = stack" + (i - 1) + ".getPrev();");
                            }
                            w.println("\t\treturn stack" + (size - 1) + ".getPrev().visit" + context.of(method.getReturnType()) + "(" + methodName(method) + "(" + range(0, size).mapToObj(i -> "stack" + (size - i - 1) + ".getNode()").collect(joining(", ")) + ")).visit" + entry.getKey() + "(symbol);");
                        }
                        w.println("\t}");
                    }
                    @Override public void visitAccept(LrItem item) {
                        ExecutableElement method = context.methodOf(item.getRule());
                        List<? extends VariableElement> parameters = method.getParameters();
                        int size = parameters.size();
                        w.println("\t@Override public boolean accepted() {");
                        w.println("\t\treturn true;");
                        w.println("\t}");
                        w.println();
                        w.println("\tpublic " + context.symbolType(context.getGrammar().getStart()) + " result() {");
                        w.println("\t\t" + chainedVar(parameters, size) + " stack0 = this;");
                        for(int i = 1; i < size; i++) {
                            w.println("\t\t" + chainedVar(parameters, size - i) + " stack" + i + " = stack" + (i - 1) + ".getPrev();");
                        }
                        w.println("\t\treturn " + methodName(method) + "(" + range(0, size).mapToObj(i -> "stack" + (size - i - 1) + ".getNode()").collect(joining(", ")) + ");");
                        w.println("\t}");
                    }
                });
            }
            w.println("}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
