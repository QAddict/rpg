package foundation.rpg.sample.json;

import foundation.rpg.parser.Element;
import foundation.rpg.parser.UnexpectedInputException;

// Generated token element wrapper for grammar parser.
public class ElementRCurl implements Element<State> {
    private final foundation.rpg.common.symbols.RCurl symbol;

    public ElementRCurl(foundation.rpg.common.symbols.RCurl symbol) {
        this.symbol = symbol;
    }

    @Override
    public State accept(State state) throws UnexpectedInputException {
        return state.visitRCurl(symbol);
    }

    @Override
    public String toString() {
        return symbol.toString();
    }
}
