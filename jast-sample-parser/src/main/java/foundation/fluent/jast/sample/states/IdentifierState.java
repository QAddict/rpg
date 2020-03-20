package foundation.fluent.jast.sample.states;

import foundation.fluent.jast.sample.ast.Expression;
import foundation.fluent.jast.sample.ast.Identifier;

class IdentifierState extends StackState<Identifier, StateVisitor> {
    public IdentifierState(Identifier symbol, StateVisitor previous) {
        super(symbol, previous);
    }
    @Override public StateVisitor visitAny(Object object) {
        return getPrevious().visit((Expression) getSymbol());
    }
}
