package foundation.rpg.sample.json;

import foundation.rpg.parser.Element;
import foundation.rpg.parser.UnexpectedInputException;

// Generated token element wrapper for grammar parser.
public class ElementRBr implements Element<State> {
    private final foundation.rpg.common.symbols.RBr symbol;

    public ElementRBr(foundation.rpg.common.symbols.RBr symbol) {
        this.symbol = symbol;
    }

    @Override
    public State accept(State state) throws UnexpectedInputException {
        return state.visitRBr(symbol);
    }

    @Override
    public String toString() {
        return symbol.toString();
    }
}
