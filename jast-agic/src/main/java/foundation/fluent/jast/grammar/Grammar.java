package foundation.fluent.jast.grammar;

import foundation.fluent.jast.util.MapOfSets;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

public class Grammar {

    private final Symbol start;
    private final Set<Symbol> terminals;
    private final Set<Symbol> nonTerminals;
    private final Set<Rule> rules;
    private final Set<Symbol> ignored;
    private final MapOfSets<Symbol, Rule> rulesBySymbol = new MapOfSets<>();

    public Grammar(Symbol start, Set<Symbol> terminals, Set<Symbol> nonTerminals, Set<Rule> rules, Set<Symbol> ignored) {
        this.start = start;
        this.terminals = terminals;
        this.nonTerminals = nonTerminals;
        this.rules = rules;
        this.ignored = ignored;
        rules.forEach(rule -> rulesBySymbol.add(rule.getLeft(), rule));
    }

    public static Grammar grammar(Symbol start, Set<Rule> rules, Set<Symbol> ignored) {
        Set<Symbol> nonTerminals = rules.stream().map(Rule::getLeft).collect(Collectors.toCollection(LinkedHashSet::new));
        Set<Symbol> terminals = rules.stream().flatMap(rule -> rule.getRight().stream()).filter(symbol -> !nonTerminals.contains(symbol)).collect(Collectors.toCollection(LinkedHashSet::new));
        return new Grammar(start, terminals, nonTerminals, rules, ignored);
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

    @Override
    public String toString() {
        return "N = " + nonTerminals + "\nT = " + terminals + "\nS = " + start + "\nR = {\n\t" + rules.stream().map(Objects::toString).collect(joining("\n\t")) + "\n}";
    }

    public Set<Symbol> getIgnored() {
        return ignored;
    }

}
