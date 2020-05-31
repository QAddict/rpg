package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.UnexpectedInputException;

// Generated visitor pattern based state for grammar parser.
public class StateAtomicExpression11 extends StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.Times, StackState<foundation.rpg.sample.language.ast.Expression, ? extends State>>> {

// NoStack:
// Stack:
    public StateAtomicExpression11(foundation.rpg.sample.language.ast.AstFactory factory, foundation.rpg.sample.language.ast.Expression node, StackState<foundation.rpg.common.symbols.Times, StackState<foundation.rpg.sample.language.ast.Expression, ? extends State>> prev) {
        super(factory, node, prev);
    }


// Reduce:
    @Override
    public State visitRPar(foundation.rpg.common.symbols.RPar symbol) throws UnexpectedInputException {
        StackState<foundation.rpg.common.symbols.Times, StackState<foundation.rpg.sample.language.ast.Expression, ? extends State>> stack1 = this.getPrev();
		StackState<foundation.rpg.sample.language.ast.Expression, ? extends State> stack2 = stack1.getPrev();
		State stack3 = stack2.getPrev();
        return stack3.visitMultiplicativeExpression(getFactory().is(stack2.getNode(), stack1.getNode(), this.getNode())).visitRPar(symbol);
    }

    @Override
    public State visitGt(foundation.rpg.common.symbols.Gt symbol) throws UnexpectedInputException {
        StackState<foundation.rpg.common.symbols.Times, StackState<foundation.rpg.sample.language.ast.Expression, ? extends State>> stack1 = this.getPrev();
		StackState<foundation.rpg.sample.language.ast.Expression, ? extends State> stack2 = stack1.getPrev();
		State stack3 = stack2.getPrev();
        return stack3.visitMultiplicativeExpression(getFactory().is(stack2.getNode(), stack1.getNode(), this.getNode())).visitGt(symbol);
    }

    @Override
    public State visitPlus(foundation.rpg.common.symbols.Plus symbol) throws UnexpectedInputException {
        StackState<foundation.rpg.common.symbols.Times, StackState<foundation.rpg.sample.language.ast.Expression, ? extends State>> stack1 = this.getPrev();
		StackState<foundation.rpg.sample.language.ast.Expression, ? extends State> stack2 = stack1.getPrev();
		State stack3 = stack2.getPrev();
        return stack3.visitMultiplicativeExpression(getFactory().is(stack2.getNode(), stack1.getNode(), this.getNode())).visitPlus(symbol);
    }

    @Override
    public State visitTimes(foundation.rpg.common.symbols.Times symbol) throws UnexpectedInputException {
        StackState<foundation.rpg.common.symbols.Times, StackState<foundation.rpg.sample.language.ast.Expression, ? extends State>> stack1 = this.getPrev();
		StackState<foundation.rpg.sample.language.ast.Expression, ? extends State> stack2 = stack1.getPrev();
		State stack3 = stack2.getPrev();
        return stack3.visitMultiplicativeExpression(getFactory().is(stack2.getNode(), stack1.getNode(), this.getNode())).visitTimes(symbol);
    }


// Shift:
// Accept:
}
