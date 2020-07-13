package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

import java.util.Arrays;
import java.util.List;

// Generated visitor pattern based state for grammar parser.
public class StateProgram1 extends StackState<foundation.rpg.sample.language.ast.Program, State> {

// NoStack:
// Stack:
    public StateProgram1(foundation.rpg.sample.language.ast.AstFactory factory, foundation.rpg.sample.language.ast.Program node, State prev) {
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
