package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.Element;
import foundation.rpg.parser.UnexpectedInputException;

// Generated token element wrapper for grammar parser.
public class ElementIdentifier implements Element<State> {
    private final foundation.rpg.sample.language.ast.Identifier symbol;

    public ElementIdentifier(foundation.rpg.sample.language.ast.Identifier symbol) {
        this.symbol = symbol;
    }

    @Override
    public State accept(State state) throws UnexpectedInputException {
        return state.visitIdentifier(symbol);
    }

    @Override
    public String toString() {
        return symbol.toString();
    }
}
