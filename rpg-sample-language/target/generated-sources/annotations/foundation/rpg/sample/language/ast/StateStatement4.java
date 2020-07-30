package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

import java.util.Arrays;
import java.util.List;

// Generated visitor pattern based state for grammar parser.
public class StateStatement4 extends StackState<foundation.rpg.sample.language.ast.Statement, StackState<foundation.rpg.common.symbols.Then, StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.If, ? extends State>>>> {

// NoStack:
// Stack:
    public StateStatement4(foundation.rpg.sample.language.ast.AstFactory factory, foundation.rpg.sample.language.ast.Statement node, StackState<foundation.rpg.common.symbols.Then, StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.If, ? extends State>>> prev) {
        super(factory, node, prev);
    }


// Reduce:
// Shift:
    @Override
    public State visitElse(foundation.rpg.common.symbols.Else symbol) {
        return new StateElse2(getFactory(), symbol, this);
    }


// Accept:
    @Override
    public List<Object> stack() {
        StackState<foundation.rpg.common.symbols.Then, StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.If, ? extends State>>> stack1 = this.getPrev();
		StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.If, ? extends State>> stack2 = stack1.getPrev();
		StackState<foundation.rpg.common.symbols.If, ? extends State> stack3 = stack2.getPrev();
		State stack4 = stack3.getPrev();
        return Arrays.asList(stack3.getNode(), stack2.getNode(), stack1.getNode(), this.getNode());
    }

}
