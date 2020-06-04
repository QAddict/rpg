package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.UnexpectedInputException;

// Generated visitor pattern based state for grammar parser.
public class StateComma1 extends StackState<foundation.rpg.common.symbols.Comma, StackState<java.util.List<foundation.rpg.sample.language.ast.Expression>, ? extends State>> {

// NoStack:
// Stack:
    public StateComma1(foundation.rpg.sample.language.ast.AstFactory factory, foundation.rpg.common.symbols.Comma node, StackState<java.util.List<foundation.rpg.sample.language.ast.Expression>, ? extends State> prev) {
        super(factory, node, prev);
    }


// Reduce:
// Shift:
    @Override
    public State visitExpression(foundation.rpg.sample.language.ast.Expression symbol) {
        return new StateExpression13(getFactory(), symbol, this);
    }

    @Override
    public State visitRelationalExpression(foundation.rpg.sample.language.ast.Expression symbol) {
        return new StateRelationalExpression6(getFactory(), symbol, this);
    }

    @Override
    public State visitAdditiveExpression(foundation.rpg.sample.language.ast.Expression symbol) {
        return new StateAdditiveExpression6(getFactory(), symbol, this);
    }

    @Override
    public State visitMultiplicativeExpression(foundation.rpg.sample.language.ast.Expression symbol) {
        return new StateMultiplicativeExpression6(getFactory(), symbol, this);
    }

    @Override
    public State visitAtomicExpression(foundation.rpg.sample.language.ast.Expression symbol) {
        return new StateAtomicExpression6(getFactory(), symbol, this);
    }

    @Override
    public State visitIdentifier(foundation.rpg.sample.language.ast.Identifier symbol) {
        return new StateIdentifier6(getFactory(), symbol, this);
    }

    @Override
    public State visitLong(java.lang.Long symbol) {
        return new StateLong6(getFactory(), symbol, this);
    }

    @Override
    public State visitString(java.lang.String symbol) {
        return new StateString6(getFactory(), symbol, this);
    }

    @Override
    public State visitLPar(foundation.rpg.common.symbols.LPar symbol) {
        return new StateLPar8(getFactory(), symbol, this);
    }


// Accept:
}
