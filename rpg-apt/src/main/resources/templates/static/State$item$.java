package $package$;

import foundation.rpg.parser.UnexpectedInputException;

// Generated visitor pattern based state for grammar parser.
public class $class$ extends $parent$ {
// Stack:
    public $class$($node$ node, $prev$ prev) {
        super(node, prev);
    }

// Reduce:
    @Override
    public State visit$name$($type$ symbol) throws UnexpectedInputException {
        $parameters$
        return $start$.visit$result$($factoryCall$).visit$name$(symbol);
    }

// Shift:
    @Override
    public State visit$name$($type$ symbol) {
        return new State$next$(symbol, this);
    }

// Accept:
    @Override
    public boolean accepted() {
        return true;
    }
    @Override
    public $result$ result() {
        return getPrev().getNode();
    }

}
