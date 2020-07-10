package foundation.rpg.sample.json;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

// Generated visitor pattern based state for grammar parser.
public class StateString7 extends StackState<java.lang.String, StackState<foundation.rpg.common.symbols.Comma, StackState<java.util.Map<java.lang.String,java.lang.Object>, ? extends State>>> {

// NoStack:
// Stack:
    public StateString7(foundation.rpg.sample.json.JsonFactory factory, java.lang.String node, StackState<foundation.rpg.common.symbols.Comma, StackState<java.util.Map<java.lang.String,java.lang.Object>, ? extends State>> prev) {
        super(factory, node, prev);
    }


// Reduce:
// Shift:
    @Override
    public State visitColon(foundation.rpg.common.symbols.Colon symbol) {
        return new StateColon2(getFactory(), symbol, this);
    }


// Accept:
}
