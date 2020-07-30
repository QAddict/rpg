package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

import java.util.Arrays;
import java.util.List;

// Generated visitor pattern based state for grammar parser.
public class StateCommaSeparatedNonEmptyListOfExpression1 extends StackState<java.util.List<foundation.rpg.sample.language.ast.Expression>, State> {

// NoStack:
// Stack:
    public StateCommaSeparatedNonEmptyListOfExpression1(foundation.rpg.sample.language.ast.AstFactory factory, java.util.List<foundation.rpg.sample.language.ast.Expression> node, State prev) {
        super(factory, node, prev);
    }


// Reduce:
    @Override
    public State visitRPar(foundation.rpg.common.symbols.RPar symbol) throws UnexpectedInputException {
        State stack1 = this.getPrev();
        return stack1.visitCommaSeparatedListOfExpression(foundation.rpg.common.rules.CommaSeparated.Rules.is(this.getNode())).visitRPar(symbol);
    }


// Shift:
    @Override
    public State visitComma(foundation.rpg.common.symbols.Comma symbol) {
        return new StateComma1(getFactory(), symbol, this);
    }


// Accept:
    @Override
    public List<Object> stack() {
        State stack1 = this.getPrev();
        return Arrays.asList(this.getNode());
    }

}
