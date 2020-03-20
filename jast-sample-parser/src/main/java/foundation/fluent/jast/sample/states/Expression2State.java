package foundation.fluent.jast.sample.states;

import foundation.fluent.jast.common.Plus;
import foundation.fluent.jast.common.RPar;
import foundation.fluent.jast.sample.ast.Expression;

class Expression2State extends StackState<Expression, LParState> {
    public Expression2State(Expression symbol, LParState previous) {
        super(symbol, previous);
    }
    @Override
    public StateVisitor visit(Plus symbol) {
        return new PlusState(symbol, this);
    }

    @Override public StateVisitor visit(RPar symbol) {
        return new RParState(symbol, this);
    }
}
