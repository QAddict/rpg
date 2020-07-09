package foundation.rpg.sample.json;

import foundation.rpg.parser.UnexpectedInputException;

// Generated visitor pattern based state for grammar parser.
public class State1 extends State {

// NoStack:
    public State1(foundation.rpg.sample.json.JsonFactory factory) {
        super(factory);
    }


// Stack:
// Reduce:
// Shift:
    @Override
    public State visitObject(java.lang.Object symbol) {
        return new StateObject1(getFactory(), symbol, this);
    }

    @Override
    public State visitString(java.lang.String symbol) {
        return new StateString1(getFactory(), symbol, this);
    }

    @Override
    public State visitInteger(java.lang.Integer symbol) {
        return new StateInteger1(getFactory(), symbol, this);
    }

    @Override
    public State visitDouble(java.lang.Double symbol) {
        return new StateDouble1(getFactory(), symbol, this);
    }

    @Override
    public State visitLBr(foundation.rpg.common.symbols.LBr symbol) {
        return new StateLBr1(getFactory(), symbol, this);
    }

    @Override
    public State visitLCurl(foundation.rpg.common.symbols.LCurl symbol) {
        return new StateLCurl1(getFactory(), symbol, this);
    }

    @Override
    public State visitstring(foundation.rpg.parser.Token symbol) {
        return new Statestring1(getFactory(), symbol, this);
    }

    @Override
    public State visitidentifier(foundation.rpg.parser.Token symbol) {
        return new Stateidentifier1(getFactory(), symbol, this);
    }

    @Override
    public State visitinteger(foundation.rpg.parser.Token symbol) {
        return new Stateinteger1(getFactory(), symbol, this);
    }

    @Override
    public State visitdouble(foundation.rpg.parser.Token symbol) {
        return new Statedouble1(getFactory(), symbol, this);
    }


// Accept:
}
