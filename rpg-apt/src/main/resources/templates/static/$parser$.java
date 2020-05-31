package $package$;

import foundation.rpg.parser.Lexer;
import foundation.rpg.parser.StreamParser;

import java.util.function.Consumer;

// Generated token element wrapper for grammar parser.
public class $class$ extends StreamParser<$result$, State> {

    public $class$(Lexer<State> lexer) {
        super(new State1(), lexer);
    }

    public $class$() {
        this(new GeneratedLexer());
    }

}
