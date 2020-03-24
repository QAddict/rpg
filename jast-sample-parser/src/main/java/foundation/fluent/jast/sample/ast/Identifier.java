package foundation.fluent.jast.sample.ast;

public class Identifier implements Expression {
    private final String name;
    private final int line;
    private final int pos;

    public Identifier(String name, int line, int pos) {
        this.name = name;
        this.line = line;
        this.pos = pos;
    }

}
