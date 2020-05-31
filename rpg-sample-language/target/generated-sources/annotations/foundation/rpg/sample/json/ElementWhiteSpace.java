package foundation.rpg.sample.json;

import foundation.rpg.parser.Element;
import foundation.rpg.parser.UnexpectedInputException;

// Generated token element wrapper for grammar parser.
public class ElementWhiteSpace implements Element<State> {
    private final foundation.rpg.common.symbols.WhiteSpace symbol;

    public ElementWhiteSpace(foundation.rpg.common.symbols.WhiteSpace symbol) {
        this.symbol = symbol;
    }

    @Override
    public State accept(State state) throws UnexpectedInputException {
        return state.visitWhiteSpace(symbol);
    }

    @Override
    public String toString() {
        return symbol.toString();
    }
}
