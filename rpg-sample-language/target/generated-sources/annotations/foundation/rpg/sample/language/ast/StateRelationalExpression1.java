package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

// Generated visitor pattern based state for grammar parser.
public class StateRelationalExpression1 extends StackState<foundation.rpg.sample.language.ast.Expression, State> {

// NoStack:
// Stack:
    public StateRelationalExpression1(foundation.rpg.sample.language.ast.AstFactory factory, foundation.rpg.sample.language.ast.Expression node, State prev) {
        super(factory, node, prev);
    }


// Reduce:
    @Override
    public State visitSemicolon(foundation.rpg.common.symbols.Semicolon symbol) throws UnexpectedInputException {
        State stack1 = this.getPrev();
        return stack1.visitExpression(getFactory().is(this.getNode())).visitSemicolon(symbol);
    }


// Shift:
    @Override
    public State visitGt(foundation.rpg.common.symbols.Gt symbol) {
        return new StateGt1(getFactory(), symbol, this);
    }


// Accept:
}
