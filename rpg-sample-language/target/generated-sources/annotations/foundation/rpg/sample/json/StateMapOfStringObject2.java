package foundation.rpg.sample.json;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

import java.util.Arrays;
import java.util.List;

// Generated visitor pattern based state for grammar parser.
public class StateMapOfStringObject2 extends StackState<java.util.Map<java.lang.String,java.lang.Object>, StackState<foundation.rpg.common.symbols.LCurl, ? extends State>> {

// NoStack:
// Stack:
    public StateMapOfStringObject2(foundation.rpg.sample.json.JsonFactory factory, java.util.Map<java.lang.String,java.lang.Object> node, StackState<foundation.rpg.common.symbols.LCurl, ? extends State> prev) {
        super(factory, node, prev);
    }


// Reduce:
// Shift:
    @Override
    public State visitRCurl(foundation.rpg.common.symbols.RCurl symbol) {
        return new StateRCurl4(getFactory(), symbol, this);
    }

    @Override
    public State visitComma(foundation.rpg.common.symbols.Comma symbol) {
        return new StateComma2(getFactory(), symbol, this);
    }


// Accept:
    @Override
    public List<Object> stack() {
        StackState<foundation.rpg.common.symbols.LCurl, ? extends State> stack1 = this.getPrev();
		State stack2 = stack1.getPrev();
        return Arrays.asList(stack1.getNode(), this.getNode());
    }

}
