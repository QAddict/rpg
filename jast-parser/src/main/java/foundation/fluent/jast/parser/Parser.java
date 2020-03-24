package foundation.fluent.jast.parser;

import java.util.Iterator;
import java.util.function.UnaryOperator;

public class Parser<S extends StateBase> {

    private final S initialState;

    public Parser(S initialState) {
        this.initialState = initialState;
    }


    public S parse(Iterator<? extends UnaryOperator<S>> input) {
        S state = initialState;
        while(!state.accepted() && input.hasNext()) {
            UnaryOperator<S> next = input.next();
            state = next.apply(state);
        }
        return state;
    }

}
