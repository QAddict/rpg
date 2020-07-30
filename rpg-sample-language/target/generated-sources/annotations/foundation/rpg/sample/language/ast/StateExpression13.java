package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

import java.util.Arrays;
import java.util.List;

// Generated visitor pattern based state for grammar parser.
public class StateExpression13 extends StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.Comma, StackState<java.util.List<foundation.rpg.sample.language.ast.Expression>, ? extends State>>> {

// NoStack:
// Stack:
    public StateExpression13(foundation.rpg.sample.language.ast.AstFactory factory, foundation.rpg.sample.language.ast.Expression node, StackState<foundation.rpg.common.symbols.Comma, StackState<java.util.List<foundation.rpg.sample.language.ast.Expression>, ? extends State>> prev) {
        super(factory, node, prev);
    }


// Reduce:
    @Override
    public State visitRPar(foundation.rpg.common.symbols.RPar symbol) throws UnexpectedInputException {
        StackState<foundation.rpg.common.symbols.Comma, StackState<java.util.List<foundation.rpg.sample.language.ast.Expression>, ? extends State>> stack1 = this.getPrev();
		StackState<java.util.List<foundation.rpg.sample.language.ast.Expression>, ? extends State> stack2 = stack1.getPrev();
		State stack3 = stack2.getPrev();
        return stack3.visitCommaSeparatedNonEmptyListOfExpression(foundation.rpg.common.rules.NonEmpty.Rules.is(stack2.getNode(), stack1.getNode(), this.getNode())).visitRPar(symbol);
    }

    @Override
    public State visitComma(foundation.rpg.common.symbols.Comma symbol) throws UnexpectedInputException {
        StackState<foundation.rpg.common.symbols.Comma, StackState<java.util.List<foundation.rpg.sample.language.ast.Expression>, ? extends State>> stack1 = this.getPrev();
		StackState<java.util.List<foundation.rpg.sample.language.ast.Expression>, ? extends State> stack2 = stack1.getPrev();
		State stack3 = stack2.getPrev();
        return stack3.visitCommaSeparatedNonEmptyListOfExpression(foundation.rpg.common.rules.NonEmpty.Rules.is(stack2.getNode(), stack1.getNode(), this.getNode())).visitComma(symbol);
    }


// Shift:
// Accept:
    @Override
    public List<Object> stack() {
        StackState<foundation.rpg.common.symbols.Comma, StackState<java.util.List<foundation.rpg.sample.language.ast.Expression>, ? extends State>> stack1 = this.getPrev();
		StackState<java.util.List<foundation.rpg.sample.language.ast.Expression>, ? extends State> stack2 = stack1.getPrev();
		State stack3 = stack2.getPrev();
        return Arrays.asList(stack2.getNode(), stack1.getNode(), this.getNode());
    }

}
