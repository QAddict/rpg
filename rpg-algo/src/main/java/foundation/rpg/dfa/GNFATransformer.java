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
import foundation.rpg.util.MapOfSets;

import java.util.*;
import java.util.function.Consumer;

import static foundation.rpg.gnfa.Thompson.epsilon;
import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;
import static java.util.stream.Collectors.*;

public class GNFATransformer {

    public GNFATransformer(Types types) {
        this.types = types;
    }

    @FunctionalInterface
    public interface Types {
        boolean isInGroup(Character group, Character input);
    }

    private final Types types;

    public DFA transform(GNFA gnfa) {
        Map<Set<State>, StateSet> cache = new LinkedHashMap<>();
        StateSet stateSet = stateSet(new LinkedHashSet<>(singleton(gnfa.getStart())), cache, s -> {});
        Bfs.withItem(stateSet, (set, queue) -> {
            Set<State> states = set.getStates();
            set.setDefaultState(stateSet(states.stream().map(State::getDefaultState).collect(toSet()), cache, queue));
            MapOfSets<Character, State> groups = new MapOfSets<>();
            MapOfSets<Character, State> trans = new MapOfSets<>();
            Set<Character> charKeys = states.stream().flatMap(s -> s.getTransitions().keys().stream().filter(k -> k != epsilon)).collect(toSet());
            Set<Character> groupKeys = states.stream().flatMap(s -> s.getGroups().keys().stream().filter(k -> k != epsilon)).collect(toSet());
            states.forEach(s -> charKeys.forEach(k -> trans.add(k, s.get(k))));
            states.forEach(s -> groupKeys.forEach(k -> groups.add(k, s.getGroup(k))));
            groups.forEach((a, s) -> {
                trans.forEach((c, as) -> {
                    if(types.isInGroup(a, c)) as.addAll(s);
                });
                set.setGroupTransition(a, stateSet(s, cache, queue));
            });
            trans.forEach((a, s) -> set.setCharTransition(a, stateSet(s, cache, queue)));
        });
        return new DFA(stateSet);
    }

    private static Set<State> epsilonClosure(Set<State> stateSet) {
        Bfs.withCollection(stateSet, (state, queue) -> state.getTransitions().forEach((k, n) -> {
            if(k == epsilon) {
                stateSet.addAll(n);
                n.forEach(queue);
            }
        }));
        return stateSet;
    }

    public StateSet stateSet(Set<State> states, Map<Set<State>, StateSet> cache, Consumer<StateSet> queue) {
        StateSet stateSet = cache.computeIfAbsent(epsilonClosure(states), StateSet::new);
        queue.accept(stateSet);
        return stateSet;
    }

}
