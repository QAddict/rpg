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

import java.util.LinkedHashSet;
import java.util.Set;

import static foundation.rpg.grammar.Symbol.ε;

public class First {

    private final MapOfSets<Symbol, Symbol> first = new MapOfSets<>();

    public First(Grammar grammar) {
        grammar.getTerminals().forEach(symbol -> first.add(symbol, symbol));
        while (addedFirstSymbol(grammar));
    }


    private boolean addedFirstSymbol(Grammar grammar) {
        for(Symbol symbol : grammar.getNonTerminals()) {
            for(Rule rule : grammar.rulesFor(symbol)) {
                if(first.add(symbol, addedEpsilon(rule)))
                    return true;
            }
        }
        return false;
    }


    private Set<Symbol> addedEpsilon(Rule rule) {
        Set<Symbol> symbols = new LinkedHashSet<>();
        for(Symbol s : rule.getRight()) {
            symbols.addAll(first.get(s));
            if(!symbols.contains(ε))
                return symbols;
            symbols.remove(ε);
        }
        symbols.add(ε);
        return symbols;
    }


    public Set<Symbol> follow(SymbolString string, Set<Symbol> lookahead) {
        Set<Symbol> follow = new LinkedHashSet<>();
        for(Symbol symbol : string) {
            follow.addAll(first.get(symbol));
            if(!follow.contains(ε)) return follow;
            follow.remove(ε);
        }
        follow.addAll(lookahead);
        return follow;
    }


}
