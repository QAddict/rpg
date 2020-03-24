package foundation.fluent.jast.generator;

import foundation.fluent.jast.grammar.Symbol;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

public class LrParser {

    private final LrItemSet start;
    private final Map<LrItemSet, LrItemSet> sets = new LinkedHashMap<>();
    private final Map<LrItemSet, Map<Symbol, LrAction>> actions = new LinkedHashMap<>();

    public LrParser(LrItemSet start) {
        this.start = start;
    }

    public boolean addState(LrItemSet itemSet) {
        return sets.putIfAbsent(itemSet, itemSet) == null;
    }

    public LrItemSet getStart() {
        return start;
    }

    public Set<LrItemSet> getSets() {
        return sets.keySet();
    }

    private void action(LrItemSet from, Symbol symbol, LrAction action) {
        Map<Symbol, LrAction> actionMap = actions.computeIfAbsent(from, k -> new HashMap<>());
        if(actionMap.containsKey(symbol)) throw new IllegalStateException("Conflict at: " + from + " for symbol: " + symbol + "\n\nCurrent parser state:\n" + this);
        actionMap.put(symbol, action);
    }

    public void transition(LrItemSet from, Symbol symbol, LrItemSet to) {
        action(from, symbol, new LrAction.Goto(sets.get(to)));
    }

    public void reduction(LrItemSet from, Symbol lookahead, LrItem item) {
        action(from, lookahead, new LrAction.Reduce(item));

    }

    public Map<Symbol, LrAction> actionsFor(LrItemSet set) {
        return actions.get(set);
    }

    @Override
    public String toString() {
        return getSets().stream().map(Object::toString).collect(joining("\n")) + "\n\n" +
                actions.entrySet().stream().flatMap(entry -> entry.getValue().entrySet().stream().map(transition -> entry.getKey().getName() + ": " + transition.getKey() + " -> " + transition.getValue())).collect(Collectors.joining("\n"));
    }

    public void accept(LrItemSet itemSet, LrItem lrItem) {
        action(itemSet, Symbol.any, new LrAction.Accept(lrItem));
    }
}
