package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.StateBase;

// Generated visitor pattern based state for grammar parser.
public class State extends StateBase<foundation.rpg.sample.language.ast.Program> {
    private final foundation.rpg.sample.language.ast.AstFactory factory;

    public State(foundation.rpg.sample.language.ast.AstFactory factory) {
        this.factory = factory;
    }

    public foundation.rpg.sample.language.ast.AstFactory getFactory() {
        return factory;
    }

// Ignored:
    public State visitWhiteSpace(foundation.rpg.common.symbols.WhiteSpace symbol) {
        return this;
    }

    public State visitComment(foundation.rpg.common.symbols.Comment symbol) {
        return this;
    }


// Symbols:
    public State visitEnd(foundation.rpg.parser.End symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitIf(foundation.rpg.common.symbols.If symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitThen(foundation.rpg.common.symbols.Then symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitElse(foundation.rpg.common.symbols.Else symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitSemicolon(foundation.rpg.common.symbols.Semicolon symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitIdentifier(foundation.rpg.sample.language.ast.Identifier symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitEqual(foundation.rpg.common.symbols.Equal symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitGt(foundation.rpg.common.symbols.Gt symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitPlus(foundation.rpg.common.symbols.Plus symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitTimes(foundation.rpg.common.symbols.Times symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitLong(java.lang.Long symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitString(java.lang.String symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitLPar(foundation.rpg.common.symbols.LPar symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitRPar(foundation.rpg.common.symbols.RPar symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitComma(foundation.rpg.common.symbols.Comma symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitProgram(foundation.rpg.sample.language.ast.Program symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitSequenceListOfStatement(java.util.List<foundation.rpg.sample.language.ast.Statement> symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitDanglingStatement(foundation.rpg.sample.language.ast.Statement symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitOpenStatement(foundation.rpg.sample.language.ast.Statement symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitStatement(foundation.rpg.sample.language.ast.Statement symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitExpression(foundation.rpg.sample.language.ast.Expression symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitRelationalExpression(foundation.rpg.sample.language.ast.Expression symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitAdditiveExpression(foundation.rpg.sample.language.ast.Expression symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitMultiplicativeExpression(foundation.rpg.sample.language.ast.Expression symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitAtomicExpression(foundation.rpg.sample.language.ast.Expression symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitCommaSeparatedListOfExpression(java.util.List<foundation.rpg.sample.language.ast.Expression> symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitCommaSeparatedNonEmptyListOfExpression(java.util.List<foundation.rpg.sample.language.ast.Expression> symbol) throws UnexpectedInputException {
        return error(symbol);
    }


}
