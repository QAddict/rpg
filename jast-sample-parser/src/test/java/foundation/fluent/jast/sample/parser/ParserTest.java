package foundation.fluent.jast.sample.parser;

import foundation.fluent.jast.sample.ast.Expression;
import foundation.fluent.jast.sample.lexer.Lexer;
import foundation.fluent.jast.sample.states.InitialState;
import foundation.fluent.jast.sample.states.StateVisitor;
import org.testng.annotations.Test;
import foundation.fluent.jast.parser.Parser;

import java.io.StringReader;

import static foundation.fluent.jast.sample.tokens.Token.*;
import static java.util.Arrays.asList;

public class ParserTest {

    @Test
    public void testParse() {
        Expression expression = new Parser<StateVisitor>(new InitialState()).parse(asList(
                LPAR,
                LPAR,
                IDENT,
                RPAR,
                PLUS,
                IDENT,
                PLUS,
                LPAR,
                IDENT,
                PLUS,
                IDENT,
                RPAR,
                RPAR,
                END
        ).iterator()).result();
        System.out.println(expression);
    }

    @Test
    public void testParserAndLexer() {
        Lexer lexer = new Lexer(new StringReader("(abcd + efg) + gfds"));
        Parser<StateVisitor> parser = new Parser<>(new InitialState());
        StateVisitor stateVisitor = parser.parse(lexer);
        Expression expression = stateVisitor.result();
        System.out.println(expression);
    }

}
