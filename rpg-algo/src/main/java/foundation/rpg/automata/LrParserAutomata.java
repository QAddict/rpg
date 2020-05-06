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

package foundation.rpg.automata;

import foundation.rpg.grammar.Grammar;
import foundation.rpg.grammar.Symbol;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

public class LrParserAutomata {

    private final LrItemSet start;
    private final Map<LrItemSet, LrItemSet> sets = new LinkedHashMap<>();
    private final Map<LrItemSet, Map<Symbol, LrAction>> actions = new LinkedHashMap<>();
    private final Grammar grammar;

    public LrParserAutomata(LrItemSet start, Grammar grammar) {
        this.start = start;
        this.grammar = grammar;
    }

    public Grammar getGrammar() {
        return grammar;
    }

    public boolean addState(LrItemSet itemSet) {
        return sets.putIfAbsent(itemSet, itemSet) == null;
    }

    public LrItemSet getStart() {
        return start;
    }

    public Set<LrItemSet> getSets() {
        return sets.keySet();
    }

    private void action(LrItemSet from, Symbol symbol, LrAction action) {
        Map<Symbol, LrAction> actionMap = actions.computeIfAbsent(from, k -> new LinkedHashMap<>());
        // Help resolve Shift / Reduce, Reduce / Reduce conflicts with rule priorities.
        if(actionMap.containsKey(symbol)) {
            LrAction currentAction = actionMap.get(symbol);
            if(action.priority() == currentAction.priority()) {
                if(action.equals(currentAction))
                    throw new IllegalStateException("Conflict at: " + from + " for symbol: " + symbol + ": " + currentAction + " / " + action + "\n\nCurrent parser state:\n" + this);
            }
            System.out.println("Resolving conflict using priority at: " + from + " for symbol: " + symbol + ": " + currentAction + " (priority=" + currentAction.priority() + ") / " + action + " (priority=" + action.priority() + ")");
            if(action.priority() > currentAction.priority()) {
                actionMap.put(symbol, action);
            }
        }
        actionMap.put(symbol, action);
    }

    public void transition(LrItemSet from, Symbol symbol, LrItemSet to) {
        action(from, symbol, new LrAction.Goto(sets.get(to)));
    }

    public void reduction(LrItemSet from, Symbol lookahead, LrItem item) {
        action(from, lookahead, new LrAction.Reduce(item));
    }

    public Map<Symbol, LrAction> actionsFor(LrItemSet set) {
        return actions.get(set);
    }

    @Override
    public String toString() {
        return getSets().stream().map(Object::toString).collect(joining("\n")) + "\n\n" +
                actions.entrySet().stream().flatMap(entry -> entry.getValue().entrySet().stream().map(transition -> entry.getKey().getName() + ": " + transition.getKey() + " -> " + transition.getValue())).collect(Collectors.joining("\n"));
    }

    public void accept(LrItemSet itemSet, LrItem lrItem) {
        action(itemSet, Symbol.any, new LrAction.Accept(lrItem));
    }
}
