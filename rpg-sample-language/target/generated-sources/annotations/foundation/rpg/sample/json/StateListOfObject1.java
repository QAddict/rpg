package foundation.rpg.sample.json;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

// Generated visitor pattern based state for grammar parser.
public class StateListOfObject1 extends StackState<java.util.List<java.lang.Object>, StackState<foundation.rpg.common.symbols.LBr, ? extends State>> {

// NoStack:
// Stack:
    public StateListOfObject1(foundation.rpg.sample.json.JsonFactory factory, java.util.List<java.lang.Object> node, StackState<foundation.rpg.common.symbols.LBr, ? extends State> prev) {
        super(factory, node, prev);
    }


// Reduce:
// Shift:
    @Override
    public State visitRBr(foundation.rpg.common.symbols.RBr symbol) {
        return new StateRBr2(getFactory(), symbol, this);
    }

    @Override
    public State visitComma(foundation.rpg.common.symbols.Comma symbol) {
        return new StateComma1(getFactory(), symbol, this);
    }


// Accept:
}
