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

package foundation.rpg.lexer;

import foundation.rpg.automata.LrItem;
import foundation.rpg.automata.LrItemSet;
import foundation.rpg.automata.LrParserAutomata;
import foundation.rpg.automata.LrParserConstructor;
import foundation.rpg.grammar.Grammar;
import foundation.rpg.grammar.Symbol;
import foundation.rpg.util.MapOfSets;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class LexerConstructor extends LrParserConstructor {
    public LexerConstructor(Grammar grammar) {
        super(grammar);
    }

    @Override
    public MapOfSets<Symbol, LrItem> transitions(LrParserAutomata parser, LrItemSet set) {
        MapOfSets<Symbol, LrItem> transitions = super.transitions(parser, set);
        Set<Symbol> keys = transitions.keys();
        Set<Symbol> chars = keys.stream().filter(k -> !isGroup(k)).collect(toSet());
        keys.stream().filter(this::isGroup).forEach(g -> chars.stream().filter(c -> isInGroup(g, c)).forEach(c -> transitions.get(c).addAll(transitions.get(g))));
        return transitions;
    }

    private boolean isInGroup(Symbol g, Symbol c) {
        return true;
    }

    private boolean isGroup(Symbol k) {
        return k.toString().startsWith("\\");
    }

    public static LrParserAutomata generateParser(Grammar grammar) {
        return new LexerConstructor(grammar).constructAutomata();
    }

}
