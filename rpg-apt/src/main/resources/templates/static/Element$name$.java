package $package$;

import foundation.rpg.parser.Element;
import foundation.rpg.parser.UnexpectedInputException;

// Generated token element wrapper for grammar parser.
public class $class$ implements Element<State> {
    private final $type$ symbol;

    public $class$($type$ symbol) {
        this.symbol = symbol;
    }

    @Override
    public State accept(State state) throws UnexpectedInputException {
        return state.visit$name$(symbol);
    }

    @Override
    public String toString() {
        return symbol.toString();
    }
}
