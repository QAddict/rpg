package foundation.rpg.sample.json;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

// Generated visitor pattern based state for grammar parser.
public class StateLCurl2 extends StackState<foundation.rpg.common.symbols.LCurl, State> {

// NoStack:
// Stack:
    public StateLCurl2(foundation.rpg.sample.json.JsonFactory factory, foundation.rpg.common.symbols.LCurl node, State prev) {
        super(factory, node, prev);
    }


// Reduce:
// Shift:
    @Override
    public State visitMapOfObject(java.util.Map<java.lang.String,java.lang.Object> symbol) {
        return new StateMapOfObject2(getFactory(), symbol, this);
    }

    @Override
    public State visitRCurl(foundation.rpg.common.symbols.RCurl symbol) {
        return new StateRCurl2(getFactory(), symbol, this);
    }

    @Override
    public State visitString(java.lang.String symbol) {
        return new StateString3(getFactory(), symbol, this);
    }

    @Override
    public State visitToken(@Named("string") foundation.rpg.parser.Token symbol) {
        return new StateToken3(getFactory(), symbol, this);
    }

    @Override
    public State visitToken$(@Named("identifier") foundation.rpg.parser.Token symbol) {
        return new StateToken$3(getFactory(), symbol, this);
    }


// Accept:
}
