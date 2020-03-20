package foundation.fluent.jast.generator;

import foundation.fluent.jast.grammar.Grammar;
import foundation.fluent.jast.grammar.Symbol;

import java.util.*;

import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toSet;

public class Generator {

    private final Grammar grammar;

    private final Map<Symbol, Set<Symbol>> first = new HashMap<>();

    public Generator(Grammar grammar) {
        this.grammar = grammar;
        countFirst();
    }

    private void countFirst() {
        grammar.getNonTerminals().forEach(symbol -> {
            grammar.rulesFor(symbol).forEach(l -> {
                first.computeIfAbsent(symbol, s -> new HashSet<>()).add(l.getRight().get(0));
            });
        });
    }

    public Set<LrItemSet> parserStates() {
        Set<LrItemSet> itemSets = new HashSet<>();
        Queue<LrItemSet> processingSets = new LinkedList<>();
        processingSets.add(itemSet(grammar.rulesFor(grammar.getStart()).stream().map(rule -> new LrItem(0, rule, emptySet())).collect(toSet())));
        while(!processingSets.isEmpty()) {
            LrItemSet set = processingSets.poll();
            Map<Symbol, Queue<LrItem>> subsets = new HashMap<>();
            set.transitionSymbols().forEach(symbol -> {
                LrItemSet nextSet = itemSet(set.itemsFollowing(symbol));
                if(itemSets.add(nextSet)) {
                    processingSets.add(nextSet);
                }
            });
        }
        return itemSets;
    }

    public LrParser parser() {
        return new LrParser(null, parserStates());
    }

    public LrItemSet itemSet(Set<LrItem> base) {
        LrItemSet lr1ItemSet = new LrItemSet(base);
        Queue<LrItem> queue = new ArrayDeque<>(base);
        while(!queue.isEmpty()) {
            LrItem item = queue.poll();
            if(lr1ItemSet.add(item) && !item.isEnd()) {
                grammar.rulesFor(item.symbolAtDot()).stream().map(rule -> new LrItem(0, rule, follow(item))).forEach(queue::add);
            }
        }
        return lr1ItemSet;
    }

    public Set<Symbol> follow(LrItem item) {
        item = item.moveDot();
        return item.isEnd() ? item.getLookahead() : first.computeIfAbsent(item.symbolAtDot(), Collections::singleton);
    }

    public static LrParser generateParser(Grammar grammar) {
        return new Generator(grammar).parser();
    }

}
