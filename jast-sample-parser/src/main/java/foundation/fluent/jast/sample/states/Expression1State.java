package foundation.fluent.jast.sample.states;

import foundation.fluent.jast.common.End;
import foundation.fluent.jast.common.Plus;
import foundation.fluent.jast.sample.ast.Expression;

class Expression1State extends StackState<Expression, StateVisitor> {
    public Expression1State(Expression symbol, StateVisitor previous) {
        super(symbol, previous);
    }
    @Override
    public StateVisitor visit(End symbol) {
        return new FinalState(symbol, this);
    }

    @Override public StateVisitor visit(Plus symbol) {
        return new PlusState(symbol, this);
    }
}
