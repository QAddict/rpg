package foundation.rpg.sample.json;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.StateBase;

// Generated visitor pattern based state for grammar parser.
public class State extends StateBase<java.lang.Object> {
    private final foundation.rpg.sample.json.JsonFactory factory;

    public State(foundation.rpg.sample.json.JsonFactory factory) {
        this.factory = factory;
    }

    public foundation.rpg.sample.json.JsonFactory getFactory() {
        return factory;
    }

// Ignored:
    public State visitWhiteSpace(foundation.rpg.common.symbols.WhiteSpace symbol) {
        return this;
    }


// Symbols:
    public State visitEnd(foundation.rpg.parser.End symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitstring(foundation.rpg.parser.Token symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitidentifier(foundation.rpg.parser.Token symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitinteger(foundation.rpg.parser.Token symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitdouble(foundation.rpg.parser.Token symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitLBr(foundation.rpg.common.symbols.LBr symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitRBr(foundation.rpg.common.symbols.RBr symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitLCurl(foundation.rpg.common.symbols.LCurl symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitRCurl(foundation.rpg.common.symbols.RCurl symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitComma(foundation.rpg.common.symbols.Comma symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitColon(foundation.rpg.common.symbols.Colon symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitString(java.lang.String symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitInteger(java.lang.Integer symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitDouble(java.lang.Double symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitObject(java.lang.Object symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitListOfObject(java.util.List<java.lang.Object> symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitMapOfObject(java.util.Map<java.lang.String,java.lang.Object> symbol) throws UnexpectedInputException {
        return error(symbol);
    }


}
