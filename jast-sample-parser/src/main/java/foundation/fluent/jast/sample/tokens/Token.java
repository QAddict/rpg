package foundation.fluent.jast.sample.tokens;

import foundation.fluent.jast.common.End;
import foundation.fluent.jast.common.LPar;
import foundation.fluent.jast.common.Plus;
import foundation.fluent.jast.common.RPar;
import foundation.fluent.jast.sample.ast.Identifier;
import foundation.fluent.jast.sample.states.StateVisitor;

import java.util.function.UnaryOperator;

public interface Token extends UnaryOperator<StateVisitor> {
    Token END = current -> current.visit(End.SYMBOL);
    Token LPAR = current -> current.visit(LPar.SYMBOL);
    Token RPAR = current -> current.visit(RPar.SYMBOL);
    Token PLUS = current -> current.visit(Plus.SYMBOL);
    Token IDENT = current -> current.visit(new Identifier("name"));
    static Token ident(String name) {return current -> current.visit(new Identifier(name));}
}
