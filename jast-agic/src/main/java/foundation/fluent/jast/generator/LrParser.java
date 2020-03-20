package foundation.fluent.jast.generator;

import java.util.Set;

public class LrParser {

    private final LrItemSet start;
    private final Set<LrItemSet> sets;

    public LrParser(LrItemSet start, Set<LrItemSet> sets) {
        this.start = start;
        this.sets = sets;
    }

    public LrItemSet getStart() {
        return start;
    }

    public Set<LrItemSet> getSets() {
        return sets;
    }

}
