package foundation.rpg.sample.json;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

// Generated visitor pattern based state for grammar parser.
public class StateString1 extends StackState<java.lang.String, State> {

// NoStack:
// Stack:
    public StateString1(foundation.rpg.sample.json.JsonFactory factory, java.lang.String node, State prev) {
        super(factory, node, prev);
    }


// Reduce:
    @Override
    public State visitEnd(foundation.rpg.parser.End symbol) throws UnexpectedInputException {
        State stack1 = this.getPrev();
        return stack1.visitObject(getFactory().is(this.getNode())).visitEnd(symbol);
    }


// Shift:
// Accept:
}
