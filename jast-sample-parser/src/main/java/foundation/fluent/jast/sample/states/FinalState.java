package foundation.fluent.jast.sample.states;

import foundation.fluent.jast.common.End;
import foundation.fluent.jast.parser.StateBase;
import foundation.fluent.jast.sample.ast.Expression;

class FinalState extends StackState<End, StackState<Expression, ? extends StateBase>> {
    public FinalState(End symbol, StackState<Expression, ? extends StateBase> previous) {
        super(symbol, previous);
    }
    @Override public StateVisitor visit(End symbol) {
        return this;
    }

    @Override
    public boolean accepted() {
        return true;
    }

    @Override
    public Expression result() {
        return getPrevious().getSymbol();
    }
}
