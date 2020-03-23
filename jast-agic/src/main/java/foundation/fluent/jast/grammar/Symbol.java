package foundation.fluent.jast.grammar;

public interface Symbol {
    Symbol ε = new Symbol() {
        @Override public String toString() { return "ε"; }
    };
    Symbol any = new Symbol() {
        @Override public String toString() { return ""; }
    };
}
