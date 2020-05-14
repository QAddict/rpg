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
import foundation.rpg.lexer.regular.ast.*;

import java.util.List;

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
        State start = new State();
        State end = new State();
        start.add(group, end);
        return new GNFA(start, end);
    }

    @Override
    public GNFA visit(Range range) {
        Pattern p = new Char(range.getStart());
        for(char i = range.getStart(); i < range.getEnd(); i++) {
            p = new Union(p, new Char((char) (i + 1)));
        }
        return p.accept(this);
    }

    @Override
    public GNFA visit(Inversion inversion) {
        State start = new State();
        State end = new State();
        start.add(inversion, end);
        return new GNFA(start, end);
    }

    @Override
    public GNFA visit(Chain chain) {
        GNFA left = chain.getLeft().accept(this);
        GNFA right = chain.getRight().accept(this);
        left.getEnd().add(epsilon, right.getStart());
        return new GNFA(left.getStart(), right.getEnd());
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
        GNFA left = union.getLeft().accept(this);
        GNFA right = union.getRight().accept(this);
        start.add(epsilon, left.getStart());
        start.add(epsilon, right.getStart());
        left.getEnd().add(epsilon, end);
        right.getEnd().add(epsilon, end);
        return new GNFA(start, end);
    }

    @Override
    public GNFA visit(Empty empty) {
        State start = new State();
        State end = new State();
        start.add(epsilon, end);
        return new GNFA(start, end);
    }

    public GNFA visit(List<Pattern> patterns) {
        State start = new State();
        State end = new State();
        for(Pattern pattern : patterns) {
            GNFA automata = pattern.accept(this);
            start.add(epsilon, automata.getStart());
            automata.getEnd().setResult(pattern).add(epsilon, end);
        }
        return new GNFA(start, end);
    }
}
