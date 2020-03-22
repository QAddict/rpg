package foundation.fluent.jast.sample.states;

import foundation.fluent.jast.common.End;
import foundation.fluent.jast.parser.State;
import foundation.fluent.jast.sample.ast.Expression;

class FinalState extends StackState<End, StackState<Expression, ? extends State>> {
    public FinalState(End symbol, StackState<Expression, ? extends State> previous) {
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
