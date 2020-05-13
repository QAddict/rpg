/*
 * BSD 2-Clause License
 *
 * Copyright (c) 2020, Ondrej Fischer
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package foundation.rpg.lexer.regular.thompson;

import foundation.rpg.lexer.regular.*;

public class ThompsonPatternVisitor implements PatternVisitor<GNFA> {

    public static final Atom epsilon = null;

    @Override
    public GNFA visit(Char character) {
        State start = new State();
        State end = new State();
        start.add(character, end);
        return new GNFA(start, end);
    }

    @Override
    public GNFA visit(Group group) {
        return null;
    }

    @Override
    public GNFA visit(Range range) {
        return null;
    }

    @Override
    public GNFA visit(Inversion inversion) {
        return null;
    }

    @Override
    public GNFA visit(Chain chain) {
        GNFA automata = chain.getOperands().get(0).accept(this);
        State start = automata.getStart();
        for(int i = 1; i < chain.getOperands().size(); i++) {
            GNFA n = chain.getOperands().get(i).accept(this);
            automata.getEnd().add(epsilon, n.getStart());
            automata = n;
        }
        return new GNFA(start, automata.getEnd());
    }

    @Override
    public GNFA visit(Repetition repetition) {
        State start = new State();
        State end = new State();
        start.add(epsilon, end);
        GNFA automata = repetition.getPattern().accept(this);
        automata.getEnd().add(epsilon, automata.getStart());
        start.add(epsilon, automata.getStart());
        automata.getEnd().add(epsilon, end);
        return new GNFA(start, end);
    }

    @Override
    public GNFA visit(Union union) {
        State start = new State();
        State end = new State();
        for(Pattern pattern : union.getOperands()) {
            GNFA automata = pattern.accept(this);
            start.add(epsilon, automata.getStart());
            automata.getEnd().add(epsilon, end);
        }
        return new GNFA(start, end);
    }

}
