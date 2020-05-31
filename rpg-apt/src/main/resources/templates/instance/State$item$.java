package $package$;

import foundation.rpg.parser.UnexpectedInputException;

// Generated visitor pattern based state for grammar parser.
public class $class$ extends $parent$ {

// NoStack:
    public $class$($factory$ factory) {
        super(factory);
    }

// Stack:
    public $class$($factory$ factory, $node$ node, $prev$ prev) {
        super(factory, node, prev);
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
        return new State$next$(getFactory(), symbol, this);
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
