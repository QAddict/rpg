package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

// Generated visitor pattern based state for grammar parser.
public class StateExpression2 extends StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.If, ? extends State>> {

// NoStack:
// Stack:
    public StateExpression2(foundation.rpg.sample.language.ast.AstFactory factory, foundation.rpg.sample.language.ast.Expression node, StackState<foundation.rpg.common.symbols.If, ? extends State> prev) {
        super(factory, node, prev);
    }


// Reduce:
// Shift:
    @Override
    public State visitThen(foundation.rpg.common.symbols.Then symbol) {
        return new StateThen1(getFactory(), symbol, this);
    }


// Accept:
}
