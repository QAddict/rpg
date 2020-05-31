package foundation.rpg.sample.json;

import foundation.rpg.parser.UnexpectedInputException;

// Generated visitor pattern based state for grammar parser.
public class StateRCurl6 extends StackState<foundation.rpg.common.symbols.RCurl, StackState<java.util.Map<java.lang.String,java.lang.Object>, StackState<foundation.rpg.common.symbols.LCurl, ? extends State>>> {

// NoStack:
// Stack:
    public StateRCurl6(foundation.rpg.sample.json.JsonFactory factory, foundation.rpg.common.symbols.RCurl node, StackState<java.util.Map<java.lang.String,java.lang.Object>, StackState<foundation.rpg.common.symbols.LCurl, ? extends State>> prev) {
        super(factory, node, prev);
    }


// Reduce:
    @Override
    public State visitRCurl(foundation.rpg.common.symbols.RCurl symbol) throws UnexpectedInputException {
        StackState<java.util.Map<java.lang.String,java.lang.Object>, StackState<foundation.rpg.common.symbols.LCurl, ? extends State>> stack1 = this.getPrev();
		StackState<foundation.rpg.common.symbols.LCurl, ? extends State> stack2 = stack1.getPrev();
		State stack3 = stack2.getPrev();
        return stack3.visitObject(getFactory().is(stack2.getNode(), stack1.getNode(), this.getNode())).visitRCurl(symbol);
    }

    @Override
    public State visitComma(foundation.rpg.common.symbols.Comma symbol) throws UnexpectedInputException {
        StackState<java.util.Map<java.lang.String,java.lang.Object>, StackState<foundation.rpg.common.symbols.LCurl, ? extends State>> stack1 = this.getPrev();
		StackState<foundation.rpg.common.symbols.LCurl, ? extends State> stack2 = stack1.getPrev();
		State stack3 = stack2.getPrev();
        return stack3.visitObject(getFactory().is(stack2.getNode(), stack1.getNode(), this.getNode())).visitComma(symbol);
    }


// Shift:
// Accept:
}
