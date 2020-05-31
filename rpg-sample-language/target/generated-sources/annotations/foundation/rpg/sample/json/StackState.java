package foundation.rpg.sample.json;

// Generated token element wrapper for grammar parser.
public class StackState<T, P> extends State {

    private final T node;
    private final P prev;

    public StackState(foundation.rpg.sample.json.JsonFactory factory, T node, P prev) {
        super(factory);
        this.node = node;
        this.prev = prev;
    }

    public final T getNode() {
        return node;
    }

    public final P getPrev() {
        return prev;
    }

}
