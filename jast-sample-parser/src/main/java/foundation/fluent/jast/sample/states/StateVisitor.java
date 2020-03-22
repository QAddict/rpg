package foundation.fluent.jast.sample.states;

import foundation.fluent.jast.common.End;
import foundation.fluent.jast.common.LPar;
import foundation.fluent.jast.common.Plus;
import foundation.fluent.jast.common.RPar;
import foundation.fluent.jast.parser.State;
import foundation.fluent.jast.sample.ast.Expression;
import foundation.fluent.jast.sample.ast.Identifier;

public class StateVisitor implements State {

    public StateVisitor visitAny(Object object) {
        throw new IllegalStateException(getClass().getSimpleName() + ": Unexpected " + object.getClass().getSimpleName());
    }

    public StateVisitor visit(End symbol) {
        return visitAny(symbol).visit(symbol);
    }

    public StateVisitor visit(LPar symbol) {
        return visitAny(symbol).visit(symbol);
    }

    public StateVisitor visit(RPar symbol) {
        return visitAny(symbol).visit(symbol);
    }

    public StateVisitor visit(Plus symbol) {
        return visitAny(symbol).visit(symbol);
    }

    public StateVisitor visit(Identifier symbol) {
        return visitAny(symbol).visit(symbol);
    }

    public StateVisitor visit(Expression symbol) {
        return visitAny(symbol).visit(symbol);
    }

    public boolean accepted() {
        return false;
    }

    public Expression result() {
        throw new IllegalStateException(getClass().getSimpleName() + ": Result not available.");
    }

}
