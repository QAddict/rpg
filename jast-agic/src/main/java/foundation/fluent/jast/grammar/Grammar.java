package foundation.fluent.jast.grammar;

import foundation.fluent.jast.util.MapOfSets;

import java.util.*;

import static java.util.stream.Collectors.toSet;

public class Grammar {

    private final Symbol start;
    private final Set<Symbol> terminals;
    private final Set<Symbol> nonTerminals;
    private final Set<Rule> rules;
    private final MapOfSets<Symbol, Rule> rulesBySymbol = new MapOfSets<>();

    public Grammar(Symbol start, Set<Symbol> terminals, Set<Symbol> nonTerminals, Set<Rule> rules) {
        this.start = start;
        this.terminals = terminals;
        this.nonTerminals = nonTerminals;
        this.rules = rules;
        rules.forEach(rule -> rulesBySymbol.add(rule.getLeft(), rule));
    }

    public static Grammar grammar(Symbol start, Set<Rule> rules) {
        Set<Symbol> nonTerminals = rules.stream().map(Rule::getLeft).collect(toSet());
        Set<Symbol> terminals = rules.stream().flatMap(rule -> rule.getRight().stream()).filter(symbol -> !nonTerminals.contains(symbol)).collect(toSet());
        return new Grammar(start, terminals, nonTerminals, rules);
    }

    public Symbol getStart() {
        return start;
    }

    public Set<Symbol> getTerminals() {
        return terminals;
    }

    public Set<Symbol> getNonTerminals() {
        return nonTerminals;
    }

    public Set<Rule> getRules() {
        return rules;
    }

    public Set<Rule> rulesFor(Symbol symbol) {
        return rulesBySymbol.get(symbol);
    }

}
