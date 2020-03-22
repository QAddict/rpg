package foundation.fluent.jast.grammar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Collections.unmodifiableList;

public final class Rule {

    private final Symbol left;
    private final List<Symbol> right;

    public Rule(Symbol left, List<Symbol> right) {
        this.left = left;
        this.right = unmodifiableList(new ArrayList<>(right));
    }

    public Symbol getLeft() {
        return left;
    }

    public List<Symbol> getRight() {
        return right;
    }

    public static Rule rule(Symbol left, List<Symbol> right) {
        return new Rule(left, right);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rule rule = (Rule) o;
        return Objects.equals(left, rule.left) && Objects.equals(right, rule.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    @Override
    public String toString() {
        return left + " -> " + right;
    }

}
