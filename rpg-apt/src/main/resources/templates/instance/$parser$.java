package $package$;

import foundation.rpg.parser.Lexer;
import foundation.rpg.parser.StreamParser;

import java.util.function.Consumer;

// Generated token element wrapper for grammar parser.
public class $class$ extends StreamParser<$result$, State> {

    public $class$($factory$ factory, Lexer<State> lexer) {
        super(new State1(factory), lexer);
    }

    public $class$($factory$ factory) {
        this(factory, new GeneratedLexer(factory));
    }

}
