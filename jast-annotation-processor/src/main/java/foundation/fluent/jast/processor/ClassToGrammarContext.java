package foundation.fluent.jast.processor;

import foundation.fluent.jast.generator.LrItemSet;
import foundation.fluent.jast.grammar.Grammar;
import foundation.fluent.jast.grammar.Rule;
import foundation.fluent.jast.grammar.Symbol;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.stream.Collectors;

import static foundation.fluent.jast.grammar.Rule.rule;
import static java.util.stream.Collectors.toList;
import static javax.lang.model.util.ElementFilter.methodsIn;

public class ClassToGrammarContext {

    private static final class TypeSymbol implements Symbol {
        private final TypeMirror typeMirror;

        private TypeSymbol(TypeMirror typeMirror) {
            this.typeMirror = typeMirror;
        }

        @Override
        public String toString() {
            String full = typeMirror.toString();
            String raw = full.split("<")[0];
            return raw.substring(raw.lastIndexOf(".") + 1) + Integer.toHexString(typeMirror.hashCode());
        }
    }

    private final Map<String, Symbol> symbolMap = new HashMap<>();
    private final Map<Rule, ExecutableElement> ruleAssociation = new HashMap<>();
    private final Grammar grammar;

    public ClassToGrammarContext(ExecutableElement startRule) {
        grammar = Grammar.grammar(
                of(startRule.getReturnType()),
                methodsIn(startRule.getEnclosingElement().getEnclosedElements()).stream().map(this::ruleOf).collect(Collectors.toCollection(LinkedHashSet::new))
        );
    }

    Symbol of(TypeMirror mirror) {
        return symbolMap.computeIfAbsent(mirror.toString(), key -> new TypeSymbol(mirror));
    }

    Rule ruleOf(ExecutableElement method) {
        Rule rule = rule(of(method.getReturnType()), method.getParameters().stream().map(par -> of(par.asType())).collect(toList()));
        ruleAssociation.put(rule, method);
        return rule;
    }

    public Grammar getGrammar() {
        return grammar;
    }

    public String stateClassName(LrItemSet set) {
        return set.getName();
    }

    public TypeMirror symbolType(Symbol symbol) {
        return ((TypeSymbol) symbol).typeMirror;
    }

    public ExecutableElement methodOf(Rule rule) {
        return ruleAssociation.get(rule);
    }

}
