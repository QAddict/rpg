package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.UnexpectedInputException;

// Generated visitor pattern based state for grammar parser.
public class State1 extends State {

// NoStack:
    public State1(foundation.rpg.sample.language.ast.AstFactory factory) {
        super(factory);
    }


// Stack:
// Reduce:
    @Override
    public State visitEnd(foundation.rpg.parser.End symbol) throws UnexpectedInputException {
        
        return this.visitList1ListOfStatement(foundation.rpg.common.rules.List1.Rules.is()).visitEnd(symbol);
    }

    @Override
    public State visitIf(foundation.rpg.common.symbols.If symbol) throws UnexpectedInputException {
        
        return this.visitList1ListOfStatement(foundation.rpg.common.rules.List1.Rules.is()).visitIf(symbol);
    }

    @Override
    public State visitIdentifier(foundation.rpg.sample.language.ast.Identifier symbol) throws UnexpectedInputException {
        
        return this.visitList1ListOfStatement(foundation.rpg.common.rules.List1.Rules.is()).visitIdentifier(symbol);
    }

    @Override
    public State visitLong(java.lang.Long symbol) throws UnexpectedInputException {
        
        return this.visitList1ListOfStatement(foundation.rpg.common.rules.List1.Rules.is()).visitLong(symbol);
    }

    @Override
    public State visitString(java.lang.String symbol) throws UnexpectedInputException {
        
        return this.visitList1ListOfStatement(foundation.rpg.common.rules.List1.Rules.is()).visitString(symbol);
    }

    @Override
    public State visitLPar(foundation.rpg.common.symbols.LPar symbol) throws UnexpectedInputException {
        
        return this.visitList1ListOfStatement(foundation.rpg.common.rules.List1.Rules.is()).visitLPar(symbol);
    }


// Shift:
    @Override
    public State visitProgram(foundation.rpg.sample.language.ast.Program symbol) {
        return new StateProgram1(getFactory(), symbol, this);
    }

    @Override
    public State visitList1ListOfStatement(java.util.List<foundation.rpg.sample.language.ast.Statement> symbol) {
        return new StateList1ListOfStatement1(getFactory(), symbol, this);
    }


// Accept:
}
