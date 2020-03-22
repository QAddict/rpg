package foundation.fluent.jast.generator;

import foundation.fluent.jast.grammar.Grammar;
import foundation.fluent.jast.grammar.Symbol;
import foundation.fluent.jast.util.MapOfSets;

import java.util.*;

import static foundation.fluent.jast.generator.LrItem.lrItem;
import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toSet;

public class Generator {

    private final Grammar grammar;

    private final MapOfSets<Symbol, Symbol> first = new MapOfSets<>();

    public Generator(Grammar grammar) {
        this.grammar = grammar;
        countFirst();
    }

    private void countFirst() {
        grammar.getNonTerminals().forEach(symbol -> {
            grammar.rulesFor(symbol).forEach(l -> {
                first.add(symbol, l.getRight().get(0));
            });
        });
    }

    public Set<LrItemSet> parserStates() {
        Set<LrItemSet> itemSets = new HashSet<>();
        Queue<LrItemSet> processingSets = new LinkedList<>();
        LrItemSet start = closure(grammar.rulesFor(grammar.getStart()).stream().map(rule -> lrItem(rule, emptySet())).collect(toSet()));
        itemSets.add(start);
        processingSets.add(start);
        while(!processingSets.isEmpty()) {
            LrItemSet set = processingSets.poll();
            MapOfSets<Symbol, LrItem> transitions = new MapOfSets<>();
            set.getItems().forEach(lrItem -> {
                if(lrItem.isEnd()) {

                } else {
                    transitions.add(lrItem.symbolAtDot(), lrItem.moveDot());
                }
            });
            transitions.forEach((transitionSymbol, baseSet) -> {
                LrItemSet nextSet = closure(baseSet);
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

    public LrItemSet closure(Set<LrItem> base) {
        LrItemSet lr1ItemSet = new LrItemSet();
        Queue<LrItem> queue = new ArrayDeque<>(base);
        while(!queue.isEmpty()) {
            LrItem item = queue.poll();
            if(lr1ItemSet.add(item) && !item.isEnd()) {
                grammar.rulesFor(item.symbolAtDot()).stream().map(rule -> lrItem(rule, follow(item))).forEach(queue::add);
            }
        }
        return lr1ItemSet;
    }

    public Set<Symbol> follow(LrItem item) {
        item = item.moveDot();
        return item.isEnd() ? item.getLookahead() : first.get(item.symbolAtDot());
    }

    public static LrParser generateParser(Grammar grammar) {
        return new Generator(grammar).parser();
    }

}
