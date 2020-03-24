package foundation.fluent.jast.processor;

import foundation.fluent.jast.generator.LrAction;
import foundation.fluent.jast.generator.LrItem;
import foundation.fluent.jast.generator.LrItemSet;
import foundation.fluent.jast.generator.LrParser;
import foundation.fluent.jast.grammar.Symbol;

import javax.annotation.processing.Filer;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
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
        try(PrintWriter w = new PrintWriter(filer.createSourceFile("foundation.fluent.jast.State").openWriter())) {
            w.println("package foundation.fluent.jast;");
            w.println("public class State implements foundation.fluent.jast.parser.StateBase {");
            w.println("\tpublic State visitAny(Object symbol) {");
            w.println("\t\tthrow new IllegalStateException(\"Unexpcted: \" + symbol + \". Expected: \");");
            w.println("\t}");
            for (Symbol symbol : context.getGrammar().getTerminals())
                w.println("\tpublic State visit(" + context.symbolType(symbol) + " symbol) {return visitAny(symbol);}");
            for (Symbol symbol : context.getGrammar().getNonTerminals())
                w.println("\tpublic State visit(" + context.symbolType(symbol) + " symbol) {return visitAny(symbol);}");
            w.println("}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateStackState() {
        try(PrintWriter w = new PrintWriter(filer.createSourceFile("foundation.fluent.jast.StackState").openWriter())) {
            w.println("package foundation.fluent.jast;");
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
        try(PrintWriter w = new PrintWriter(filer.createSourceFile("foundation.fluent.jast.State" + context.stateClassName(set)).openWriter())) {
            w.println("package foundation.fluent.jast;");
            w.println("/*\n" + parser + "\n*/\n\n");
            int dot = longest.getDot();
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
                        w.println("\t@Override public State visit(" + context.symbolType(entry.getKey()) + " symbol) {");
                        w.println("\t\treturn new State" + context.stateClassName(set) + "(symbol, this);");
                        w.println("\t}");
                    }
                    @Override public void visitReduction(LrItem item) {
                        ExecutableElement method = context.methodOf(item.getRule());
                        List<? extends VariableElement> parameters = method.getParameters();
                        int size = parameters.size();
                        w.println("\t@Override public State visit(" + context.symbolType(entry.getKey()) + " symbol) {");
                        if(parameters.isEmpty()) {
                            w.println("\t\treturn visit(" + methodName(method) + "()).visit(symbol);");
                        } else {
                            w.println("\t\t" + chainedVar(parameters, size) + " stack0 = this;");
                            for(int i = 1; i < size; i++) {
                                w.println("\t\t" + chainedVar(parameters, size - i) + " stack" + i + " = stack" + (i - 1) + ".getPrev();");
                            }
                            w.println("\t\treturn stack" + (size - 1) + ".getPrev().visit(" + methodName(method) + "(" + range(0, size).mapToObj(i -> "stack" + (size - i - 1) + ".getNode()").collect(joining(", ")) + ")).visit(symbol);");
                        }
                        w.println("\t}");
                    }
                    @Override public void visitAccept(LrItem item) {
                        w.println("\t@Override public boolean accepted() {");
                        w.println("\t\treturn true;");
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
