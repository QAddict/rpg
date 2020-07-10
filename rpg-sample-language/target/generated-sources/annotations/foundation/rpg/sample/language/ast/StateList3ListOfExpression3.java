package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

// Generated visitor pattern based state for grammar parser.
public class StateList3ListOfExpression3 extends StackState<java.util.List<foundation.rpg.sample.language.ast.Expression>, StackState<foundation.rpg.common.symbols.LPar, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State>>> {

// NoStack:
// Stack:
    public StateList3ListOfExpression3(foundation.rpg.sample.language.ast.AstFactory factory, java.util.List<foundation.rpg.sample.language.ast.Expression> node, StackState<foundation.rpg.common.symbols.LPar, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State>> prev) {
        super(factory, node, prev);
    }


// Reduce:
// Shift:
    @Override
    public State visitRPar(foundation.rpg.common.symbols.RPar symbol) {
        return new StateRPar6(getFactory(), symbol, this);
    }


// Accept:
}
