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

package foundation.rpg.dfa;

import foundation.rpg.gnfa.GNFA;
import foundation.rpg.gnfa.State;
import foundation.rpg.util.Bfs;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import static foundation.rpg.gnfa.Thompson.epsilon;

public class GNFATransformer {

    public GNFATransformer(Types types) {
        this.types = types;
    }

    public interface Types {
        boolean isInversion(Object input);
        boolean isGroup(Object input);
        boolean isInGroup(Object group, Object input);
    }

    private final Types types;

    public DFA transform(GNFA gnfa) {
        StateSet stateSet = new StateSet();
        State start = gnfa.getStart();
        stateSet.add(start);
        e(stateSet);
        Map<StateSet, StateSet> cache = new LinkedHashMap<>();
        Bfs.withItem(stateSet, (set, queue) -> {
            Map<Object, StateSet> trans = new LinkedHashMap<>();
            Map<Object, StateSet> groups = new LinkedHashMap<>();
            Set<Object> inv = new LinkedHashSet<>();
            StateSet defaultState = new StateSet();
            set.getStates().stream().flatMap(s -> s.getTransitions().stream()).filter(t -> t.getInput() != epsilon)
                    .forEach(t -> {
                        if(types.isInversion(t.getInput())) {
                            defaultState.add(t.getNext());
                            set.addFailOn(t.getInput());
                        } else {
                            (types.isGroup(t.getInput()) ? groups : trans).computeIfAbsent(t.getInput(), k -> new StateSet()).add(t.getNext());
                        }
                    });
            if(!defaultState.getStates().isEmpty()) {
                e(defaultState);
                queue.accept(defaultState);
                set.setDefaultState(cache.computeIfAbsent(defaultState, k -> k));
            }
            groups.forEach((a, s) -> {
                e(s);
                StateSet cachedSet = cache.computeIfAbsent(s, k -> k);
                trans.forEach((c, as) -> {
                    if(types.isInGroup(a, c)) as.addAll(s);
                });
                set.setGroupTransition(a, cachedSet);
                queue.accept(s);
            });
            trans.forEach((a, s) -> {
                e(s);
                set.setCharTransition(a, cache.computeIfAbsent(s, k -> k));
                queue.accept(s);
            });
        });
        return new DFA(stateSet);
    }

    private void e(StateSet stateSet) {
        Bfs.withCollection(stateSet.getStates(), (state, queue) -> state.getTransitions().forEach(t -> {
            if(t.getInput() == epsilon) {
                stateSet.add(t.getNext());
                queue.accept(t.getNext());
            }
        }));
    }

}