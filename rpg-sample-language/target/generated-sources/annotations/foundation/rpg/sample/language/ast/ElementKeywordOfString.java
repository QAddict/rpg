package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.Element;
import foundation.rpg.parser.UnexpectedInputException;

// Generated token element wrapper for grammar parser.
public class ElementKeywordOfString implements Element<State> {
    private final foundation.rpg.sample.language.ast.Keyword<java.lang.String> symbol;

    public ElementKeywordOfString(foundation.rpg.sample.language.ast.Keyword<java.lang.String> symbol) {
        this.symbol = symbol;
    }

    @Override
    public State accept(State state) throws UnexpectedInputException {
        return state.visitKeywordOfString(symbol);
    }

    @Override
    public String toString() {
        return symbol.toString();
    }
}
