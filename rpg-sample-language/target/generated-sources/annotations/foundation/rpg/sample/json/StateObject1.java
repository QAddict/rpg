package foundation.rpg.sample.json;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

import java.util.Arrays;
import java.util.List;

// Generated visitor pattern based state for grammar parser.
public class StateObject1 extends StackState<java.lang.Object, State> {

// NoStack:
// Stack:
    public StateObject1(foundation.rpg.sample.json.JsonFactory factory, java.lang.Object node, State prev) {
        super(factory, node, prev);
    }


// Reduce:
// Shift:
    @Override
    public State visitEnd(foundation.rpg.parser.End symbol) {
        return new StateEnd1(getFactory(), symbol, this);
    }


// Accept:
    @Override
    public List<Object> stack() {
        State stack1 = this.getPrev();
        return Arrays.asList(this.getNode());
    }

}
