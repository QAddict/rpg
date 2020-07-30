package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.Element;
import foundation.rpg.parser.UnexpectedInputException;

// Generated token element wrapper for grammar parser.
public class ElementThen implements Element<State> {
    private final foundation.rpg.common.symbols.Then symbol;

    public ElementThen(foundation.rpg.common.symbols.Then symbol) {
        this.symbol = symbol;
    }

    @Override
    public State accept(State state) throws UnexpectedInputException {
        return state.visitThen(symbol);
    }

    @Override
    public String toString() {
        return symbol.toString();
    }
}
