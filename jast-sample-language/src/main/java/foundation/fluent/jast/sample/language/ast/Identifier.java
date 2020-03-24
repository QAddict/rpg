package foundation.fluent.jast.sample.language.ast;

public class Identifier implements Expression {
    private final String name;

    public Identifier(String name) {
        this.name = name;
    }
}
