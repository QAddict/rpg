package foundation.fluent.jast.sample.states;

import foundation.fluent.jast.common.LPar;
import foundation.fluent.jast.sample.ast.Expression;
import foundation.fluent.jast.sample.ast.Identifier;

public class InitialState extends StateVisitor {
    @Override public StateVisitor visit(LPar symbol) {
        return new LParState(symbol, this);
    }
    @Override public StateVisitor visit(Identifier symbol) {
        return new IdentifierState(symbol, this);
    }
    @Override public StateVisitor visit(Expression symbol) {
        return new Expression1State(symbol, this);
    }

}
