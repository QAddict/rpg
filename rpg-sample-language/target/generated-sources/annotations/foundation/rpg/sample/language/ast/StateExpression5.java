package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.UnexpectedInputException;

// Generated visitor pattern based state for grammar parser.
public class StateExpression5 extends StackState<foundation.rpg.sample.language.ast.Expression, StackState<foundation.rpg.common.symbols.Equal, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State>>> {

// NoStack:
// Stack:
    public StateExpression5(foundation.rpg.sample.language.ast.AstFactory factory, foundation.rpg.sample.language.ast.Expression node, StackState<foundation.rpg.common.symbols.Equal, StackState<foundation.rpg.sample.language.ast.Identifier, ? extends State>> prev) {
        super(factory, node, prev);
    }


// Reduce:
// Shift:
    @Override
    public State visitSemicolon(foundation.rpg.common.symbols.Semicolon symbol) {
        return new StateSemicolon2(getFactory(), symbol, this);
    }


// Accept:
}
