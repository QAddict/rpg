package foundation.fluent.jast.generator;

import java.util.*;

import static java.util.stream.Collectors.joining;

public class LrItemSet {

    private final String name;
    private final Set<LrItem> closure = new HashSet<>();

    public LrItemSet(String name) {
        this.name = name;
    }

    public boolean add(LrItem item) {
        return this.closure.add(item);
    }

    public Set<LrItem> getItems() {
        return closure;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LrItemSet that = (LrItemSet) o;
        return Objects.equals(closure, that.closure);
    }

    @Override
    public int hashCode() {
        return Objects.hash(closure);
    }

    @Override
    public String toString() {
        return name + closure.stream().map(Objects::toString).collect(joining("\n\t", ": {\n\t", "\n}"));
    }

}
