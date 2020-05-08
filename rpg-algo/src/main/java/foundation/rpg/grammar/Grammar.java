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

package foundation.rpg.grammar;

import foundation.rpg.util.MapOfSets;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toCollection;

public class Grammar {

    private final Symbol start;
    private final Set<Symbol> symbols = new LinkedHashSet<>();
    private final Set<Symbol> terminals;
    private final Set<Symbol> nonTerminals;
    private final Set<Rule> rules;
    private final Set<Symbol> ignored;
    private final MapOfSets<Symbol, Rule> rulesBySymbol = new MapOfSets<>();

    public Grammar(Symbol start, Set<Symbol> terminals, Set<Symbol> nonTerminals, Set<Rule> rules, Set<Symbol> ignored) {
        this.start = start;
        this.terminals = terminals;
        this.nonTerminals = nonTerminals;
        this.symbols.addAll(terminals);
        this.symbols.addAll(nonTerminals);
        this.rules = rules;
        this.ignored = ignored;
        rules.forEach(rule -> rulesBySymbol.add(rule.getLeft(), rule));
    }

    public static Grammar grammar(Symbol start, Set<Rule> rules, Set<Symbol> ignored) {
        Set<Symbol> nonTerminals = rules.stream().map(Rule::getLeft).collect(toCollection(LinkedHashSet::new));
        Set<Symbol> terminals = rules.stream().flatMap(rule -> rule.getRight().stream()).filter(symbol -> !nonTerminals.contains(symbol)).collect(toCollection(LinkedHashSet::new));
        return new Grammar(start, terminals, nonTerminals, rules, ignored);
    }

    public Grammar augmented() {
        Set<Rule> augmentedRules = new LinkedHashSet<>();
        augmentedRules.add(Rule.rule(Symbol.start, asList(this.start, Symbol.end)));
        augmentedRules.addAll(rules);
        return grammar(Symbol.start, augmentedRules, ignored);
    }

    public Symbol getStart() {
        return start;
    }

    public Set<Symbol> getTerminals() {
        return terminals;
    }

    public Set<Symbol> getNonTerminals() {
        return nonTerminals;
    }

    public Set<Symbol> getSymbols() {
        return symbols;
    }

    public Set<Rule> getRules() {
        return rules;
    }

    public Set<Rule> rulesFor(Symbol symbol) {
        return rulesBySymbol.get(symbol);
    }

    @Override
    public String toString() {
        return "N = " + nonTerminals + "\nT = " + terminals + "\nS = " + start + "\nR = {\n\t" + rules.stream().map(Objects::toString).collect(joining("\n\t")) + "\n}";
    }

    public Set<Symbol> getIgnored() {
        return ignored;
    }

}
