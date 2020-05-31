package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.Element;
import foundation.rpg.parser.UnexpectedInputException;

// Generated token element wrapper for grammar parser.
public class ElementGt implements Element<State> {
    private final foundation.rpg.common.symbols.Gt symbol;

    public ElementGt(foundation.rpg.common.symbols.Gt symbol) {
        this.symbol = symbol;
    }

    @Override
    public State accept(State state) throws UnexpectedInputException {
        return state.visitGt(symbol);
    }

    @Override
    public String toString() {
        return symbol.toString();
    }
}
