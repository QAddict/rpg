package foundation.rpg.sample.json;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

// Generated visitor pattern based state for grammar parser.
public class StateString3 extends StackState<java.lang.String, State> {

// NoStack:
// Stack:
    public StateString3(foundation.rpg.sample.json.JsonFactory factory, java.lang.String node, State prev) {
        super(factory, node, prev);
    }


// Reduce:
// Shift:
    @Override
    public State visitColon(foundation.rpg.common.symbols.Colon symbol) {
        return new StateColon1(getFactory(), symbol, this);
    }


// Accept:
}
