package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.Element;
import foundation.rpg.parser.UnexpectedInputException;

// Generated token element wrapper for grammar parser.
public class ElementRPar implements Element<State> {
    private final foundation.rpg.common.symbols.RPar symbol;

    public ElementRPar(foundation.rpg.common.symbols.RPar symbol) {
        this.symbol = symbol;
    }

    @Override
    public State accept(State state) throws UnexpectedInputException {
        return state.visitRPar(symbol);
    }

    @Override
    public String toString() {
        return symbol.toString();
    }
}
