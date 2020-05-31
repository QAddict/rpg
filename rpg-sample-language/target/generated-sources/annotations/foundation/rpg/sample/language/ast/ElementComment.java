package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.Element;
import foundation.rpg.parser.UnexpectedInputException;

// Generated token element wrapper for grammar parser.
public class ElementComment implements Element<State> {
    private final foundation.rpg.common.symbols.Comment symbol;

    public ElementComment(foundation.rpg.common.symbols.Comment symbol) {
        this.symbol = symbol;
    }

    @Override
    public State accept(State state) throws UnexpectedInputException {
        return state.visitComment(symbol);
    }

    @Override
    public String toString() {
        return symbol.toString();
    }
}
