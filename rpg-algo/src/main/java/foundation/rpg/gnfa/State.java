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

package foundation.rpg.gnfa;

import foundation.rpg.util.MapOfSets;

import java.util.Set;

import static java.util.Collections.singleton;

public final class State {

    public static final State ERROR = new State();

    private final String name;
    private final MapOfSets<Character, State> transitions = new MapOfSets<>();
    private final MapOfSets<Character, State> groups = new MapOfSets<>();
    private final State defaultState;

    private State(String name, State defaultState) {
        this.name = name;
        this.defaultState = defaultState;
    }

    private State() {
        this.defaultState = this;
        this.name = "ERROR";
    }

    public static State state(String name) {
        return new State(name, ERROR);
    }

    public static State state(String name, State defaultState) {
        return new State(name, defaultState);
    }

    public void add(Character character, State next) {
        transitions.add(character, next);
    }

    public void addGroup(Character character, State next) {
        groups.add(character, next);
    }

    public MapOfSets<Character, State> getTransitions() {
        return transitions;
    }

    public MapOfSets<Character, State> getGroups() {
        return groups;
    }

    public State getDefaultState() {
        return defaultState;
    }

    public Set<State> get(Character character) {
        return transitions.getOrDefault(character, singleton(defaultState));
    }

    public Set<State> getGroup(Character character) {
        return groups.getOrDefault(character, singleton(defaultState));
    }

    @Override
    public String toString() {
        return super.toString() + "/" + name;
    }

}
