package foundation.rpg.sample.json;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

import java.util.Arrays;
import java.util.List;

// Generated visitor pattern based state for grammar parser.
public class StateComma1 extends StackState<foundation.rpg.common.symbols.Comma, StackState<java.util.List<java.lang.Object>, ? extends State>> {

// NoStack:
// Stack:
    public StateComma1(foundation.rpg.sample.json.JsonFactory factory, foundation.rpg.common.symbols.Comma node, StackState<java.util.List<java.lang.Object>, ? extends State> prev) {
        super(factory, node, prev);
    }


// Reduce:
// Shift:
    @Override
    public State visitObject(java.lang.Object symbol) {
        return new StateObject4(getFactory(), symbol, this);
    }

    @Override
    public State visitToken(@Named("string") foundation.rpg.parser.Token symbol) {
        return new StateToken2(getFactory(), symbol, this);
    }

    @Override
    public State visitToken$(@Named("integer") foundation.rpg.parser.Token symbol) {
        return new StateToken$2(getFactory(), symbol, this);
    }

    @Override
    public State visitToken$$(@Named("double") foundation.rpg.parser.Token symbol) {
        return new StateToken$$2(getFactory(), symbol, this);
    }

    @Override
    public State visitLBr(foundation.rpg.common.symbols.LBr symbol) {
        return new StateLBr2(getFactory(), symbol, this);
    }

    @Override
    public State visitLCurl(foundation.rpg.common.symbols.LCurl symbol) {
        return new StateLCurl2(getFactory(), symbol, this);
    }


// Accept:
    @Override
    public List<Object> stack() {
        StackState<java.util.List<java.lang.Object>, ? extends State> stack1 = this.getPrev();
		State stack2 = stack1.getPrev();
        return Arrays.asList(stack1.getNode(), this.getNode());
    }

}
