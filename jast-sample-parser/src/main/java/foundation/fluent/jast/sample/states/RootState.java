package foundation.fluent.jast.sample.states;

import foundation.fluent.jast.common.End;
import foundation.fluent.jast.sample.ast.Root;

class RootState extends StackState<Root, StateVisitor> {
    public RootState(Root symbol, StateVisitor previous) {
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
    public Root result() {
        return getSymbol();
    }
}
