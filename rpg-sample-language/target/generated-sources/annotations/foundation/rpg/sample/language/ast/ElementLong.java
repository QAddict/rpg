package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.Element;
import foundation.rpg.parser.UnexpectedInputException;

// Generated token element wrapper for grammar parser.
public class ElementLong implements Element<State> {
    private final java.lang.Long symbol;

    public ElementLong(java.lang.Long symbol) {
        this.symbol = symbol;
    }

    @Override
    public State accept(State state) throws UnexpectedInputException {
        return state.visitLong(symbol);
    }

    @Override
    public String toString() {
        return symbol.toString();
    }
}
