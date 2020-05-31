package foundation.rpg.sample.json;

import foundation.rpg.parser.UnexpectedInputException;

// Generated visitor pattern based state for grammar parser.
public class StateLBr2 extends StackState<foundation.rpg.common.symbols.LBr, State> {

// NoStack:
// Stack:
    public StateLBr2(foundation.rpg.sample.json.JsonFactory factory, foundation.rpg.common.symbols.LBr node, State prev) {
        super(factory, node, prev);
    }


// Reduce:
// Shift:
    @Override
    public State visitListOfObject(java.util.List<java.lang.Object> symbol) {
        return new StateListOfObject2(getFactory(), symbol, this);
    }

    @Override
    public State visitRBr(foundation.rpg.common.symbols.RBr symbol) {
        return new StateRBr3(getFactory(), symbol, this);
    }

    @Override
    public State visitObject(java.lang.Object symbol) {
        return new StateObject2(getFactory(), symbol, this);
    }

    @Override
    public State visitString(java.lang.String symbol) {
        return new StateString2(getFactory(), symbol, this);
    }

    @Override
    public State visitInteger(java.lang.Integer symbol) {
        return new StateInteger2(getFactory(), symbol, this);
    }

    @Override
    public State visitDouble(java.lang.Double symbol) {
        return new StateDouble2(getFactory(), symbol, this);
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
}
