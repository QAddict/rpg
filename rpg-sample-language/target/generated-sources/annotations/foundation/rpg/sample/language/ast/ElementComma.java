package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.Element;
import foundation.rpg.parser.UnexpectedInputException;

// Generated token element wrapper for grammar parser.
public class ElementComma implements Element<State> {
    private final foundation.rpg.common.symbols.Comma symbol;

    public ElementComma(foundation.rpg.common.symbols.Comma symbol) {
        this.symbol = symbol;
    }

    @Override
    public State accept(State state) throws UnexpectedInputException {
        return state.visitComma(symbol);
    }

    @Override
    public String toString() {
        return symbol.toString();
    }
}
