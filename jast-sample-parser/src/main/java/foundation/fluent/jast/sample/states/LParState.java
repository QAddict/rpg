package foundation.fluent.jast.sample.states;

import foundation.fluent.jast.common.LPar;
import foundation.fluent.jast.sample.ast.Expression;
import foundation.fluent.jast.sample.ast.Identifier;

class LParState extends StackState<LPar, StateVisitor> {
    public LParState(LPar symbol, StateVisitor previous) {
        super(symbol, previous);
    }
    @Override public StateVisitor visit(LPar symbol) {
        return new LParState(symbol, this);
    }
    @Override public StateVisitor visit(Identifier symbol) {
        return new IdentifierState(symbol, this);
    }
    @Override public StateVisitor visit(Expression symbol) {
        return new Expression2State(symbol, this);
    }
}
