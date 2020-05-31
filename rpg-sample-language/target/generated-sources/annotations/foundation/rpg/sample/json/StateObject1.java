package foundation.rpg.sample.json;

import foundation.rpg.parser.UnexpectedInputException;

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
}
