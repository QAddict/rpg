package foundation.fluent.jast.grammar;

import java.util.ArrayList;
import java.util.List;

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

}
