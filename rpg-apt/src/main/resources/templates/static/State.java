package $package$;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.StateBase;

// Generated visitor pattern based state for grammar parser.
public class $class$ extends StateBase<$result$> {

// Ignored:
    public State visit$name$($type$ symbol) {
        return this;
    }

// Symbols:
    public State visit$name$($type$ symbol) throws UnexpectedInputException {
        return error(symbol);
    }

}
