package foundation.fluent.jast.generator;

import foundation.fluent.jast.grammar.Grammar;
import foundation.fluent.jast.grammar.Symbol;
import org.testng.annotations.Test;

import java.util.Collections;

import static foundation.fluent.jast.generator.GeneratorTest.Symbols.*;
import static foundation.fluent.jast.generator.RuleBuilder.of;
import static foundation.fluent.jast.generator.RuleBuilder.rule;
import static java.util.Collections.emptySet;

public class GeneratorTest {

    public enum Symbols implements Symbol { S, A, a }

    @Test
    public void testParser() {
        Grammar grammar = new Grammar(S, of(ε, a), of(S, A), of(
                rule(S).to(A, ε),
                rule(A).to(a, A)
        ), emptySet());
        LrParser parser = Generator.generateParser(grammar);
        System.out.println(parser);
    }
}