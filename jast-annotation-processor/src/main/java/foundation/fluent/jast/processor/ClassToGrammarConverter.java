package foundation.fluent.jast.processor;

import foundation.fluent.jast.grammar.Grammar;
import foundation.fluent.jast.grammar.Rule;
import foundation.fluent.jast.grammar.Symbol;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;

import java.util.HashMap;
import java.util.Map;

import static foundation.fluent.jast.grammar.Rule.rule;
import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static javax.lang.model.util.ElementFilter.methodsIn;

public class ClassToGrammarConverter {

    private final Map<String, Symbol> symbolMap = new HashMap<>();

    Symbol of(TypeMirror mirror) {
        return symbolMap.computeIfAbsent(mirror.toString(), key -> new Symbol() {});
    }

    Rule ruleOf(ExecutableElement method) {
        return rule(of(method.getReturnType()), method.getParameters().stream().map(par -> of(par.asType())).collect(toList()));
    }

    Grammar grammar(ExecutableElement startRule) {
        return new Grammar(
                of(startRule.getReturnType()),
                emptySet(),
                emptySet(),
                methodsIn(startRule.getEnclosingElement().getEnclosedElements()).stream().map(this::ruleOf).collect(toSet())
        );
    }

    public static Grammar classToGrammar(ExecutableElement startRule) {
        return new ClassToGrammarConverter().grammar(startRule);
    }

}
