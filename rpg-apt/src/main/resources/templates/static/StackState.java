package $package$;

// Generated token element wrapper for grammar parser.
public class $class$<T, P> extends State {

    private final T node;
    private final P prev;

    public $class$(T node, P prev) {
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
