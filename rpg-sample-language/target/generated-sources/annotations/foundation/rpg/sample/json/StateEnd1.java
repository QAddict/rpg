package foundation.rpg.sample.json;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

import java.util.Arrays;
import java.util.List;

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


    @Override
    public List<Object> stack() {
        StackState<java.lang.Object, ? extends State> stack1 = this.getPrev();
		State stack2 = stack1.getPrev();
        return Arrays.asList(stack1.getNode(), this.getNode());
    }

}
