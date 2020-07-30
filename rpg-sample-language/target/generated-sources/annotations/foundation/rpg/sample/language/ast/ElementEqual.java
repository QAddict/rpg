package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.Element;
import foundation.rpg.parser.UnexpectedInputException;

// Generated token element wrapper for grammar parser.
public class ElementEqual implements Element<State> {
    private final foundation.rpg.common.symbols.Equal symbol;

    public ElementEqual(foundation.rpg.common.symbols.Equal symbol) {
        this.symbol = symbol;
    }

    @Override
    public State accept(State state) throws UnexpectedInputException {
        return state.visitEqual(symbol);
    }

    @Override
    public String toString() {
        return symbol.toString();
    }
}
