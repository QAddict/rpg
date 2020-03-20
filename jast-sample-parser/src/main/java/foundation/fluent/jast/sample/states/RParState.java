package foundation.fluent.jast.sample.states;

import foundation.fluent.jast.common.RPar;
import foundation.fluent.jast.sample.ast.AstFactory;

class RParState extends StackState<RPar, Expression2State> {
    public RParState(RPar symbol, Expression2State previous) {
        super(symbol, previous);
    }
    @Override
    public StateVisitor visitAny(Object object) {
        Expression2State exp = getPrevious();
        LParState lpar = exp.getPrevious();
        return lpar.getPrevious().visit(AstFactory.expression(lpar.getSymbol(), exp.getSymbol(), getSymbol()));
    }
}
