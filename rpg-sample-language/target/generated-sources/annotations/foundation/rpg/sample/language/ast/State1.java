package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

import java.util.Arrays;
import java.util.List;

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
        
        return this.visitSequenceListOfStatement(foundation.rpg.common.rules.Sequence.Rules.is()).visitEnd(symbol);
    }

    @Override
    public State visitIf(foundation.rpg.common.symbols.If symbol) throws UnexpectedInputException {
        
        return this.visitSequenceListOfStatement(foundation.rpg.common.rules.Sequence.Rules.is()).visitIf(symbol);
    }

    @Override
    public State visitIdentifier(foundation.rpg.sample.language.ast.Identifier symbol) throws UnexpectedInputException {
        
        return this.visitSequenceListOfStatement(foundation.rpg.common.rules.Sequence.Rules.is()).visitIdentifier(symbol);
    }

    @Override
    public State visitLong(java.lang.Long symbol) throws UnexpectedInputException {
        
        return this.visitSequenceListOfStatement(foundation.rpg.common.rules.Sequence.Rules.is()).visitLong(symbol);
    }

    @Override
    public State visitString(java.lang.String symbol) throws UnexpectedInputException {
        
        return this.visitSequenceListOfStatement(foundation.rpg.common.rules.Sequence.Rules.is()).visitString(symbol);
    }

    @Override
    public State visitLPar(foundation.rpg.common.symbols.LPar symbol) throws UnexpectedInputException {
        
        return this.visitSequenceListOfStatement(foundation.rpg.common.rules.Sequence.Rules.is()).visitLPar(symbol);
    }


// Shift:
    @Override
    public State visitProgram(foundation.rpg.sample.language.ast.Program symbol) {
        return new StateProgram1(getFactory(), symbol, this);
    }

    @Override
    public State visitSequenceListOfStatement(java.util.List<foundation.rpg.sample.language.ast.Statement> symbol) {
        return new StateSequenceListOfStatement1(getFactory(), symbol, this);
    }


// Accept:
    @Override
    public List<Object> stack() {
        
        return Arrays.asList();
    }

}
