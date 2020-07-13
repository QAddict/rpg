package foundation.rpg.sample.json;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

import java.util.Arrays;
import java.util.List;

// Generated visitor pattern based state for grammar parser.
public class StateRBr3 extends StackState<foundation.rpg.common.symbols.RBr, StackState<foundation.rpg.common.symbols.LBr, ? extends State>> {

// NoStack:
// Stack:
    public StateRBr3(foundation.rpg.sample.json.JsonFactory factory, foundation.rpg.common.symbols.RBr node, StackState<foundation.rpg.common.symbols.LBr, ? extends State> prev) {
        super(factory, node, prev);
    }


// Reduce:
    @Override
    public State visitRBr(foundation.rpg.common.symbols.RBr symbol) throws UnexpectedInputException {
        StackState<foundation.rpg.common.symbols.LBr, ? extends State> stack1 = this.getPrev();
		State stack2 = stack1.getPrev();
        return stack2.visitObject(getFactory().is(stack1.getNode(), this.getNode())).visitRBr(symbol);
    }

    @Override
    public State visitComma(foundation.rpg.common.symbols.Comma symbol) throws UnexpectedInputException {
        StackState<foundation.rpg.common.symbols.LBr, ? extends State> stack1 = this.getPrev();
		State stack2 = stack1.getPrev();
        return stack2.visitObject(getFactory().is(stack1.getNode(), this.getNode())).visitComma(symbol);
    }


// Shift:
// Accept:
    @Override
    public List<Object> stack() {
        StackState<foundation.rpg.common.symbols.LBr, ? extends State> stack1 = this.getPrev();
		State stack2 = stack1.getPrev();
        return Arrays.asList(stack1.getNode(), this.getNode());
    }

}
