package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.UnexpectedInputException;

// Generated visitor pattern based state for grammar parser.
public class StateExpression3 extends StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.LPar, ? extends State>> {

// NoStack:
// Stack:
    public StateExpression3(foundation.rpg.sample.language.ast.AstFactory factory, foundation.rpg.sample.language.ast.Expression node, StackState<foundation.rpg.common.symbols.LPar, ? extends State> prev) {
        super(factory, node, prev);
    }


// Reduce:
// Shift:
    @Override
    public State visitRPar(foundation.rpg.common.symbols.RPar symbol) {
        return new StateRPar1(getFactory(), symbol, this);
    }


// Accept:
}
