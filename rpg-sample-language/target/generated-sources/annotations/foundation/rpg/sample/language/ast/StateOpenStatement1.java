package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

import java.util.Arrays;
import java.util.List;

// Generated visitor pattern based state for grammar parser.
public class StateOpenStatement1 extends StackState<foundation.rpg.sample.language.ast.Statement, State> {

// NoStack:
// Stack:
    public StateOpenStatement1(foundation.rpg.sample.language.ast.AstFactory factory, foundation.rpg.sample.language.ast.Statement node, State prev) {
        super(factory, node, prev);
    }


// Reduce:
    @Override
    public State visitEnd(foundation.rpg.parser.End symbol) throws UnexpectedInputException {
        State stack1 = this.getPrev();
        return stack1.visitDanglingStatement(getFactory().is(this.getNode())).visitEnd(symbol);
    }

    @Override
    public State visitIf(foundation.rpg.common.symbols.If symbol) throws UnexpectedInputException {
        State stack1 = this.getPrev();
        return stack1.visitDanglingStatement(getFactory().is(this.getNode())).visitIf(symbol);
    }

    @Override
    public State visitIdentifier(foundation.rpg.sample.language.ast.Identifier symbol) throws UnexpectedInputException {
        State stack1 = this.getPrev();
        return stack1.visitDanglingStatement(getFactory().is(this.getNode())).visitIdentifier(symbol);
    }

    @Override
    public State visitLong(java.lang.Long symbol) throws UnexpectedInputException {
        State stack1 = this.getPrev();
        return stack1.visitDanglingStatement(getFactory().is(this.getNode())).visitLong(symbol);
    }

    @Override
    public State visitString(java.lang.String symbol) throws UnexpectedInputException {
        State stack1 = this.getPrev();
        return stack1.visitDanglingStatement(getFactory().is(this.getNode())).visitString(symbol);
    }

    @Override
    public State visitLPar(foundation.rpg.common.symbols.LPar symbol) throws UnexpectedInputException {
        State stack1 = this.getPrev();
        return stack1.visitDanglingStatement(getFactory().is(this.getNode())).visitLPar(symbol);
    }


// Shift:
// Accept:
    @Override
    public List<Object> stack() {
        State stack1 = this.getPrev();
        return Arrays.asList(this.getNode());
    }

}
