package foundation.fluent.jast.sample.language;

import foundation.fluent.jast.State;
import foundation.fluent.jast.State1;
import foundation.fluent.jast.parser.Parser;
import org.testng.annotations.Test;

import java.util.function.UnaryOperator;

public class LanguageParserTest {

    static class Token implements UnaryOperator<State> {

        @Override
        public State apply(State state) {
            return null;
        }
    }
    @Test
    public void testParser() {
        new Parser<State, Token>(new State1());
    }

}
