package foundation.fluent.jast.generator;

import foundation.fluent.jast.grammar.Symbol;

import java.util.*;

public class LrItemSet {

    private final SortedSet<LrItem> closure;
    private final Map<Symbol, Set<LrItem>> transitions = new HashMap<>();

    public LrItemSet(Set<LrItem> base) {
        this.closure = new TreeSet<>(base);
    }

    public boolean add(LrItem item) {
        return this.closure.add(item);
    }

    public Set<Symbol> transitionSymbols() {
        return transitions.keySet();
    }

    public Set<LrItem> itemsFollowing(Symbol symbol) {
        return Collections.emptySet();
    }

}
