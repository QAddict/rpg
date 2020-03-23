package foundation.fluent.jast.generator;

public interface LrAction {

    void accept(LrActionVisitor visitor);

    interface LrActionVisitor {
        void visitGoto(LrItemSet set);
        void visitReduction(LrItem item);
        void visitAccept(LrItem item);
    }

    class Goto implements LrAction {
        private final LrItemSet to;

        public Goto(LrItemSet to) {
            this.to = to;
        }

        @Override
        public String toString() {
            return "GOTO: " + to.getName();
        }

        @Override
        public void accept(LrActionVisitor visitor) {
            visitor.visitGoto(to);
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

        @Override
        public void accept(LrActionVisitor visitor) {
            visitor.visitReduction(item);
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

        @Override
        public void accept(LrActionVisitor visitor) {
            visitor.visitAccept(item);
        }
    }
}
