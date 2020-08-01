package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.Element;
import foundation.rpg.parser.UnexpectedInputException;

// Generated token element wrapper for grammar parser.
public class ElementKeywordOfInteger implements Element<State> {
    private final foundation.rpg.sample.language.ast.Keyword<java.lang.Integer> symbol;

    public ElementKeywordOfInteger(foundation.rpg.sample.language.ast.Keyword<java.lang.Integer> symbol) {
        this.symbol = symbol;
    }

    @Override
    public State accept(State state) throws UnexpectedInputException {
        return state.visitKeywordOfInteger(symbol);
    }

    @Override
    public String toString() {
        return symbol.toString();
    }
}
