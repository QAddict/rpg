package foundation.fluent.jast.parser;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class StateBase {

    public boolean accepted() {
        return false;
    }

    public <T> T error(Object symbol) {
        throw new IllegalStateException(getClass().getSimpleName() + ": Unexpected input: " + symbol + ". Expected one of: " + Stream.of(getClass().getDeclaredMethods()).filter(m -> m.getName().startsWith("visit") && m.getParameterCount() == 1).map(m -> m.getParameterTypes()[0].getSimpleName()).collect(joining(", ", "{", "}")));
    }

}
