package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.Element;
import foundation.rpg.parser.UnexpectedInputException;

// Generated token element wrapper for grammar parser.
public class ElementSemicolon implements Element<State> {
    private final foundation.rpg.common.symbols.Semicolon symbol;

    public ElementSemicolon(foundation.rpg.common.symbols.Semicolon symbol) {
        this.symbol = symbol;
    }

    @Override
    public State accept(State state) throws UnexpectedInputException {
        return state.visitSemicolon(symbol);
    }

    @Override
    public String toString() {
        return symbol.toString();
    }
}
