package foundation.fluent.jast.sample.language;

import foundation.fluent.jast.*;
import foundation.fluent.jast.common.*;
import foundation.fluent.jast.parser.Parser;
import foundation.fluent.jast.sample.language.ast.Identifier;
import foundation.fluent.jast.sample.language.ast.Program;
import org.testng.annotations.Test;

import static java.util.Arrays.asList;

public class LanguageParserTest {

    @Test
    public void testParser() {
        Parser<State> parser = new Parser<>(new State1());
        Program program = parser.parse(asList(
                new TokenComment(new Comment()),
                new TokenLPar(LPar.SYMBOL),
                new TokenWhiteSpace(new WhiteSpace()),
                new TokenIdentifier(new Identifier("variable")),
                new TokenRPar(RPar.SYMBOL),
                new TokenDot(Dot.SYMBOL),
                new TokenEnd(End.SYMBOL)
        ).iterator()).result();
        System.out.println(program);
    }

}
