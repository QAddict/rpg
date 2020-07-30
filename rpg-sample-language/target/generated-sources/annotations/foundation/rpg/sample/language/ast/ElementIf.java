package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.Element;
import foundation.rpg.parser.UnexpectedInputException;

// Generated token element wrapper for grammar parser.
public class ElementIf implements Element<State> {
    private final foundation.rpg.common.symbols.If symbol;

    public ElementIf(foundation.rpg.common.symbols.If symbol) {
        this.symbol = symbol;
    }

    @Override
    public State accept(State state) throws UnexpectedInputException {
        return state.visitIf(symbol);
    }

    @Override
    public String toString() {
        return symbol.toString();
    }
}
