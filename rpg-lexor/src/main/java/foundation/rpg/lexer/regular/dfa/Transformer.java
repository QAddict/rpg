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

package foundation.rpg.lexer.regular.dfa;

import foundation.rpg.lexer.regular.ast.Atom;
import foundation.rpg.lexer.regular.Bfs;
import foundation.rpg.lexer.regular.thompson.GNFA;
import foundation.rpg.lexer.regular.thompson.State;

import java.util.*;

import static foundation.rpg.lexer.regular.thompson.ThompsonPatternVisitor.epsilon;
import static java.util.Collections.singleton;

public class Transformer {

    public DFA transform(GNFA gnfa) {
        StateSet stateSet = new StateSet();
        State start = gnfa.getStart();
        stateSet.add(start);
        e(stateSet);
        Map<StateSet, StateSet> cache = new LinkedHashMap<>();
        Bfs.bfs((set, queue) -> {
            Map<Atom, StateSet> trans = new LinkedHashMap<>();
            set.getStates().stream().flatMap(s -> s.getTransitions().stream()).filter(t -> t.getAtom() != epsilon).forEach(t -> trans.computeIfAbsent(t.getAtom(), k -> new StateSet()).add(t.getNext()));
            trans.forEach((a, s) -> {
                e(s);
                set.setTransition(a, cache.computeIfAbsent(s, k -> k));
                queue.accept(s);
            });
        }, singleton(stateSet));
        return new DFA(stateSet);
    }

    private void e(StateSet stateSet) {
        Bfs.bfs((state, queue) -> state.getTransitions().forEach(t -> {
            if(t.getAtom() == epsilon) {
                stateSet.add(t.getNext());
                queue.accept(t.getNext());
            }
        }), stateSet.getStates());
    }

}
