package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.Element;
import foundation.rpg.parser.UnexpectedInputException;

// Generated token element wrapper for grammar parser.
public class ElementEnd implements Element<State> {
    private final foundation.rpg.parser.End symbol;

    public ElementEnd(foundation.rpg.parser.End symbol) {
        this.symbol = symbol;
    }

    @Override
    public State accept(State state) throws UnexpectedInputException {
        return state.visitEnd(symbol);
    }

    @Override
    public String toString() {
        return symbol.toString();
    }
}
