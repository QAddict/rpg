package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

// Generated visitor pattern based state for grammar parser.
public class StateExpression4 extends StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.LPar, ? extends State>> {

// NoStack:
// Stack:
    public StateExpression4(foundation.rpg.sample.language.ast.AstFactory factory, foundation.rpg.sample.language.ast.Expression node, StackState<foundation.rpg.common.symbols.LPar, ? extends State> prev) {
        super(factory, node, prev);
    }


// Reduce:
// Shift:
    @Override
    public State visitRPar(foundation.rpg.common.symbols.RPar symbol) {
        return new StateRPar2(getFactory(), symbol, this);
    }


// Accept:
}
