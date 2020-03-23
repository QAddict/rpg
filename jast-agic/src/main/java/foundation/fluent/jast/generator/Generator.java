package foundation.fluent.jast.generator;

import foundation.fluent.jast.grammar.Grammar;
import foundation.fluent.jast.grammar.Symbol;
import foundation.fluent.jast.util.MapOfSets;

import java.util.*;

import static foundation.fluent.jast.generator.LrItem.lrItem;
import static java.util.Collections.emptySet;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toSet;

public class Generator {

    private final Grammar grammar;

    private final MapOfSets<Symbol, Symbol> first = new MapOfSets<>();
    private final Map<Symbol, Integer> counters = new HashMap<>();

    public Generator(Grammar grammar) {
        this.grammar = grammar;
        countFirst();
    }

    private void countFirst() {
        grammar.getNonTerminals().forEach(symbol -> {
            grammar.rulesFor(symbol).stream().filter(r -> r.getRight().size() > 0).map(r -> r.getRight().get(0)).filter(grammar.getTerminals()::contains).forEach(l -> {
                first.add(symbol, l);
            });
        });
        grammar.getTerminals().forEach(symbol -> first.add(symbol, symbol));
    }

    public LrParser parser() {
        Queue<LrItemSet> processingSets = new LinkedList<>();
        LrItemSet start = closure("InitialState", grammar.rulesFor(grammar.getStart()).stream().map(rule -> lrItem(rule, emptySet())).collect(toSet()));
        LrParser parser = new LrParser(start);
        parser.addState(start);
        processingSets.add(start);
        while(!processingSets.isEmpty()) {
            LrItemSet set = processingSets.poll();
            MapOfSets<Symbol, LrItem> transitions = new MapOfSets<>();
            set.getItems().forEach(lrItem -> {
                if(lrItem.isEnd()) {
                    if(lrItem.getLookahead().isEmpty()) {
                        parser.accept(set, lrItem);
                    } else {
                        lrItem.getLookahead().forEach(lookahead -> parser.reduction(set, lookahead, lrItem));
                    }
                } else {
                    transitions.add(lrItem.symbolAtDot(), lrItem.moveDot());
                }
            });
            transitions.forEach((transitionSymbol, baseSet) -> {
                LrItemSet nextSet = closure("" + transitionSymbol + counters.compute(transitionSymbol, (s, i) -> isNull(i) ? 1 : i+1), baseSet);
                parser.transition(set, transitionSymbol, nextSet);
                if(parser.addState(nextSet)) {
                    processingSets.add(nextSet);
                }
            });
        }
        return parser;
    }

    public LrItemSet closure(String name, Set<LrItem> base) {
        LrItemSet lr1ItemSet = new LrItemSet(name);
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
