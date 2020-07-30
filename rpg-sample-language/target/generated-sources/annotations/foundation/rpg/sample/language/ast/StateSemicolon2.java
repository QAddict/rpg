package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

import java.util.Arrays;
import java.util.List;

// Generated visitor pattern based state for grammar parser.
public class StateSemicolon2 extends StackState<foundation.rpg.common.symbols.Semicolon, StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.Equal, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State>>>> {

// NoStack:
// Stack:
    public StateSemicolon2(foundation.rpg.sample.language.ast.AstFactory factory, foundation.rpg.common.symbols.Semicolon node, StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.Equal, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State>>> prev) {
        super(factory, node, prev);
    }


// Reduce:
    @Override
    public State visitEnd(foundation.rpg.parser.End symbol) throws UnexpectedInputException {
        StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.Equal, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State>>> stack1 = this.getPrev();
		StackState<foundation.rpg.common.symbols.Equal, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State>> stack2 = stack1.getPrev();
		StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State> stack3 = stack2.getPrev();
		State stack4 = stack3.getPrev();
        return stack4.visitStatement(getFactory().is(stack3.getNode(), stack2.getNode(), stack1.getNode(), this.getNode())).visitEnd(symbol);
    }

    @Override
    public State visitIf(foundation.rpg.common.symbols.If symbol) throws UnexpectedInputException {
        StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.Equal, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State>>> stack1 = this.getPrev();
		StackState<foundation.rpg.common.symbols.Equal, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State>> stack2 = stack1.getPrev();
		StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State> stack3 = stack2.getPrev();
		State stack4 = stack3.getPrev();
        return stack4.visitStatement(getFactory().is(stack3.getNode(), stack2.getNode(), stack1.getNode(), this.getNode())).visitIf(symbol);
    }

    @Override
    public State visitIdentifier(foundation.rpg.sample.language.ast.Identifier symbol) throws UnexpectedInputException {
        StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.Equal, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State>>> stack1 = this.getPrev();
		StackState<foundation.rpg.common.symbols.Equal, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State>> stack2 = stack1.getPrev();
		StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State> stack3 = stack2.getPrev();
		State stack4 = stack3.getPrev();
        return stack4.visitStatement(getFactory().is(stack3.getNode(), stack2.getNode(), stack1.getNode(), this.getNode())).visitIdentifier(symbol);
    }

    @Override
    public State visitLong(java.lang.Long symbol) throws UnexpectedInputException {
        StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.Equal, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State>>> stack1 = this.getPrev();
		StackState<foundation.rpg.common.symbols.Equal, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State>> stack2 = stack1.getPrev();
		StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State> stack3 = stack2.getPrev();
		State stack4 = stack3.getPrev();
        return stack4.visitStatement(getFactory().is(stack3.getNode(), stack2.getNode(), stack1.getNode(), this.getNode())).visitLong(symbol);
    }

    @Override
    public State visitString(java.lang.String symbol) throws UnexpectedInputException {
        StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.Equal, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State>>> stack1 = this.getPrev();
		StackState<foundation.rpg.common.symbols.Equal, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State>> stack2 = stack1.getPrev();
		StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State> stack3 = stack2.getPrev();
		State stack4 = stack3.getPrev();
        return stack4.visitStatement(getFactory().is(stack3.getNode(), stack2.getNode(), stack1.getNode(), this.getNode())).visitString(symbol);
    }

    @Override
    public State visitLPar(foundation.rpg.common.symbols.LPar symbol) throws UnexpectedInputException {
        StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.Equal, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State>>> stack1 = this.getPrev();
		StackState<foundation.rpg.common.symbols.Equal, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State>> stack2 = stack1.getPrev();
		StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State> stack3 = stack2.getPrev();
		State stack4 = stack3.getPrev();
        return stack4.visitStatement(getFactory().is(stack3.getNode(), stack2.getNode(), stack1.getNode(), this.getNode())).visitLPar(symbol);
    }


// Shift:
// Accept:
    @Override
    public List<Object> stack() {
        StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.Equal, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State>>> stack1 = this.getPrev();
		StackState<foundation.rpg.common.symbols.Equal, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State>> stack2 = stack1.getPrev();
		StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State> stack3 = stack2.getPrev();
		State stack4 = stack3.getPrev();
        return Arrays.asList(stack3.getNode(), stack2.getNode(), stack1.getNode(), this.getNode());
    }

}
