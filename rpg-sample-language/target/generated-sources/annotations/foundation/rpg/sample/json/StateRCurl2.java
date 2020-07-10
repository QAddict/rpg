package foundation.rpg.sample.json;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

// Generated visitor pattern based state for grammar parser.
public class StateRCurl2 extends StackState<foundation.rpg.common.symbols.RCurl, StackState<foundation.rpg.common.symbols.LCurl, ? extends State>> {

// NoStack:
// Stack:
    public StateRCurl2(foundation.rpg.sample.json.JsonFactory factory, foundation.rpg.common.symbols.RCurl node, StackState<foundation.rpg.common.symbols.LCurl, ? extends State> prev) {
        super(factory, node, prev);
    }


// Reduce:
    @Override
    public State visitRBr(foundation.rpg.common.symbols.RBr symbol) throws UnexpectedInputException {
        StackState<foundation.rpg.common.symbols.LCurl, ? extends State> stack1 = this.getPrev();
		State stack2 = stack1.getPrev();
        return stack2.visitObject(getFactory().is(stack1.getNode(), this.getNode())).visitRBr(symbol);
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
