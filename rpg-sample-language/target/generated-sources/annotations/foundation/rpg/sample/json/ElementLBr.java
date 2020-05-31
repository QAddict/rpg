package foundation.rpg.sample.json;

import foundation.rpg.parser.Element;
import foundation.rpg.parser.UnexpectedInputException;

// Generated token element wrapper for grammar parser.
public class ElementLBr implements Element<State> {
    private final foundation.rpg.common.symbols.LBr symbol;

    public ElementLBr(foundation.rpg.common.symbols.LBr symbol) {
        this.symbol = symbol;
    }

    @Override
    public State accept(State state) throws UnexpectedInputException {
        return state.visitLBr(symbol);
    }

    @Override
    public String toString() {
        return symbol.toString();
    }
}
