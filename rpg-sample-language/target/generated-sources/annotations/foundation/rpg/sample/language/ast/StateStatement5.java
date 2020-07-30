package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

import java.util.Arrays;
import java.util.List;

// Generated visitor pattern based state for grammar parser.
public class StateStatement5 extends StackState<foundation.rpg.sample.language.ast.Statement, StackState<foundation.rpg.common.symbols.Else, StackState<foundation.rpg.sample.language.ast.Statement, StackState<foundation.rpg.common.symbols.Then, StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.If, ? extends State>>>>>> {

// NoStack:
// Stack:
    public StateStatement5(foundation.rpg.sample.language.ast.AstFactory factory, foundation.rpg.sample.language.ast.Statement node, StackState<foundation.rpg.common.symbols.Else, StackState<foundation.rpg.sample.language.ast.Statement, StackState<foundation.rpg.common.symbols.Then, StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.If, ? extends State>>>>> prev) {
        super(factory, node, prev);
    }


// Reduce:
    @Override
    public State visitElse(foundation.rpg.common.symbols.Else symbol) throws UnexpectedInputException {
        StackState<foundation.rpg.common.symbols.Else, StackState<foundation.rpg.sample.language.ast.Statement, StackState<foundation.rpg.common.symbols.Then, StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.If, ? extends State>>>>> stack1 = this.getPrev();
		StackState<foundation.rpg.sample.language.ast.Statement, StackState<foundation.rpg.common.symbols.Then, StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.If, ? extends State>>>> stack2 = stack1.getPrev();
		StackState<foundation.rpg.common.symbols.Then, StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.If, ? extends State>>> stack3 = stack2.getPrev();
		StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.If, ? extends State>> stack4 = stack3.getPrev();
		StackState<foundation.rpg.common.symbols.If, ? extends State> stack5 = stack4.getPrev();
		State stack6 = stack5.getPrev();
        return stack6.visitStatement(getFactory().is2(stack5.getNode(), stack4.getNode(), stack3.getNode(), stack2.getNode(), stack1.getNode(), this.getNode())).visitElse(symbol);
    }


// Shift:
// Accept:
    @Override
    public List<Object> stack() {
        StackState<foundation.rpg.common.symbols.Else, StackState<foundation.rpg.sample.language.ast.Statement, StackState<foundation.rpg.common.symbols.Then, StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.If, ? extends State>>>>> stack1 = this.getPrev();
		StackState<foundation.rpg.sample.language.ast.Statement, StackState<foundation.rpg.common.symbols.Then, StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.If, ? extends State>>>> stack2 = stack1.getPrev();
		StackState<foundation.rpg.common.symbols.Then, StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.If, ? extends State>>> stack3 = stack2.getPrev();
		StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.If, ? extends State>> stack4 = stack3.getPrev();
		StackState<foundation.rpg.common.symbols.If, ? extends State> stack5 = stack4.getPrev();
		State stack6 = stack5.getPrev();
        return Arrays.asList(stack5.getNode(), stack4.getNode(), stack3.getNode(), stack2.getNode(), stack1.getNode(), this.getNode());
    }

}
