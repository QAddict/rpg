package foundation.rpg.sample.json;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

import java.util.Arrays;
import java.util.List;

// Generated visitor pattern based state for grammar parser.
public class StateColon2 extends StackState<foundation.rpg.common.symbols.Colon, StackState<java.lang.String, StackState<foundation.rpg.common.symbols.Comma, StackState<java.util.Map<java.lang.String,java.lang.Object>, ? extends State>>>> {

// NoStack:
// Stack:
    public StateColon2(foundation.rpg.sample.json.JsonFactory factory, foundation.rpg.common.symbols.Colon node, StackState<java.lang.String, StackState<foundation.rpg.common.symbols.Comma, StackState<java.util.Map<java.lang.String,java.lang.Object>, ? extends State>>> prev) {
        super(factory, node, prev);
    }


// Reduce:
// Shift:
    @Override
    public State visitObject(java.lang.Object symbol) {
        return new StateObject7(getFactory(), symbol, this);
    }

    @Override
    public State visitString(java.lang.String symbol) {
        return new StateString8(getFactory(), symbol, this);
    }

    @Override
    public State visitInteger(java.lang.Integer symbol) {
        return new StateInteger5(getFactory(), symbol, this);
    }

    @Override
    public State visitDouble(java.lang.Double symbol) {
        return new StateDouble5(getFactory(), symbol, this);
    }

    @Override
    public State visitLBr(foundation.rpg.common.symbols.LBr symbol) {
        return new StateLBr5(getFactory(), symbol, this);
    }

    @Override
    public State visitLCurl(foundation.rpg.common.symbols.LCurl symbol) {
        return new StateLCurl5(getFactory(), symbol, this);
    }

    @Override
    public State visitToken(@Named("string") foundation.rpg.parser.Token symbol) {
        return new StateToken8(getFactory(), symbol, this);
    }

    @Override
    public State visitToken$(@Named("identifier") foundation.rpg.parser.Token symbol) {
        return new StateToken$8(getFactory(), symbol, this);
    }

    @Override
    public State visitToken$$(@Named("integer") foundation.rpg.parser.Token symbol) {
        return new StateToken$$5(getFactory(), symbol, this);
    }

    @Override
    public State visitToken$$$(@Named("double") foundation.rpg.parser.Token symbol) {
        return new StateToken$$$5(getFactory(), symbol, this);
    }


// Accept:
    @Override
    public List<Object> stack() {
        StackState<java.lang.String, StackState<foundation.rpg.common.symbols.Comma, StackState<java.util.Map<java.lang.String,java.lang.Object>, ? extends State>>> stack1 = this.getPrev();
		StackState<foundation.rpg.common.symbols.Comma, StackState<java.util.Map<java.lang.String,java.lang.Object>, ? extends State>> stack2 = stack1.getPrev();
		StackState<java.util.Map<java.lang.String,java.lang.Object>, ? extends State> stack3 = stack2.getPrev();
		State stack4 = stack3.getPrev();
        return Arrays.asList(stack3.getNode(), stack2.getNode(), stack1.getNode(), this.getNode());
    }

}
