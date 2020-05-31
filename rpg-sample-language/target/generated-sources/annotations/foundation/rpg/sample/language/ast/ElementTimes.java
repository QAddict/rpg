package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.Element;
import foundation.rpg.parser.UnexpectedInputException;

// Generated token element wrapper for grammar parser.
public class ElementTimes implements Element<State> {
    private final foundation.rpg.common.symbols.Times symbol;

    public ElementTimes(foundation.rpg.common.symbols.Times symbol) {
        this.symbol = symbol;
    }

    @Override
    public State accept(State state) throws UnexpectedInputException {
        return state.visitTimes(symbol);
    }

    @Override
    public String toString() {
        return symbol.toString();
    }
}
