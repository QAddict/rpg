package foundation.fluent.jast.sample.language.ast;

import foundation.fluent.jast.StartSymbol;
import foundation.fluent.jast.common.*;

import java.util.ArrayList;
import java.util.List;

public class AstFactory {

    @StartSymbol
    public static Program program(List<Statement> statements, End end) {
        return new Program();
    }

    public static List<Statement> statements() {
        return new ArrayList<>();
    }

    public static List<Statement> statements(List<Statement> statements, Statement statement) {
        statements.add(statement);
        return statements;
    }

    public static Statement statement(Expression expression, Dot dot) {
        return new ExpressionStatement();
    }

    public static Expression expression(Identifier identifier) {
        return identifier;
    }

    public static Expression expression(Expression left, Plus plus, Identifier right) {
        return new BinaryExpression();
    }

    public static Expression expression(LPar l, Expression expression, RPar r) {
        return expression;
    }

}
