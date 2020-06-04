package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.UnexpectedInputException;

// Generated visitor pattern based state for grammar parser.
public class StateStatement1 extends StackState<foundation.rpg.sample.language.ast.Statement, StackState<java.util.List<foundation.rpg.sample.language.ast.Statement>, ? extends State>> {

// NoStack:
// Stack:
    public StateStatement1(foundation.rpg.sample.language.ast.AstFactory factory, foundation.rpg.sample.language.ast.Statement node, StackState<java.util.List<foundation.rpg.sample.language.ast.Statement>, ? extends State> prev) {
        super(factory, node, prev);
    }


// Reduce:
    @Override
    public State visitEnd(foundation.rpg.parser.End symbol) throws UnexpectedInputException {
        StackState<java.util.List<foundation.rpg.sample.language.ast.Statement>, ? extends State> stack1 = this.getPrev();
		State stack2 = stack1.getPrev();
        return stack2.visitList1ListOfStatement(foundation.rpg.common.rules.ListRules.isList1(stack1.getNode(), this.getNode())).visitEnd(symbol);
    }

    @Override
    public State visitIf(foundation.rpg.common.symbols.If symbol) throws UnexpectedInputException {
        StackState<java.util.List<foundation.rpg.sample.language.ast.Statement>, ? extends State> stack1 = this.getPrev();
		State stack2 = stack1.getPrev();
        return stack2.visitList1ListOfStatement(foundation.rpg.common.rules.ListRules.isList1(stack1.getNode(), this.getNode())).visitIf(symbol);
    }

    @Override
    public State visitIdentifier(foundation.rpg.sample.language.ast.Identifier symbol) throws UnexpectedInputException {
        StackState<java.util.List<foundation.rpg.sample.language.ast.Statement>, ? extends State> stack1 = this.getPrev();
		State stack2 = stack1.getPrev();
        return stack2.visitList1ListOfStatement(foundation.rpg.common.rules.ListRules.isList1(stack1.getNode(), this.getNode())).visitIdentifier(symbol);
    }

    @Override
    public State visitLong(java.lang.Long symbol) throws UnexpectedInputException {
        StackState<java.util.List<foundation.rpg.sample.language.ast.Statement>, ? extends State> stack1 = this.getPrev();
		State stack2 = stack1.getPrev();
        return stack2.visitList1ListOfStatement(foundation.rpg.common.rules.ListRules.isList1(stack1.getNode(), this.getNode())).visitLong(symbol);
    }

    @Override
    public State visitString(java.lang.String symbol) throws UnexpectedInputException {
        StackState<java.util.List<foundation.rpg.sample.language.ast.Statement>, ? extends State> stack1 = this.getPrev();
		State stack2 = stack1.getPrev();
        return stack2.visitList1ListOfStatement(foundation.rpg.common.rules.ListRules.isList1(stack1.getNode(), this.getNode())).visitString(symbol);
    }

    @Override
    public State visitLPar(foundation.rpg.common.symbols.LPar symbol) throws UnexpectedInputException {
        StackState<java.util.List<foundation.rpg.sample.language.ast.Statement>, ? extends State> stack1 = this.getPrev();
		State stack2 = stack1.getPrev();
        return stack2.visitList1ListOfStatement(foundation.rpg.common.rules.ListRules.isList1(stack1.getNode(), this.getNode())).visitLPar(symbol);
    }


// Shift:
// Accept:
}
