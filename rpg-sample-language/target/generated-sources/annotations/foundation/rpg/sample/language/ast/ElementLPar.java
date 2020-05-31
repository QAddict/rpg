package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.Element;
import foundation.rpg.parser.UnexpectedInputException;

// Generated token element wrapper for grammar parser.
public class ElementLPar implements Element<State> {
    private final foundation.rpg.common.symbols.LPar symbol;

    public ElementLPar(foundation.rpg.common.symbols.LPar symbol) {
        this.symbol = symbol;
    }

    @Override
    public State accept(State state) throws UnexpectedInputException {
        return state.visitLPar(symbol);
    }

    @Override
    public String toString() {
        return symbol.toString();
    }
}
