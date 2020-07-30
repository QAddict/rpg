package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

import java.util.Arrays;
import java.util.List;

// Generated visitor pattern based state for grammar parser.
public class StateSemicolon3 extends StackState<foundation.rpg.common.symbols.Semicolon, StackState<foundation.rpg.sample.language.ast.Expression, ? extends State>> {

// NoStack:
// Stack:
    public StateSemicolon3(foundation.rpg.sample.language.ast.AstFactory factory, foundation.rpg.common.symbols.Semicolon node, StackState<foundation.rpg.sample.language.ast.Expression, ? extends State> prev) {
        super(factory, node, prev);
    }


// Reduce:
    @Override
    public State visitElse(foundation.rpg.common.symbols.Else symbol) throws UnexpectedInputException {
        StackState<foundation.rpg.sample.language.ast.Expression, ? extends State> stack1 = this.getPrev();
		State stack2 = stack1.getPrev();
        return stack2.visitStatement(getFactory().is(stack1.getNode(), this.getNode())).visitElse(symbol);
    }


// Shift:
// Accept:
    @Override
    public List<Object> stack() {
        StackState<foundation.rpg.sample.language.ast.Expression, ? extends State> stack1 = this.getPrev();
		State stack2 = stack1.getPrev();
        return Arrays.asList(stack1.getNode(), this.getNode());
    }

}
