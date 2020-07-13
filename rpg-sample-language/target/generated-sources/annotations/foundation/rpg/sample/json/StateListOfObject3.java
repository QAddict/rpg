package foundation.rpg.sample.json;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

import java.util.Arrays;
import java.util.List;

// Generated visitor pattern based state for grammar parser.
public class StateListOfObject3 extends StackState<java.util.List<java.lang.Object>, StackState<foundation.rpg.common.symbols.LBr, ? extends State>> {

// NoStack:
// Stack:
    public StateListOfObject3(foundation.rpg.sample.json.JsonFactory factory, java.util.List<java.lang.Object> node, StackState<foundation.rpg.common.symbols.LBr, ? extends State> prev) {
        super(factory, node, prev);
    }


// Reduce:
// Shift:
    @Override
    public State visitRBr(foundation.rpg.common.symbols.RBr symbol) {
        return new StateRBr6(getFactory(), symbol, this);
    }

    @Override
    public State visitComma(foundation.rpg.common.symbols.Comma symbol) {
        return new StateComma1(getFactory(), symbol, this);
    }


// Accept:
    @Override
    public List<Object> stack() {
        StackState<foundation.rpg.common.symbols.LBr, ? extends State> stack1 = this.getPrev();
		State stack2 = stack1.getPrev();
        return Arrays.asList(stack1.getNode(), this.getNode());
    }

}
