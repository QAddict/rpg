package foundation.fluent.jast.sample.language.ast;

public class BinaryExpression implements Expression {

    private final Expression left;
    private final Operand right;

    public BinaryExpression(Expression left, Operand right) {
        this.left = left;
        this.right = right;
    }
}
