package foundation.rpg.sample.json;

import foundation.rpg.parser.UnexpectedInputException;

// Generated visitor pattern based state for grammar parser.
public class StateObject5 extends StackState<java.lang.Object, StackState<foundation.rpg.common.symbols.Colon, StackState<java.lang.String, ? extends State>>> {

// NoStack:
// Stack:
    public StateObject5(foundation.rpg.sample.json.JsonFactory factory, java.lang.Object node, StackState<foundation.rpg.common.symbols.Colon, StackState<java.lang.String, ? extends State>> prev) {
        super(factory, node, prev);
    }


// Reduce:
    @Override
    public State visitRCurl(foundation.rpg.common.symbols.RCurl symbol) throws UnexpectedInputException {
        StackState<foundation.rpg.common.symbols.Colon, StackState<java.lang.String, ? extends State>> stack1 = this.getPrev();
		StackState<java.lang.String, ? extends State> stack2 = stack1.getPrev();
		State stack3 = stack2.getPrev();
        return stack3.visitMapOfObject(getFactory().is(stack2.getNode(), stack1.getNode(), this.getNode())).visitRCurl(symbol);
    }

    @Override
    public State visitComma(foundation.rpg.common.symbols.Comma symbol) throws UnexpectedInputException {
        StackState<foundation.rpg.common.symbols.Colon, StackState<java.lang.String, ? extends State>> stack1 = this.getPrev();
		StackState<java.lang.String, ? extends State> stack2 = stack1.getPrev();
		State stack3 = stack2.getPrev();
        return stack3.visitMapOfObject(getFactory().is(stack2.getNode(), stack1.getNode(), this.getNode())).visitComma(symbol);
    }


// Shift:
// Accept:
}
