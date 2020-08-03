package foundation.rpg.sample.json;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

import java.util.Arrays;
import java.util.List;

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
    public State visitToken(@Named("string") foundation.rpg.parser.Token symbol) {
        return new StateToken1(getFactory(), symbol, this);
    }

    @Override
    public State visitToken$(@Named("integer") foundation.rpg.parser.Token symbol) {
        return new StateToken$1(getFactory(), symbol, this);
    }

    @Override
    public State visitToken$$(@Named("double") foundation.rpg.parser.Token symbol) {
        return new StateToken$$1(getFactory(), symbol, this);
    }

    @Override
    public State visitLBr(foundation.rpg.common.symbols.LBr symbol) {
        return new StateLBr1(getFactory(), symbol, this);
    }

    @Override
    public State visitLCurl(foundation.rpg.common.symbols.LCurl symbol) {
        return new StateLCurl1(getFactory(), symbol, this);
    }


// Accept:
    @Override
    public List<Object> stack() {
        
        return Arrays.asList();
    }

}
