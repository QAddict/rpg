# RPG - Java Rapid Parser Generator
[![Released version](https://img.shields.io/maven-central/v/foundation.fluent.api/rpg-project.svg)](https://search.maven.org/#search%7Cga%7C1%7Crpg-project)
[![Build Status](https://travis-ci.org/c0stra/rpg.svg?branch=master)](https://travis-ci.org/c0stra/rpg)

__!!! Under development !!!__ - API of Lexer, Parser and common tools is not yet stable and may change.

Java Abstract Syntax Tree driven LR0 / LR1 rapid parser generator, generating parser based on Java AST factory methods, and
returning user defined root node of the tree.

## Table of content
* [Maven configuration](#maven-configuration)
    * [Code dependencies](#code-dependencies)
    * [Annotation processing dependencies](#annotation-processing-dependencies)
* [Grammar definition using Java code - re-use AST factory](#rpg---java-rapid-parser-generator)
* [Solving conflicts using priorities](#solving-conflicts-using-priorities)
* [Lexer definition](#lexer-definition)
    * [RPG's regular expressions](#rpgs-regular-expressions)
    * [Instructions for Lexer generator](#instructions-for-lexer-generator)
* [Putting generated parser parts together](#putting-generated-parser-parts-together)
* [Generated Parser - Typesafe heterogeneous active stack](#generated-parser---typesafe-heterogeneous-active-stack)
* [Meta rules](#meta-rules)
* [Decomposition of grammar definition](#decomposition-of-grammar-definition)
* ["No coding" parser](#no-coding-parser)
* [Resources](#resources)

Imagine your syntax tree factory class is at the same time definition of your parser grammar. That can be achieved
by small enhancement of the factory.

Node types (classes) in the abstract syntax (parse) tree in fact correspond to non-terminal symbols of a grammar.
There are also types, that represent terminal symbols of our grammar (identifiers, string literals, any other literals).
In order to define grammar, we'd need to add "unused" types describing only the syntax.

To avoid unnecessary overhead in a project, module `rpg-common` contains plenty of ready to use classes representing
terminal symbols. Common operators, keywords as well as literals.

## Maven configuration
### Code dependencies

First you need dependencies used in your code.

Following dependency contains annotations used to mark your grammar: `@StartSymbol` and `@Priority`.

Then it contains base classes, that the generated parser uses. So this is mandatory compile time dependency.
Put it simply to your dependencies.
```xml
<dependency>
    <groupId>foundation.fluent.api</groupId>
    <artifactId>rpg</artifactId>
    <version>${rpg.version}</version>
</dependency>
```

Next dependency is not mandatory, but provides plenty of ready to use tools like:
* Ready to use classes representing terminal symbols used in common languages, like operators, keywords, parentheses,
 whitespaces, etc.
* `AstUtils` class for frequently used actions in building the whole tree. Mostly shortcuts to simply create or update
 lists or maps.
* Ready to use sets of rules:
    * Typical whitespace removal rules - ignoring whitespaces and comments.
    * Meta rules for typical constructs - lists with left recursion, comma separated lists etc.

It's this dependency:
```xml
<dependency>
    <groupId>foundation.fluent.api</groupId>
    <artifactId>rpg-common</artifactId>
    <version>${rpg.version}</version>
</dependency>
```

### Annotation processing dependencies

We need a dependency for annotation processor in order to get the parser generated. Ideally pass it to compiler plugin.
But if no compiler plugin is defined, it can be picked up also from project's compile time dependencies.
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>${compiler.plugin.version}</version>
            <configuration>
                <annotationProcessorPaths>
                    <annotationProcessorPath>
                        <groupId>foundation.fluent.api</groupId>
                        <artifactId>rpg-apt</artifactId>
                        <version>${rpg.version}</version>
                    </annotationProcessorPath>
                </annotationProcessorPaths>
            </configuration>
        </plugin>
    </plugins>
</build>
```


## Grammar definition using Java code - re-use AST factory

Given the terminal classes and AST nodes declared for our language, lets define (and enrich by "syntactic" terminals)
the the factory, which in turn defines our grammar rules:

```java
package foundation.rpg.sample.language.ast;

import foundation.rpg.Priority;
import foundation.rpg.StartSymbol;

import java.util.List;

import static foundation.rpg.common.AstUtils.addTo;
import static foundation.rpg.common.AstUtils.list;

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

The goal of __RPG__ is to re-use such factory as grammar definition, and generate the LR(1) parser forsuch grammar,
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
As any parser, we also need to have complementary lexer. It should be generated too from yet another additional
information - token description (a.k.a. regular expression).

RPG lexer generator picks token description from annotations.

- Annotation `@Name(String)` is used by the lexer to be matched as-is. That allows usage of any characters, including those,
  having special meaning in RPG's regular expression.
- Annotation `@Match(String)` is used by the lexer to match token by RPG's regular expression. Keep in mind, that RPG
  doesn't implement none of widely used full standards of regular expressions. It implements just limited set of features.


### RPG's Regular expressions
RGP's regular expressions support following:

| Feature | Example | Description |
|-------------|-------|------------------|
| Alternation | <code>a&#124;b</code> | Match `a` or `b` |
| Concatenation | `ab` | Match exact sequence `ab` |
| Repetition | `a*` | Matches any number of `a` including 0 |
| Repetition | `a+` | Matches any number of `a` but at least 1 |
| Character class | `[abc]` | Matches `a` or `b` or `c` |
| Character range | `[a-c]` | Matches `a` or `b` or `c` |
| Character class inversion | `[^abc]` | Matches characters other than `a`, `b` or `c` |
| Subpattern (no capturing) | `(a)` | Matches `a`, but useful e.g. for repetition: `(ab)*` |
| Predefined character class shortcuts | | |
| Digit | `\d` | Matches any digit |
| Word | `\w` | Matches any digit or letter or `_` |
| Whitespace | `\s` | Matches any whitespace character |
| Not a digit | `\D` | Matches anything else than digit |
| Not a word | `\W` | Matches anyhing else than digit or letter or `_` |
| Not whitespace | `\S` | Matches anyhing else than whitespace character |
| Additional usefull groups| | |
| Start of identifier | `\i` | Any character or `_` or `$` |
| Start of unicode identifier | `\u` | Any unicode character or `_` or `$` |
| Unicode word | `\x` | Any unicode character, digit or `_` or `$` |

Not supported features (not listed all, but most important examples):

| Feature | Example | Description |
| --- | --- | --- |
| Optional occurence | `a?` | Match when `a` is present once, or not at all |
| Repetition with boundaries | `a{3}`, `a{3,6}` | Matches `a` min-to-max times |
| Possix groups | | |
| Equivalence classes | | |
| Back references | | |
| Anything else not mentioned in the table of supported features | | |


### Instructions for Lexer generator

1. Annotate directly the class representing terminal symbol with `@Name` or `@Match`:

```java
@Name(".") class Dot {}
@Name("+") class Plus {}
@Name("(") class LPar {}
@Name(")") class RPar {}
@Match("\\i\\w*") class Identifier {}
```

2. Annotate the symbol anywhere in the any grammar rule. Keep in mind, it can be annotated only once not to introduce conflicts

```java
Object is (@Match("\\d+") Integer i) { return i; }
```

For both cases above, the type representing the terminal symbol needs to have constructor accepting either `Token` describing
fully the code fragment identified, including file, position, length and actual content, or `String`. Generated lexer will use
such constructor.

3. Explicit instruction for lexer

If more specific creation of a token is needed, then include special methods in the "grammar" factory class, that instruct
lexer how to create the instance of terminal. Such method's return type is the type representing terminal symbol, and
exactly one parameter must be of type `Token`, annotated with the matching instruction:

```java
Integer integer (@Match("\\d+") Token t) { return Integer.parseInt(t.toString()); }
```



## Putting generated parser parts together

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


## Generated Parser - Typesafe heterogeneous active stack
The LR(1) parser, generated by __RPG__ is implemented as object oriented stack based state automata.

By object oriented state automata I mean here visitor pattern where:
Token represents (wraps) a terminal symbol red from input, which is at the same time the `node` in the visitor
pattern.
State represents state of the LR(1) parsing automata, and contains whole transition table as methods. So it is the
`visitor` in the visitor pattern.
Any non-starting state is also representing item on the stack holding typesafe symbol of the grammar (node in the AST),
and link to previous stacked state.
Every state represents exactly known prefix of the longest possible rule to reduce __including all types of symbols
within the prefix__. So it's typesafe. As reduction is done within a state visitor method, it benefits from the type
information of all elements on the stack to be used in the rule application (AST method invocation).

## Meta rules
__RPG__ supports re-usable rules using annotation marked generic methods.

Such rules are called __Meta rules__ and in fact you can think of them as of generic rules or rule templates, which
can be re-used, and applied (expanded) by refering their marker annotation.

See example:

```java
// Marker annotation
@MetaRule @interface SimpleList {}

// Re-usable meta rules
@SimpleList static <T> List<T> is () { return list(); }
@SimpleList static <T> List<T> is(List<T> l, T t) { return addTo(l, t); }

// Usage of the meta rules adds them automatically for given type.
static Program is(@SimpleList List<Statement> s, End e) { return new Program(s); }

```

## Decomposition of grammar definition
There is also support for decomposition of the rules into multiple interfaces.
They are re-used simply by extending the other interface.

`rpg-common` comes already with such re-usable sets of rules. You can re-use them by extending:

```java
public interface MyAstFactory extends WhiteSpaceRules, ListRules {

    @StartSymbol
    Program is (@List1 List<Statement> s) { return new Program(s); }
    // ...

}

```

## "No coding parser"
As __RPG__ allows to use any type, including builtin or 3rd party types for representation of the AST
nodes (and therefore symbols of the grammar), then it makes it very simple to generate data parsers.
E.g. very simple JSON parser can be constructed with following factory / grammar definition:

```java
@SuppressWarnings("unused")
public class JsonFactory {

    @StartSymbol
    Object              is (@Match("'([~'\\]|\\['\\rnt])*'|\"([~\"\\]|\\[\"\\rnt])*\"") String v) { return v; }
    Object              is (@Match("\\d*") Integer v)                                             { return v; }
    Object              is (@Match("\\d+\\[\\.eE]\\d+") Double v)                                 { return v; }
    Object              is (LBr o, List<Object> l, RBr c)                                         { return l; }
    Object              is (LBr o, RBr c)                                                         { return emptyList(); }
    Object              is (LCurl o, Map<String, Object> m, RCurl c)                              { return m; }
    Object              is (LCurl o, RCurl c)                                                     { return emptyMap(); }
    List<Object>        is (Object v)                                                             { return list(v); }
    List<Object>        is (List<Object> l, Comma c, Object v)                                    { return addTo(l, v); }
    Map<String, Object> is (String k, Colon c, Object v)                                          { return map(k, v); }
    Map<String, Object> is (Map<String, Object> m, Comma s, String k, Colon c, Object v)          { return putUniqueIn(m, k, v, "Duplicate key: " + k); }

    void ignore(@Match("\\s+") WhiteSpace w) { }

}
``` 
It demonstrates, that there's no need to define any AST nodes, as Java's `List`, `Map` and literals is sufficient
to represent simple JSON object.
The factory in turn also immediately checks correctness - not allowing duplicate entries in JSON object.

For full example see json package in rpg-sample-language module. There is also unit test for it.

## Resources

Resources used for implementation of this parser generator:

- [1] [https://en.wikipedia.org/wiki/LR_parser](https://en.wikipedia.org/wiki/LR_parser)
- [2] [https://cs.wikipedia.org/wiki/LR_syntakticky_analyzator](https://cs.wikipedia.org/wiki/LR_syntaktick%C3%BD_analyz%C3%A1tor)
- [3] [https://en.wikipedia.org/wiki/Visitor_pattern](https://en.wikipedia.org/wiki/Visitor_pattern)
- [4] [https://en.wikipedia.org/wiki/Regular_grammar](https://en.wikipedia.org/wiki/Regular_grammar)
- [5] [https://en.wikipedia.org/wiki/Thompson%27s_construction](https://en.wikipedia.org/wiki/Thompson%27s_construction)
- [6] [https://en.wikipedia.org/wiki/Powerset_construction](https://en.wikipedia.org/wiki/Powerset_construction)
- [7] [https://en.wikipedia.org/wiki/Generalized_nondeterministic_finite_automaton](https://en.wikipedia.org/wiki/Generalized_nondeterministic_finite_automaton)
- [8] [https://en.wikipedia.org/wiki/DFA_minimization](https://en.wikipedia.org/wiki/DFA_minimization)
- [9] _ČVUT, 1999_, _Češka Milan, Prof. RNDr., CSc., Ježek Karel, doc. Ing., Csc., Melichar Bořivoj, prof. Ing., Dr.Sc., Richta Karel_: Konstrukce překladačů
- [10] [_2004_, _David R. Tribble:_ Practical LR(k) Parser Construction](http://david.tribble.com/text/lrk_parsing.html)
- [11] [_2017, Tomáš Vacho_: Algorithms for transformation of regular expressions to finite automata](https://dspace.vsb.cz/bitstream/handle/10084/119008/VAC0089_FEI_N2647_2612T025_2017.pdf?sequence=1)
- [12] [http://michaluvweb.cz/2015/11/10/ka06-prevod-znka-na-dka/](http://michaluvweb.cz/2015/11/10/ka06-prevod-znka-na-dka/)
- [13] [https://medium.com/@phanindramoganti/regex-under-the-hood-implementing-a-simple-regex-compiler-in-go-ef2af5c6079](https://medium.com/@phanindramoganti/regex-under-the-hood-implementing-a-simple-regex-compiler-in-go-ef2af5c6079)
- [14] [_1996_, _Tim A. Wagner, Susan L. Graham_, General incremental lexical analysis. _ACM Transactions on Programming Languages and Systems_ (ftp://ftp.cs.berkeley.edu/pub/sggs/lexing.ps)](ftp://ftp.cs.berkeley.edu/pub/sggs/lexing.ps)
- [15] [_1997_, _Tim A. Wagner, Susan L. Graham_, Efficient and flexible incrementall parsing. _ACM Transactions on Programming Languages and Systems_ (ftp://ftp.cs.berkeley.edu/pub/sggs/toplas-parsing.ps)](ftp://ftp.cs.berkeley.edu/pub/sggs/toplas-parsing.ps)
- [16] [_1998_, _Tim A. Wagner, Susan L. Graham_, Modeling user-provided whitespace and comments in an incremental software development environment. _Software - Practice and Experience_ (ftp://ftp.cs.berkeley.edu/pub/sggs/whitespace.ps)](ftp://ftp.cs.berkeley.edu/pub/sggs/whitespace.ps)
- [17] [https://microsoft.github.io/language-server-protocol/](https://microsoft.github.io/language-server-protocol/)
