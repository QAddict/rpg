package foundation.rpg.sample.json;

import foundation.rpg.parser.Lexer;
import foundation.rpg.parser.StreamParser;

import java.util.function.Consumer;

// Generated token element wrapper for grammar parser.
public class JsonParser extends StreamParser<java.lang.Object, State> {

    public JsonParser(foundation.rpg.sample.json.JsonFactory factory, Lexer<State> lexer) {
        super(new State1(factory), lexer);
    }

    public JsonParser(foundation.rpg.sample.json.JsonFactory factory) {
        this(factory, new ObjectLexer(factory));
    }

}
