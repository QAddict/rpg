package foundation.rpg.sample.json;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

// Generated visitor pattern based state for grammar parser.
public class StateRCurl5 extends StackState<foundation.rpg.common.symbols.RCurl, StackState<foundation.rpg.common.symbols.LCurl, ? extends State>> {

// NoStack:
// Stack:
    public StateRCurl5(foundation.rpg.sample.json.JsonFactory factory, foundation.rpg.common.symbols.RCurl node, StackState<foundation.rpg.common.symbols.LCurl, ? extends State> prev) {
        super(factory, node, prev);
    }


// Reduce:
    @Override
    public State visitRCurl(foundation.rpg.common.symbols.RCurl symbol) throws UnexpectedInputException {
        StackState<foundation.rpg.common.symbols.LCurl, ? extends State> stack1 = this.getPrev();
		State stack2 = stack1.getPrev();
        return stack2.visitObject(getFactory().is(stack1.getNode(), this.getNode())).visitRCurl(symbol);
    }

    @Override
    public State visitComma(foundation.rpg.common.symbols.Comma symbol) throws UnexpectedInputException {
        StackState<foundation.rpg.common.symbols.LCurl, ? extends State> stack1 = this.getPrev();
		State stack2 = stack1.getPrev();
        return stack2.visitObject(getFactory().is(stack1.getNode(), this.getNode())).visitComma(symbol);
    }


// Shift:
// Accept:
}
