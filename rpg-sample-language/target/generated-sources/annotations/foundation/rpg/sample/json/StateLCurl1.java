package foundation.rpg.sample.json;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

import java.util.Arrays;
import java.util.List;

// Generated visitor pattern based state for grammar parser.
public class StateLCurl1 extends StackState<foundation.rpg.common.symbols.LCurl, State> {

// NoStack:
// Stack:
    public StateLCurl1(foundation.rpg.sample.json.JsonFactory factory, foundation.rpg.common.symbols.LCurl node, State prev) {
        super(factory, node, prev);
    }


// Reduce:
// Shift:
    @Override
    public State visitMapOfObject(java.util.Map<java.lang.String,java.lang.Object> symbol) {
        return new StateMapOfObject1(getFactory(), symbol, this);
    }

    @Override
    public State visitRCurl(foundation.rpg.common.symbols.RCurl symbol) {
        return new StateRCurl1(getFactory(), symbol, this);
    }

    @Override
    public State visitString(java.lang.String symbol) {
        return new StateString3(getFactory(), symbol, this);
    }

    @Override
    public State visitToken(@Named("string") foundation.rpg.parser.Token symbol) {
        return new StateToken3(getFactory(), symbol, this);
    }

    @Override
    public State visitToken$(@Named("identifier") foundation.rpg.parser.Token symbol) {
        return new StateToken$3(getFactory(), symbol, this);
    }


// Accept:
    @Override
    public List<Object> stack() {
        State stack1 = this.getPrev();
        return Arrays.asList(this.getNode());
    }

}
