package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.UnexpectedInputException;

// Generated visitor pattern based state for grammar parser.
public class StateList2ListOfExpression1 extends StackState<java.util.List<foundation.rpg.sample.language.ast.Expression>, State> {

// NoStack:
// Stack:
    public StateList2ListOfExpression1(foundation.rpg.sample.language.ast.AstFactory factory, java.util.List<foundation.rpg.sample.language.ast.Expression> node, State prev) {
        super(factory, node, prev);
    }


// Reduce:
    @Override
    public State visitRPar(foundation.rpg.common.symbols.RPar symbol) throws UnexpectedInputException {
        State stack1 = this.getPrev();
        return stack1.visitList3ListOfExpression(foundation.rpg.common.rules.ListRules.isList3(this.getNode())).visitRPar(symbol);
    }


// Shift:
    @Override
    public State visitComma(foundation.rpg.common.symbols.Comma symbol) {
        return new StateComma1(getFactory(), symbol, this);
    }


// Accept:
}
