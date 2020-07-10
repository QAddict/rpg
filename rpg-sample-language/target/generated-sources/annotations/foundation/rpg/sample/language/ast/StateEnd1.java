package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

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


}
