package foundation.rpg.sample.json;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

import java.util.Arrays;
import java.util.List;

// Generated visitor pattern based state for grammar parser.
public class StateLCurl5 extends StackState<foundation.rpg.common.symbols.LCurl, State> {

// NoStack:
// Stack:
    public StateLCurl5(foundation.rpg.sample.json.JsonFactory factory, foundation.rpg.common.symbols.LCurl node, State prev) {
        super(factory, node, prev);
    }


// Reduce:
// Shift:
    @Override
    public State visitMapOfStringObject(java.util.Map<java.lang.String,java.lang.Object> symbol) {
        return new StateMapOfStringObject3(getFactory(), symbol, this);
    }

    @Override
    public State visitRCurl(foundation.rpg.common.symbols.RCurl symbol) {
        return new StateRCurl5(getFactory(), symbol, this);
    }

    @Override
    public State visitString(java.lang.String symbol) {
        return new StateString1(getFactory(), symbol, this);
    }

    @Override
    public State visitToken$$$(@Named("id") foundation.rpg.parser.Token symbol) {
        return new StateToken$$$1(getFactory(), symbol, this);
    }

    @Override
    public State visitToken(@Named("string") foundation.rpg.parser.Token symbol) {
        return new StateToken3(getFactory(), symbol, this);
    }


// Accept:
    @Override
    public List<Object> stack() {
        State stack1 = this.getPrev();
        return Arrays.asList(this.getNode());
    }

}
