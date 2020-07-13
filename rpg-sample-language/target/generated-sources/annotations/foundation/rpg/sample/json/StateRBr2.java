package foundation.rpg.sample.json;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

import java.util.Arrays;
import java.util.List;

// Generated visitor pattern based state for grammar parser.
public class StateRBr2 extends StackState<foundation.rpg.common.symbols.RBr, StackState<java.util.List<java.lang.Object>, StackState<foundation.rpg.common.symbols.LBr, ? extends State>>> {

// NoStack:
// Stack:
    public StateRBr2(foundation.rpg.sample.json.JsonFactory factory, foundation.rpg.common.symbols.RBr node, StackState<java.util.List<java.lang.Object>, StackState<foundation.rpg.common.symbols.LBr, ? extends State>> prev) {
        super(factory, node, prev);
    }


// Reduce:
    @Override
    public State visitEnd(foundation.rpg.parser.End symbol) throws UnexpectedInputException {
        StackState<java.util.List<java.lang.Object>, StackState<foundation.rpg.common.symbols.LBr, ? extends State>> stack1 = this.getPrev();
		StackState<foundation.rpg.common.symbols.LBr, ? extends State> stack2 = stack1.getPrev();
		State stack3 = stack2.getPrev();
        return stack3.visitObject(getFactory().is(stack2.getNode(), stack1.getNode(), this.getNode())).visitEnd(symbol);
    }


// Shift:
// Accept:
    @Override
    public List<Object> stack() {
        StackState<java.util.List<java.lang.Object>, StackState<foundation.rpg.common.symbols.LBr, ? extends State>> stack1 = this.getPrev();
		StackState<foundation.rpg.common.symbols.LBr, ? extends State> stack2 = stack1.getPrev();
		State stack3 = stack2.getPrev();
        return Arrays.asList(stack2.getNode(), stack1.getNode(), this.getNode());
    }

}
