package foundation.rpg.sample.json;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

import java.util.Arrays;
import java.util.List;

// Generated visitor pattern based state for grammar parser.
public class StateToken$$$5 extends StackState<foundation.rpg.parser.Token, State> {

// NoStack:
// Stack:
    public StateToken$$$5(foundation.rpg.sample.json.JsonFactory factory, foundation.rpg.parser.Token node, State prev) {
        super(factory, node, prev);
    }


// Reduce:
    @Override
    public State visitRCurl(foundation.rpg.common.symbols.RCurl symbol) throws UnexpectedInputException {
        State stack1 = this.getPrev();
        return stack1.visitDouble(getFactory().matchDouble(this.getNode())).visitRCurl(symbol);
    }

    @Override
    public State visitComma(foundation.rpg.common.symbols.Comma symbol) throws UnexpectedInputException {
        State stack1 = this.getPrev();
        return stack1.visitDouble(getFactory().matchDouble(this.getNode())).visitComma(symbol);
    }


// Shift:
// Accept:
    @Override
    public List<Object> stack() {
        State stack1 = this.getPrev();
        return Arrays.asList(this.getNode());
    }

}
