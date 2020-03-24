package foundation.fluent.jast.sample.language.ast;

public class Operand {
    private final Expression expression;

    public Operand(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }
}
