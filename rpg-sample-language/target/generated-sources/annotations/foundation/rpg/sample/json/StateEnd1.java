package foundation.rpg.sample.json;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

// Generated visitor pattern based state for grammar parser.
public class StateEnd1 extends StackState<foundation.rpg.parser.End, StackState<java.lang.Object, ? extends State>> {

// NoStack:
// Stack:
    public StateEnd1(foundation.rpg.sample.json.JsonFactory factory, foundation.rpg.parser.End node, StackState<java.lang.Object, ? extends State> prev) {
        super(factory, node, prev);
    }


// Reduce:
// Shift:
// Accept:
    @Override
    public boolean accepted() {
        return true;
    }
    @Override
    public java.lang.Object result() {
        return getPrev().getNode();
    }


}
