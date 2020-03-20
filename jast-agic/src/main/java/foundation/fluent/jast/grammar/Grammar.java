package foundation.fluent.jast.grammar;

import java.util.*;

import static java.util.Collections.emptySet;

public class Grammar {

    private final Symbol start;
    private final Set<Symbol> terminals;
    private final Set<Symbol> nonTerminals;
    private final Set<Rule> rules;
    private final Map<Symbol, Set<Rule>> rulesBySymbol = new HashMap<>();

    public Grammar(Symbol start, Set<Symbol> terminals, Set<Symbol> nonTerminals, Set<Rule> rules) {
        this.start = start;
        this.terminals = terminals;
        this.nonTerminals = nonTerminals;
        this.rules = rules;
        rules.forEach(rule -> rulesBySymbol.computeIfAbsent(rule.getLeft(), symbol -> new HashSet<>()).add(rule));
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
        return rulesBySymbol.getOrDefault(symbol, emptySet());
    }

}
