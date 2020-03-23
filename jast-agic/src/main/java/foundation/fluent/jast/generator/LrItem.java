package foundation.fluent.jast.generator;

import foundation.fluent.jast.grammar.Rule;
import foundation.fluent.jast.grammar.Symbol;

import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

public final class LrItem implements Comparable<LrItem> {

    private final int dot;
    private final Rule rule;
    private final Set<Symbol> lookahead;

    private LrItem(int dot, Rule rule, Set<Symbol> lookahead) {
        this.dot = dot;
        this.rule = rule;
        this.lookahead = lookahead;
    }

    public static LrItem lrItem(Rule rule, Set<Symbol> lookahead) {
        return new LrItem(0, rule, lookahead);
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

    public int getDot() {
        return dot;
    }

    public Rule getRule() {
        return rule;
    }

    public LrItem moveDot() {
        return new LrItem(dot + 1, rule, lookahead);
    }

    @Override
    public int compareTo(LrItem o) {
        return dot - o.dot;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LrItem lr1Item = (LrItem) o;
        return dot == lr1Item.dot && Objects.equals(rule, lr1Item.rule) && Objects.equals(lookahead, lr1Item.lookahead);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dot, rule, lookahead);
    }

    @Override
    public String toString() {
        StringJoiner j = new StringJoiner(" ");
        for(int i = 0; i < rule.getRight().size(); i++) {
            if(i == dot) j.add("•");
            j.add(rule.getRight().get(i).toString());
        }
        if(dot == rule.getRight().size())
            j.add("•");
        return rule.getLeft() + " -> " + j + " " + lookahead;
    }

}
