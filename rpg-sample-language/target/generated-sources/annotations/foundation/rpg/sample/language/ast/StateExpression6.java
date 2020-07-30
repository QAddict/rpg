package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

import java.util.Arrays;
import java.util.List;

// Generated visitor pattern based state for grammar parser.
public class StateExpression6 extends StackState<foundation.rpg.sample.language.ast.Expression, State> {

// NoStack:
// Stack:
    public StateExpression6(foundation.rpg.sample.language.ast.AstFactory factory, foundation.rpg.sample.language.ast.Expression node, State prev) {
        super(factory, node, prev);
    }


// Reduce:
    @Override
    public State visitRPar(foundation.rpg.common.symbols.RPar symbol) throws UnexpectedInputException {
        State stack1 = this.getPrev();
        return stack1.visitCommaSeparatedNonEmptyListOfExpression(foundation.rpg.common.rules.NonEmpty.Rules.is(this.getNode())).visitRPar(symbol);
    }

    @Override
    public State visitComma(foundation.rpg.common.symbols.Comma symbol) throws UnexpectedInputException {
        State stack1 = this.getPrev();
        return stack1.visitCommaSeparatedNonEmptyListOfExpression(foundation.rpg.common.rules.NonEmpty.Rules.is(this.getNode())).visitComma(symbol);
    }


// Shift:
// Accept:
    @Override
    public List<Object> stack() {
        State stack1 = this.getPrev();
        return Arrays.asList(this.getNode());
    }

}
