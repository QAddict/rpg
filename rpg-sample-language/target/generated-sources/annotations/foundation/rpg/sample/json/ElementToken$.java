package foundation.rpg.sample.json;

import foundation.rpg.parser.Element;
import foundation.rpg.parser.UnexpectedInputException;

// Generated token element wrapper for grammar parser.
public class ElementToken$ implements Element<State> {
    private final foundation.rpg.parser.Token symbol;

    public ElementToken$(foundation.rpg.parser.Token symbol) {
        this.symbol = symbol;
    }

    @Override
    public State accept(State state) throws UnexpectedInputException {
        return state.visitToken$(symbol);
    }

    @Override
    public String toString() {
        return symbol.toString();
    }
}
