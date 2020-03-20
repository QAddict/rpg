package foundation.fluent.jast.sample.states;

public class StackState<S, P> extends StateVisitor {

    private final S symbol;
    private final P previous;

    public StackState(S symbol, P previous) {
        this.symbol = symbol;
        this.previous = previous;
    }

    public S getSymbol() {
        return symbol;
    }

    public P getPrevious() {
        return previous;
    }

}
