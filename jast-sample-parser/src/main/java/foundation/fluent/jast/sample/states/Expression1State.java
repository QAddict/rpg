package foundation.fluent.jast.sample.states;

import foundation.fluent.jast.common.End;
import foundation.fluent.jast.common.Plus;
import foundation.fluent.jast.sample.ast.Expression;
import foundation.fluent.jast.sample.ast.Root;

class Expression1State extends StackState<Expression, StateVisitor> {
    public Expression1State(Expression symbol, StateVisitor previous) {
        super(symbol, previous);
    }
    @Override
    public StateVisitor visit(End symbol) {
        return getPrevious().visit(new Root(getSymbol())).visit(symbol);
    }

    @Override public StateVisitor visit(Plus symbol) {
        return new PlusState(symbol, this);
    }
}
