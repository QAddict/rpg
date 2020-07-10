package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

// Generated visitor pattern based state for grammar parser.
public class StateLPar3 extends StackState<foundation.rpg.common.symbols.LPar, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State>> {

// NoStack:
// Stack:
    public StateLPar3(foundation.rpg.sample.language.ast.AstFactory factory, foundation.rpg.common.symbols.LPar node, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State> prev) {
        super(factory, node, prev);
    }


// Reduce:
    @Override
    public State visitRPar(foundation.rpg.common.symbols.RPar symbol) throws UnexpectedInputException {
        
        return this.visitList3ListOfExpression(foundation.rpg.common.rules.List3.Rules.is()).visitRPar(symbol);
    }


// Shift:
    @Override
    public State visitList3ListOfExpression(java.util.List<foundation.rpg.sample.language.ast.Expression> symbol) {
        return new StateList3ListOfExpression1(getFactory(), symbol, this);
    }


// Accept:
}
