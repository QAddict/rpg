package foundation.rpg.sample.json;

import foundation.rpg.parser.Element;
import foundation.rpg.parser.UnexpectedInputException;

// Generated token element wrapper for grammar parser.
public class ElementString implements Element<State> {
    private final java.lang.String symbol;

    public ElementString(java.lang.String symbol) {
        this.symbol = symbol;
    }

    @Override
    public State accept(State state) throws UnexpectedInputException {
        return state.visitString(symbol);
    }

    @Override
    public String toString() {
        return symbol.toString();
    }
}
