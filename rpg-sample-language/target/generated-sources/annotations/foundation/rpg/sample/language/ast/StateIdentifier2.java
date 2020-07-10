package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

// Generated visitor pattern based state for grammar parser.
public class StateIdentifier2 extends StackState<foundation.rpg.sample.language.ast.Identifier, State> {

// NoStack:
// Stack:
    public StateIdentifier2(foundation.rpg.sample.language.ast.AstFactory factory, foundation.rpg.sample.language.ast.Identifier node, State prev) {
        super(factory, node, prev);
    }


// Reduce:
    @Override
    public State visitThen(foundation.rpg.common.symbols.Then symbol) throws UnexpectedInputException {
        State stack1 = this.getPrev();
        return stack1.visitAtomicExpression(getFactory().is(this.getNode())).visitThen(symbol);
    }

    @Override
    public State visitGt(foundation.rpg.common.symbols.Gt symbol) throws UnexpectedInputException {
        State stack1 = this.getPrev();
        return stack1.visitAtomicExpression(getFactory().is(this.getNode())).visitGt(symbol);
    }

    @Override
    public State visitPlus(foundation.rpg.common.symbols.Plus symbol) throws UnexpectedInputException {
        State stack1 = this.getPrev();
        return stack1.visitAtomicExpression(getFactory().is(this.getNode())).visitPlus(symbol);
    }

    @Override
    public State visitTimes(foundation.rpg.common.symbols.Times symbol) throws UnexpectedInputException {
        State stack1 = this.getPrev();
        return stack1.visitAtomicExpression(getFactory().is(this.getNode())).visitTimes(symbol);
    }


// Shift:
    @Override
    public State visitLPar(foundation.rpg.common.symbols.LPar symbol) {
        return new StateLPar5(getFactory(), symbol, this);
    }


// Accept:
}
