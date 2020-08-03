package foundation.rpg.sample.json;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

import java.util.Arrays;
import java.util.List;

// Generated visitor pattern based state for grammar parser.
public class StateString1 extends StackState<java.lang.String, State> {

// NoStack:
// Stack:
    public StateString1(foundation.rpg.sample.json.JsonFactory factory, java.lang.String node, State prev) {
        super(factory, node, prev);
    }


// Reduce:
// Shift:
    @Override
    public State visitColon(foundation.rpg.common.symbols.Colon symbol) {
        return new StateColon1(getFactory(), symbol, this);
    }


// Accept:
    @Override
    public List<Object> stack() {
        State stack1 = this.getPrev();
        return Arrays.asList(this.getNode());
    }

}
