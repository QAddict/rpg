package foundation.fluent.jast.parser;

import java.util.Deque;
import java.util.function.UnaryOperator;

public class Parser<S extends StateBase> {

    private final S initialState;

    public Parser(S initialState) {
        this.initialState = initialState;
    }


    public S parse(Deque<? extends UnaryOperator<S>> input) {
        S state = initialState;
        while(!state.accepted() && !input.isEmpty()) {
            state = input.poll().apply(state);
        }
        return state;
    }

}
