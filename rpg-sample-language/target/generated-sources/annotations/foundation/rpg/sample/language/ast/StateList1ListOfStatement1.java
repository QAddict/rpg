package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

// Generated visitor pattern based state for grammar parser.
public class StateList1ListOfStatement1 extends StackState<java.util.List<foundation.rpg.sample.language.ast.Statement>, State> {

// NoStack:
// Stack:
    public StateList1ListOfStatement1(foundation.rpg.sample.language.ast.AstFactory factory, java.util.List<foundation.rpg.sample.language.ast.Statement> node, State prev) {
        super(factory, node, prev);
    }


// Reduce:
    @Override
    public State visitEnd(foundation.rpg.parser.End symbol) throws UnexpectedInputException {
        State stack1 = this.getPrev();
        return stack1.visitProgram(getFactory().is(this.getNode())).visitEnd(symbol);
    }


// Shift:
    @Override
    public State visitStatement(foundation.rpg.sample.language.ast.Statement symbol) {
        return new StateStatement1(getFactory(), symbol, this);
    }

    @Override
    public State visitIf(foundation.rpg.common.symbols.If symbol) {
        return new StateIf1(getFactory(), symbol, this);
    }

    @Override
    public State visitExpression(foundation.rpg.sample.language.ast.Expression symbol) {
        return new StateExpression1(getFactory(), symbol, this);
    }

    @Override
    public State visitIdentifier(foundation.rpg.sample.language.ast.Identifier symbol) {
        return new StateIdentifier1(getFactory(), symbol, this);
    }

    @Override
    public State visitRelationalExpression(foundation.rpg.sample.language.ast.Expression symbol) {
        return new StateRelationalExpression1(getFactory(), symbol, this);
    }

    @Override
    public State visitAdditiveExpression(foundation.rpg.sample.language.ast.Expression symbol) {
        return new StateAdditiveExpression1(getFactory(), symbol, this);
    }

    @Override
    public State visitMultiplicativeExpression(foundation.rpg.sample.language.ast.Expression symbol) {
        return new StateMultiplicativeExpression1(getFactory(), symbol, this);
    }

    @Override
    public State visitAtomicExpression(foundation.rpg.sample.language.ast.Expression symbol) {
        return new StateAtomicExpression1(getFactory(), symbol, this);
    }

    @Override
    public State visitLong(java.lang.Long symbol) {
        return new StateLong1(getFactory(), symbol, this);
    }

    @Override
    public State visitString(java.lang.String symbol) {
        return new StateString1(getFactory(), symbol, this);
    }

    @Override
    public State visitLPar(foundation.rpg.common.symbols.LPar symbol) {
        return new StateLPar1(getFactory(), symbol, this);
    }


// Accept:
}
