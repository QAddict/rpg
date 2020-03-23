package foundation.fluent.jast.generator;

import foundation.fluent.jast.grammar.Symbol;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

public class LrParser {

    private final LrItemSet start;
    private final Set<LrItemSet> sets = new HashSet<>();
    private final Map<LrItemSet, Map<Symbol, LrAction>> actions = new HashMap<>();

    public LrParser(LrItemSet start) {
        this.start = start;
    }

    public boolean addState(LrItemSet itemSet) {
        return sets.add(itemSet);
    }

    public LrItemSet getStart() {
        return start;
    }

    public Set<LrItemSet> getSets() {
        return sets;
    }

    private void action(LrItemSet from, Symbol symbol, LrAction action) {
        Map<Symbol, LrAction> actionMap = actions.computeIfAbsent(from, k -> new HashMap<>());
        if(actionMap.containsKey(symbol)) throw new IllegalStateException("Conflict at: " + from + " for symbol: " + symbol);
        actionMap.put(symbol, action);
    }

    public void transition(LrItemSet from, Symbol symbol, LrItemSet to) {
        action(from, symbol, new LrAction.Goto(to));
    }

    public void reduction(LrItemSet from, Symbol lookahead, LrItem item) {
        action(from, lookahead, new LrAction.Reduce(item));

    }

    @Override
    public String toString() {
        return sets.stream().map(Object::toString).collect(joining("\n")) + "\n\n" +
                actions.entrySet().stream().flatMap(entry -> entry.getValue().entrySet().stream().map(transition -> entry.getKey().getName() + ": " + transition.getKey() + " -> " + transition.getValue())).collect(Collectors.joining("\n"));
    }

    public void accept(LrItemSet itemSet, LrItem lrItem) {
        action(itemSet, new Symbol() {
            @Override public String toString() { return ""; }
        }, new LrAction.Accept(lrItem));
    }
}
