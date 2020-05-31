package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.Element;
import foundation.rpg.parser.UnexpectedInputException;

// Generated token element wrapper for grammar parser.
public class ElementPlus implements Element<State> {
    private final foundation.rpg.common.symbols.Plus symbol;

    public ElementPlus(foundation.rpg.common.symbols.Plus symbol) {
        this.symbol = symbol;
    }

    @Override
    public State accept(State state) throws UnexpectedInputException {
        return state.visitPlus(symbol);
    }

    @Override
    public String toString() {
        return symbol.toString();
    }
}
