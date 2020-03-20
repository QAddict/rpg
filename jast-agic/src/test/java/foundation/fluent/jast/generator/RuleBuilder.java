package foundation.fluent.jast.generator;

import foundation.fluent.jast.grammar.Rule;
import foundation.fluent.jast.grammar.Symbol;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

public interface RuleBuilder {

    Rule to(Symbol... right);

    static RuleBuilder rule(Symbol left) {
        return right -> new Rule(left, asList(right));
    }

    static <T> Set<T> of(T... items) {
        return Stream.of(items).collect(Collectors.toSet());
    }
}
