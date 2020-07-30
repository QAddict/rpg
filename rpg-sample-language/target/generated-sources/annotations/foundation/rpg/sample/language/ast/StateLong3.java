package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

import java.util.Arrays;
import java.util.List;

// Generated visitor pattern based state for grammar parser.
public class StateLong3 extends StackState<java.lang.Long, State> {

// NoStack:
// Stack:
    public StateLong3(foundation.rpg.sample.language.ast.AstFactory factory, java.lang.Long node, State prev) {
        super(factory, node, prev);
    }


// Reduce:
    @Override
    public State visitRPar(foundation.rpg.common.symbols.RPar symbol) throws UnexpectedInputException {
        State stack1 = this.getPrev();
        return stack1.visitAtomicExpression(getFactory().is(this.getNode())).visitRPar(symbol);
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
// Accept:
    @Override
    public List<Object> stack() {
        State stack1 = this.getPrev();
        return Arrays.asList(this.getNode());
    }

}
