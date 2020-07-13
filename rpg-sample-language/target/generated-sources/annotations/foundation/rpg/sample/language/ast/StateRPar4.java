package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

import java.util.Arrays;
import java.util.List;

// Generated visitor pattern based state for grammar parser.
public class StateRPar4 extends StackState<foundation.rpg.common.symbols.RPar, StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.LPar, ? extends State>>> {

// NoStack:
// Stack:
    public StateRPar4(foundation.rpg.sample.language.ast.AstFactory factory, foundation.rpg.common.symbols.RPar node, StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.LPar, ? extends State>> prev) {
        super(factory, node, prev);
    }


// Reduce:
    @Override
    public State visitRPar(foundation.rpg.common.symbols.RPar symbol) throws UnexpectedInputException {
        StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.LPar, ? extends State>> stack1 = this.getPrev();
		StackState<foundation.rpg.common.symbols.LPar, ? extends State> stack2 = stack1.getPrev();
		State stack3 = stack2.getPrev();
        return stack3.visitAtomicExpression(getFactory().is(stack2.getNode(), stack1.getNode(), this.getNode())).visitRPar(symbol);
    }

    @Override
    public State visitGt(foundation.rpg.common.symbols.Gt symbol) throws UnexpectedInputException {
        StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.LPar, ? extends State>> stack1 = this.getPrev();
		StackState<foundation.rpg.common.symbols.LPar, ? extends State> stack2 = stack1.getPrev();
		State stack3 = stack2.getPrev();
        return stack3.visitAtomicExpression(getFactory().is(stack2.getNode(), stack1.getNode(), this.getNode())).visitGt(symbol);
    }

    @Override
    public State visitPlus(foundation.rpg.common.symbols.Plus symbol) throws UnexpectedInputException {
        StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.LPar, ? extends State>> stack1 = this.getPrev();
		StackState<foundation.rpg.common.symbols.LPar, ? extends State> stack2 = stack1.getPrev();
		State stack3 = stack2.getPrev();
        return stack3.visitAtomicExpression(getFactory().is(stack2.getNode(), stack1.getNode(), this.getNode())).visitPlus(symbol);
    }

    @Override
    public State visitTimes(foundation.rpg.common.symbols.Times symbol) throws UnexpectedInputException {
        StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.LPar, ? extends State>> stack1 = this.getPrev();
		StackState<foundation.rpg.common.symbols.LPar, ? extends State> stack2 = stack1.getPrev();
		State stack3 = stack2.getPrev();
        return stack3.visitAtomicExpression(getFactory().is(stack2.getNode(), stack1.getNode(), this.getNode())).visitTimes(symbol);
    }


// Shift:
// Accept:
    @Override
    public List<Object> stack() {
        StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.LPar, ? extends State>> stack1 = this.getPrev();
		StackState<foundation.rpg.common.symbols.LPar, ? extends State> stack2 = stack1.getPrev();
		State stack3 = stack2.getPrev();
        return Arrays.asList(stack2.getNode(), stack1.getNode(), this.getNode());
    }

}
