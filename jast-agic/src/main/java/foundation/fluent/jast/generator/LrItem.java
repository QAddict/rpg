package foundation.fluent.jast.generator;

import foundation.fluent.jast.grammar.Rule;
import foundation.fluent.jast.grammar.Symbol;

import java.util.Set;

public class LrItem implements Comparable<LrItem> {

    private final int dot;
    private final Rule rule;
    private final Set<Symbol> lookahead;

    public LrItem(int dot, Rule rule, Set<Symbol> lookahead) {
        this.dot = dot;
        this.rule = rule;
        this.lookahead = lookahead;
    }

    public Symbol symbolAtDot() {
        return rule.getRight().get(dot);
    }

    public boolean isEnd() {
        return dot >= rule.getRight().size();
    }

    public Set<Symbol> getLookahead() {
        return lookahead;
    }

    public LrItem moveDot() {
        return new LrItem(dot + 1, rule, lookahead);
    }

    @Override
    public int compareTo(LrItem o) {
        return dot - o.dot;
    }

}
