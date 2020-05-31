package foundation.rpg.sample.json;

import foundation.rpg.parser.Element;
import foundation.rpg.parser.UnexpectedInputException;

// Generated token element wrapper for grammar parser.
public class ElementColon implements Element<State> {
    private final foundation.rpg.common.symbols.Colon symbol;

    public ElementColon(foundation.rpg.common.symbols.Colon symbol) {
        this.symbol = symbol;
    }

    @Override
    public State accept(State state) throws UnexpectedInputException {
        return state.visitColon(symbol);
    }

    @Override
    public String toString() {
        return symbol.toString();
    }
}
