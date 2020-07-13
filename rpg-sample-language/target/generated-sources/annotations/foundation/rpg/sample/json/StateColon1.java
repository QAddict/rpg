package foundation.rpg.sample.json;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

import java.util.Arrays;
import java.util.List;

// Generated visitor pattern based state for grammar parser.
public class StateColon1 extends StackState<foundation.rpg.common.symbols.Colon, StackState<java.lang.String, ? extends State>> {

// NoStack:
// Stack:
    public StateColon1(foundation.rpg.sample.json.JsonFactory factory, foundation.rpg.common.symbols.Colon node, StackState<java.lang.String, ? extends State> prev) {
        super(factory, node, prev);
    }


// Reduce:
// Shift:
    @Override
    public State visitObject(java.lang.Object symbol) {
        return new StateObject5(getFactory(), symbol, this);
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
        StackState<java.lang.String, ? extends State> stack1 = this.getPrev();
		State stack2 = stack1.getPrev();
        return Arrays.asList(stack1.getNode(), this.getNode());
    }

}
