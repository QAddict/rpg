package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

import java.util.Arrays;
import java.util.List;

// Generated visitor pattern based state for grammar parser.
public class StateAdditiveExpression10 extends StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.Gt, StackState<foundation.rpg.sample.language.ast.Expression, ? extends State>>> {

// NoStack:
// Stack:
    public StateAdditiveExpression10(foundation.rpg.sample.language.ast.AstFactory factory, foundation.rpg.sample.language.ast.Expression node, StackState<foundation.rpg.common.symbols.Gt, StackState<foundation.rpg.sample.language.ast.Expression, ? extends State>> prev) {
        super(factory, node, prev);
    }


// Reduce:
    @Override
    public State visitThen(foundation.rpg.common.symbols.Then symbol) throws UnexpectedInputException {
        StackState<foundation.rpg.common.symbols.Gt, StackState<foundation.rpg.sample.language.ast.Expression, ? extends State>> stack1 = this.getPrev();
		StackState<foundation.rpg.sample.language.ast.Expression, ? extends State> stack2 = stack1.getPrev();
		State stack3 = stack2.getPrev();
        return stack3.visitRelationalExpression(getFactory().is(stack2.getNode(), stack1.getNode(), this.getNode())).visitThen(symbol);
    }

    @Override
    public State visitGt(foundation.rpg.common.symbols.Gt symbol) throws UnexpectedInputException {
        StackState<foundation.rpg.common.symbols.Gt, StackState<foundation.rpg.sample.language.ast.Expression, ? extends State>> stack1 = this.getPrev();
		StackState<foundation.rpg.sample.language.ast.Expression, ? extends State> stack2 = stack1.getPrev();
		State stack3 = stack2.getPrev();
        return stack3.visitRelationalExpression(getFactory().is(stack2.getNode(), stack1.getNode(), this.getNode())).visitGt(symbol);
    }


// Shift:
    @Override
    public State visitPlus(foundation.rpg.common.symbols.Plus symbol) {
        return new StatePlus2(getFactory(), symbol, this);
    }


// Accept:
    @Override
    public List<Object> stack() {
        StackState<foundation.rpg.common.symbols.Gt, StackState<foundation.rpg.sample.language.ast.Expression, ? extends State>> stack1 = this.getPrev();
		StackState<foundation.rpg.sample.language.ast.Expression, ? extends State> stack2 = stack1.getPrev();
		State stack3 = stack2.getPrev();
        return Arrays.asList(stack2.getNode(), stack1.getNode(), this.getNode());
    }

}
