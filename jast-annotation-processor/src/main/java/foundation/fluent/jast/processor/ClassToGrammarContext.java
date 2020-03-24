package foundation.fluent.jast.processor;

import foundation.fluent.jast.generator.LrItemSet;
import foundation.fluent.jast.grammar.Grammar;
import foundation.fluent.jast.grammar.Rule;
import foundation.fluent.jast.grammar.Symbol;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import java.util.*;
import java.util.stream.Stream;

import static foundation.fluent.jast.grammar.Rule.rule;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static javax.lang.model.util.ElementFilter.methodsIn;

public class ClassToGrammarContext {

    private final Set<String> usedNames = new HashSet<>();

    private String uniqueName(TypeMirror typeMirror) {
        String full = typeMirror.toString().replaceAll(">", "");
        String s = Stream.of(full.split("<")).map(p -> p.substring(p.lastIndexOf(".") + 1)).collect(joining("Of"));
        while(!usedNames.add(s)) {
            s = s + "$";
        }
        return s;
    }

    private static final class TypeSymbol implements Symbol {
        private final TypeMirror typeMirror;
        private final String uniqueName;

        private TypeSymbol(TypeMirror typeMirror, String uniqueName) {
            this.typeMirror = typeMirror;
            this.uniqueName = uniqueName;
        }

        @Override
        public String toString() {
            return uniqueName;
        }
    }

    private final Map<String, Symbol> symbolMap = new HashMap<>();
    private final Map<Rule, ExecutableElement> ruleAssociation = new HashMap<>();
    private final Grammar grammar;

    public ClassToGrammarContext(ExecutableElement startRule) {
        Set<Rule> rules = new LinkedHashSet<>();
        Set<Symbol> ignored = new LinkedHashSet<>();
        methodsIn(startRule.getEnclosingElement().getEnclosedElements()).forEach(method -> {
            if(method.getReturnType().getKind().equals(TypeKind.VOID)) {
                method.getParameters().forEach(p -> ignored.add(of(p.asType())));
            } else {
                rules.add(ruleOf(method));
            }
        });
        grammar = Grammar.grammar(
                of(startRule.getReturnType()),
                rules,
                ignored
        );
    }

    Symbol of(TypeMirror mirror) {
        return symbolMap.computeIfAbsent(mirror.toString(), key -> new TypeSymbol(mirror, uniqueName(mirror)));
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
