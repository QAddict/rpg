package foundation.rpg.sample.json;

import foundation.rpg.parser.Element;
import foundation.rpg.parser.UnexpectedInputException;

// Generated token element wrapper for grammar parser.
public class ElementLCurl implements Element<State> {
    private final foundation.rpg.common.symbols.LCurl symbol;

    public ElementLCurl(foundation.rpg.common.symbols.LCurl symbol) {
        this.symbol = symbol;
    }

    @Override
    public State accept(State state) throws UnexpectedInputException {
        return state.visitLCurl(symbol);
    }

    @Override
    public String toString() {
        return symbol.toString();
    }
}
