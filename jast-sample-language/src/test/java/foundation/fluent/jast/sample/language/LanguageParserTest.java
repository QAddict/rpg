package foundation.fluent.jast.sample.language;

import foundation.fluent.jast.*;
import foundation.fluent.jast.common.Dot;
import foundation.fluent.jast.common.End;
import foundation.fluent.jast.common.LPar;
import foundation.fluent.jast.common.RPar;
import foundation.fluent.jast.parser.Parser;
import foundation.fluent.jast.sample.language.ast.Identifier;
import foundation.fluent.jast.sample.language.ast.Program;
import org.testng.annotations.Test;

import java.util.LinkedList;

import static java.util.Arrays.asList;

public class LanguageParserTest {

    @Test
    public void testParser() {
        Parser<State> parser = new Parser<>(new State1());
        Program result = parser.parse(new LinkedList<>(asList(
                new TokenLPar(LPar.SYMBOL),
                new TokenIdentifier(new Identifier()),
                new TokenRPar(RPar.SYMBOL),
                new TokenDot(Dot.SYMBOL),
                new TokenEnd(End.SYMBOL)
        ))).result();
        System.out.println(result);
    }

}
