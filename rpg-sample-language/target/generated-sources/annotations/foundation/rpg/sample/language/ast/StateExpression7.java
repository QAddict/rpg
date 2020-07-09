package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.UnexpectedInputException;

// Generated visitor pattern based state for grammar parser.
public class StateExpression7 extends StackState<foundation.rpg.sample.language.ast.Expression, State> {

// NoStack:
// Stack:
    public StateExpression7(foundation.rpg.sample.language.ast.AstFactory factory, foundation.rpg.sample.language.ast.Expression node, State prev) {
        super(factory, node, prev);
    }


// Reduce:
// Shift:
    @Override
    public State visitSemicolon(foundation.rpg.common.symbols.Semicolon symbol) {
        return new StateSemicolon3(getFactory(), symbol, this);
    }


// Accept:
}
