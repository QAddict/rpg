package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

import java.util.Arrays;
import java.util.List;

// Generated visitor pattern based state for grammar parser.
public class StateThen1 extends StackState<foundation.rpg.common.symbols.Then, StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.If, ? extends State>>> {

// NoStack:
// Stack:
    public StateThen1(foundation.rpg.sample.language.ast.AstFactory factory, foundation.rpg.common.symbols.Then node, StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.If, ? extends State>> prev) {
        super(factory, node, prev);
    }


// Reduce:
// Shift:
    @Override
    public State visitStatement(foundation.rpg.sample.language.ast.Statement symbol) {
        return new StateStatement2(getFactory(), symbol, this);
    }

    @Override
    public State visitIf(foundation.rpg.common.symbols.If symbol) {
        return new StateIf2(getFactory(), symbol, this);
    }

    @Override
    public State visitExpression(foundation.rpg.sample.language.ast.Expression symbol) {
        return new StateExpression8(getFactory(), symbol, this);
    }

    @Override
    public State visitIdentifier(foundation.rpg.sample.language.ast.Identifier symbol) {
        return new StateIdentifier11(getFactory(), symbol, this);
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
    @Override
    public List<Object> stack() {
        StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.If, ? extends State>> stack1 = this.getPrev();
		StackState<foundation.rpg.common.symbols.If, ? extends State> stack2 = stack1.getPrev();
		State stack3 = stack2.getPrev();
        return Arrays.asList(stack2.getNode(), stack1.getNode(), this.getNode());
    }

}
