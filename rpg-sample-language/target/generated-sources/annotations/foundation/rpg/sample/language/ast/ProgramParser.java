package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.Lexer;
import foundation.rpg.parser.StreamParser;

import java.util.function.Consumer;

// Generated token element wrapper for grammar parser.
public class ProgramParser extends StreamParser<foundation.rpg.sample.language.ast.Program, State> {

    public ProgramParser(foundation.rpg.sample.language.ast.AstFactory factory, Lexer<State> lexer) {
        super(new State1(factory), lexer);
    }

    public ProgramParser(foundation.rpg.sample.language.ast.AstFactory factory) {
        this(factory, new GeneratedLexer(factory));
    }

}
