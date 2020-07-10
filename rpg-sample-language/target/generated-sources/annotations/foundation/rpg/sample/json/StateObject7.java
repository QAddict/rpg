package foundation.rpg.sample.json;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

// Generated visitor pattern based state for grammar parser.
public class StateObject7 extends StackState<java.lang.Object, StackState<foundation.rpg.common.symbols.Colon, StackState<java.lang.String, StackState<foundation.rpg.common.symbols.Comma, StackState<java.util.Map<java.lang.String,java.lang.Object>, ? extends State>>>>> {

// NoStack:
// Stack:
    public StateObject7(foundation.rpg.sample.json.JsonFactory factory, java.lang.Object node, StackState<foundation.rpg.common.symbols.Colon, StackState<java.lang.String, StackState<foundation.rpg.common.symbols.Comma, StackState<java.util.Map<java.lang.String,java.lang.Object>, ? extends State>>>> prev) {
        super(factory, node, prev);
    }


// Reduce:
    @Override
    public State visitRCurl(foundation.rpg.common.symbols.RCurl symbol) throws UnexpectedInputException {
        StackState<foundation.rpg.common.symbols.Colon, StackState<java.lang.String, StackState<foundation.rpg.common.symbols.Comma, StackState<java.util.Map<java.lang.String,java.lang.Object>, ? extends State>>>> stack1 = this.getPrev();
		StackState<java.lang.String, StackState<foundation.rpg.common.symbols.Comma, StackState<java.util.Map<java.lang.String,java.lang.Object>, ? extends State>>> stack2 = stack1.getPrev();
		StackState<foundation.rpg.common.symbols.Comma, StackState<java.util.Map<java.lang.String,java.lang.Object>, ? extends State>> stack3 = stack2.getPrev();
		StackState<java.util.Map<java.lang.String,java.lang.Object>, ? extends State> stack4 = stack3.getPrev();
		State stack5 = stack4.getPrev();
        return stack5.visitMapOfObject(getFactory().is(stack4.getNode(), stack3.getNode(), stack2.getNode(), stack1.getNode(), this.getNode())).visitRCurl(symbol);
    }

    @Override
    public State visitComma(foundation.rpg.common.symbols.Comma symbol) throws UnexpectedInputException {
        StackState<foundation.rpg.common.symbols.Colon, StackState<java.lang.String, StackState<foundation.rpg.common.symbols.Comma, StackState<java.util.Map<java.lang.String,java.lang.Object>, ? extends State>>>> stack1 = this.getPrev();
		StackState<java.lang.String, StackState<foundation.rpg.common.symbols.Comma, StackState<java.util.Map<java.lang.String,java.lang.Object>, ? extends State>>> stack2 = stack1.getPrev();
		StackState<foundation.rpg.common.symbols.Comma, StackState<java.util.Map<java.lang.String,java.lang.Object>, ? extends State>> stack3 = stack2.getPrev();
		StackState<java.util.Map<java.lang.String,java.lang.Object>, ? extends State> stack4 = stack3.getPrev();
		State stack5 = stack4.getPrev();
        return stack5.visitMapOfObject(getFactory().is(stack4.getNode(), stack3.getNode(), stack2.getNode(), stack1.getNode(), this.getNode())).visitComma(symbol);
    }


// Shift:
// Accept:
}
