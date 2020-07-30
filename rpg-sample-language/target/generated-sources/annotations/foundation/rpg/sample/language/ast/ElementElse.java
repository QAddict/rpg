package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.Element;
import foundation.rpg.parser.UnexpectedInputException;

// Generated token element wrapper for grammar parser.
public class ElementElse implements Element<State> {
    private final foundation.rpg.common.symbols.Else symbol;

    public ElementElse(foundation.rpg.common.symbols.Else symbol) {
        this.symbol = symbol;
    }

    @Override
    public State accept(State state) throws UnexpectedInputException {
        return state.visitElse(symbol);
    }

    @Override
    public String toString() {
        return symbol.toString();
    }
}
