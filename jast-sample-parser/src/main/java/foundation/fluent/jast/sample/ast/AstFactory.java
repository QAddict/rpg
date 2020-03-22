package foundation.fluent.jast.sample.ast;

import foundation.fluent.jast.common.LPar;
import foundation.fluent.jast.common.Plus;
import foundation.fluent.jast.common.RPar;

public class AstFactory {

    public static Expression expression(LPar l, Expression expression, RPar r) {
        return expression;
    }

    public static Expression expression(Expression left, Plus p, Expression right) {
        return new Addition(left, right);
    }

}
