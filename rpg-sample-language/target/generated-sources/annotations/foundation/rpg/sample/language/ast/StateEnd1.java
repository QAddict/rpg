package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

import java.util.Arrays;
import java.util.List;

// Generated visitor pattern based state for grammar parser.
public class StateEnd1 extends StackState<foundation.rpg.parser.End, StackState<foundation.rpg.sample.language.ast.Program, ? extends State>> {

// NoStack:
// Stack:
    public StateEnd1(foundation.rpg.sample.language.ast.AstFactory factory, foundation.rpg.parser.End node, StackState<foundation.rpg.sample.language.ast.Program, ? extends State> prev) {
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
    public foundation.rpg.sample.language.ast.Program result() {
        return getPrev().getNode();
    }


    @Override
    public List<Object> stack() {
        StackState<foundation.rpg.sample.language.ast.Program, ? extends State> stack1 = this.getPrev();
		State stack2 = stack1.getPrev();
        return Arrays.asList(stack1.getNode(), this.getNode());
    }

}
