# JAST-agic
Java Abstract Syntax Tree driven LR0 / LR1 parser generator, generating parser based on Java AST factory methods, and
returning user defined root node of the tree.

Imagine your syntax tree factory class is at the same time definition of your parser grammar. That can be achieved
by small enhancement of the factory.

Node types (classes) in the abstract syntax (parse) tree in fact correspond to non-terminal symbols of a grammar.
There are also types, that represent terminal symbols of our grammar (identifiers, string literals, any other literals).
In order to define grammar, we'd need to add "unused" types describing only the syntax.

To avoid unnecessary overhead in a project, module `jast-common` contains plenty of ready to use classes representing
terminal symbols. Common operators, keywords as well as literals.

## Grammar definition using Java code - re-use AST factory

Given the terminal classes and AST nodes declared for our language, lets define (and enrich by "syntactic" terminals)
the the factory, which in turn defines our grammar rules:

```java
package foundation.fluent.jast.sample.language.ast;

import foundation.fluent.jast.RulePriority;
import foundation.fluent.jast.StartSymbol;
import foundation.fluent.jast.common.*;

import java.util.List;

import static foundation.fluent.jast.parser.AstUtils.addTo;
import static foundation.fluent.jast.parser.AstUtils.list;

@RulePriority(1)
public interface AstFactory {

    @StartSymbol
    static Program         is  (List<Statement> s, End e)            { return new Program(s); }
    static List<Statement> is  ()                                    { return list(); }
    static List<Statement> is  (List<Statement> l, Statement s)      { return addTo(l, s); }
    static Statement       is  (Expression e, Dot d)                 { return new ExpressionStatement(e); }
    static Expression      is  (Expression l, Plus p, Expression r)  { return new BinaryExpression(l, r); }
    static Expression      is  (Identifier i)                        { return i; }
    static Expression      is  (LPar l, Expression e, RPar r)        { return e; }

    static void ignore(WhiteSpace w) {}
    static void ignore(Comment c) {}

}
```

The goal of __JAST-agic__ is to re-use such factory as grammar definition, and generate the LR(1) parser forsuch grammar,
which in turn invokes the factory methods during parsing.
BTW. the AST factory in fact looks very similar to other parser generators. Yet this is directly Java code.

## Solving conflicts using priorities

Note, that this grammar above is not really LR(1). It contains SHIFT / REDUCE conflict because it would natively allow
ambigous syntax trees for rule `Expression is(Expression, Plus, Expression)`.
The parser contains additional feature - priorities.

Default priorities of all actions is 0. Rule priorities can be changed either globally or per rule. Here increasing
rule (REDUCE) priority over SHIFT priority resolves the SHIFT / REDUCE conflict in favor of REDUCE. The final idea is
to solve using priorities e.g. operator precedence. E.g. given that multiplication has higher priority than addition,
then SHIFT in multiplication will be preferred over REDUCE of addition. That in turn allows more "intuitive / naive"
grammar definition. Instead of defining complex hierarchy of nodes per type of operation and operand, it allows simply:

```java
@RulePriority(1) static Expression is (Expression l, Plus p, Expression r) { ... }
@RulePriority(2) static Expression is (Expression l, Star s, Expression r) { ... }
```

The generated parser will either fail parsing with `ParseErrorException` or return the root of the parse tree,
represented by the return type of method annotated as start symbol.

One can see, that the generator can be done for any type, inclusing JDK or 3rd party types (see List<Statement>). That
should make new parser development really rapid.

## Lexer definition
As any parser, we also need to have complementary lexer. It should be generated too (TBD) from yet another additional
information - token description (a.k.a. regular expression).

Simply annotate the class representing terminal symbol with `@Name`:

```java
@Name(".") class Dot {}
@Name("+") class Plus {}
@Name("(") class LPar {}
@Name(")") class RPar {}
@Pattern("\\w\\a*") class Identifier {}
```

Resulting generated code usage example is following:

```java
// State is generated parent of all states.
// State1 is generated initial state.
// Lexer is supposed to be generated lexer for generated grammar.

Parser<State> parser = new Parser<>(new State1());
TokenInput<State> input = TokenInput.tokenInput(new Lexer(), new Input("file"));
Program program = parser.parse(input).result();
```

Now `Program` is already our totally custom program representation created using our fully controlled `AstFactory`.

The lexer generator is not yet done. Therefore no release is available yet.
Feel free to checkout and try from sources.


## Generated Parser - Typesafe heterogeneous active stack
The LR(1) parser, generated by __JAST-agic__ is implemented as object oriented stack based state automata.

By object oriented state automata I mean here visitor pattern where:
Token represents (wraps) a terminal symbol red from input, which is at the same time the `element` in the visitor
pattern.
State represents state of the LR(1) parsing automata, and contains whole transition table as methods. So it is the
`visitor` in the visitor pattern.
Any non-starting state is also representing item on the stack holding typesafe symbol of the grammar (node in the AST),
and link to previous stacked state.
Every state represents exactly known prefix of the longest possible rule to reduce __including all types of symbols
within the prefix__. So it's typesafe. As reduction is done within a state visitor method, it benefits from the type
information of all elements on the stack to be used in the rule application (AST method invocation).

Example of such parser, but implemented manually, can be found in the module `jast-sample-parser`.
