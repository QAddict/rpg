package foundation.fluent.jast.sample.states;

import foundation.fluent.jast.sample.ast.AstFactory;
import foundation.fluent.jast.sample.ast.Expression;

class Expression3State extends StackState<Expression, PlusState> {
    public Expression3State(Expression symbol, PlusState previous) {
        super(symbol, previous);
    }
    @Override
    public StateVisitor visitAny(Object object) {
        PlusState plusState = getPrevious();
        StackState<Expression, ? extends StateVisitor> expression1State = plusState.getPrevious();
        return expression1State.getPrevious().visit(AstFactory.expression(expression1State.getSymbol(), plusState.getSymbol(), getSymbol()));
    }
}
