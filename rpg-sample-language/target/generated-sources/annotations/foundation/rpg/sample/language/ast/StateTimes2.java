package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.UnexpectedInputException;

// Generated visitor pattern based state for grammar parser.
public class StateTimes2 extends StackState<foundation.rpg.common.symbols.Times, StackState<foundation.rpg.sample.language.ast.Expression, ? extends State>> {

// NoStack:
// Stack:
    public StateTimes2(foundation.rpg.sample.language.ast.AstFactory factory, foundation.rpg.common.symbols.Times node, StackState<foundation.rpg.sample.language.ast.Expression, ? extends State> prev) {
        super(factory, node, prev);
    }


// Reduce:
// Shift:
    @Override
    public State visitAtomicExpression(foundation.rpg.sample.language.ast.Expression symbol) {
        return new StateAtomicExpression13(getFactory(), symbol, this);
    }

    @Override
    public State visitIdentifier(foundation.rpg.sample.language.ast.Identifier symbol) {
        return new StateIdentifier2(getFactory(), symbol, this);
    }

    @Override
    public State visitLong(java.lang.Long symbol) {
        return new StateLong2(getFactory(), symbol, this);
    }

    @Override
    public State visitString(java.lang.String symbol) {
        return new StateString2(getFactory(), symbol, this);
    }

    @Override
    public State visitLPar(foundation.rpg.common.symbols.LPar symbol) {
        return new StateLPar2(getFactory(), symbol, this);
    }


// Accept:
}
