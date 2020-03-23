package foundation.fluent.jast.generator;

public interface LrAction {

    class Goto implements LrAction {
        private final LrItemSet to;

        public Goto(LrItemSet to) {
            this.to = to;
        }

        @Override
        public String toString() {
            return "GOTO: " + to.getName();
        }
    }

    class Reduce implements LrAction {
        private final LrItem item;

        public Reduce(LrItem item) {
            this.item = item;
        }

        @Override
        public String toString() {
            return "REDUCE: " + item;
        }
    }

    class Accept implements LrAction {
        private final LrItem item;

        public Accept(LrItem item) {
            this.item = item;
        }

        @Override
        public String toString() {
            return "ACCEPT: " + item;
        }
    }
}
