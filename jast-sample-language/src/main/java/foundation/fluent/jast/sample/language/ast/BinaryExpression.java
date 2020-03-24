package foundation.fluent.jast.sample.language.ast;

public class BinaryExpression implements Expression {

    private final Expression left;
    private final Expression right;

    public BinaryExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
}
