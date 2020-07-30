package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

import java.util.Arrays;
import java.util.List;

// Generated visitor pattern based state for grammar parser.
public class StateExpression16 extends StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.Equal, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State>>> {

// NoStack:
// Stack:
    public StateExpression16(foundation.rpg.sample.language.ast.AstFactory factory, foundation.rpg.sample.language.ast.Expression node, StackState<foundation.rpg.common.symbols.Equal, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State>> prev) {
        super(factory, node, prev);
    }


// Reduce:
// Shift:
    @Override
    public State visitSemicolon(foundation.rpg.common.symbols.Semicolon symbol) {
        return new StateSemicolon4(getFactory(), symbol, this);
    }


// Accept:
    @Override
    public List<Object> stack() {
        StackState<foundation.rpg.common.symbols.Equal, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State>> stack1 = this.getPrev();
		StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State> stack2 = stack1.getPrev();
		State stack3 = stack2.getPrev();
        return Arrays.asList(stack2.getNode(), stack1.getNode(), this.getNode());
    }

}
