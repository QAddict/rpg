# RPG - Java Rapid Parser Generator
[![Released version](https://img.shields.io/maven-central/v/foundation.fluent.api/rpg-project.svg)](https://search.maven.org/#search%7Cga%7C1%7Crpg-project)
[![Build Status](https://travis-ci.org/c0stra/rpg.svg?branch=master)](https://travis-ci.org/c0stra/rpg)

__!!!Under development !!!__ - Parser generator works, Lexer generator missing.

Java Abstract Syntax Tree driven LR0 / LR1 rapid parser generator, generating parser based on Java AST factory methods, and
returning user defined root node of the tree.

## Table of content
* [Maven configuration](#maven-configuration)
    * [Code dependencies](#code-dependencies)
    * [Annotation processing dependencies](#annotation-processing-dependencies)
* [Grammar definition using Java code - re-use AST factory](#rpg---java-rapid-parser-generator)
* [Solving conflicts using priorities](#solving-conflicts-using-priorities)
* [Lexer definition](#lexer-definition)
* [Generated Parser - Typesafe heterogeneous active stack](#generated-parser---typesafe-heterogeneous-active-stack)
* [Meta rules](#meta-rules)
* [Decomposition of grammar definition](#decomposition-of-grammar-definition)
* ["No coding" parser](#no-coding-parser)

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

### "No coding parser"
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

