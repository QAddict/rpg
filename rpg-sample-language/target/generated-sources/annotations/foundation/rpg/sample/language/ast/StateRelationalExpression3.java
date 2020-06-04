package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.UnexpectedInputException;

// Generated visitor pattern based state for grammar parser.
public class StateRelationalExpression3 extends StackState<foundation.rpg.sample.language.ast.Expression, State> {

// NoStack:
// Stack:
    public StateRelationalExpression3(foundation.rpg.sample.language.ast.AstFactory factory, foundation.rpg.sample.language.ast.Expression node, State prev) {
        super(factory, node, prev);
    }


// Reduce:
    @Override
    public State visitRPar(foundation.rpg.common.symbols.RPar symbol) throws UnexpectedInputException {
        State stack1 = this.getPrev();
        return stack1.visitExpression(getFactory().is(this.getNode())).visitRPar(symbol);
    }


// Shift:
    @Override
    public State visitGt(foundation.rpg.common.symbols.Gt symbol) {
        return new StateGt3(getFactory(), symbol, this);
    }


// Accept:
}
