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

package foundation.fluent.jast.automata;

import foundation.fluent.jast.grammar.Rule;
import foundation.fluent.jast.grammar.Symbol;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

public final class LrItem implements Comparable<LrItem> {

    private final int dot;
    private final Rule rule;
    private final Set<Symbol> lookahead;

    private LrItem(int dot, Rule rule, Set<Symbol> lookahead) {
        this.dot = dot;
        this.rule = rule;
        this.lookahead = lookahead;
    }

    public static LrItem lrItem(Rule rule, Set<Symbol> lookahead) {
        return new LrItem(0, rule, lookahead);
    }

    public static LrItem lrItem(int i, Rule rule, Set<Symbol> lookahead) {
        return new LrItem(i, rule, lookahead);
    }

    public Symbol symbolAtDot() {
        return rule.getRight().get(dot);
    }

    public boolean isEnd() {
        return dot >= rule.getRight().size();
    }

    public Set<Symbol> getLookahead() {
        return lookahead;
    }

    public int getDot() {
        return dot;
    }

    public Rule getRule() {
        return rule;
    }

    public LrItem moveDot() {
        return new LrItem(dot + 1, rule, lookahead);
    }

    @Override
    public int compareTo(LrItem o) {
        return dot - o.dot;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LrItem lr1Item = (LrItem) o;
        return dot == lr1Item.dot && Objects.equals(rule, lr1Item.rule) && Objects.equals(lookahead, lr1Item.lookahead);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dot, rule, lookahead);
    }

    @Override
    public String toString() {
        StringJoiner j = new StringJoiner(" ");
        for(int i = 0; i < rule.getRight().size(); i++) {
            if(i == dot) j.add("•");
            j.add(rule.getRight().get(i).toString());
        }
        if(dot == rule.getRight().size())
            j.add("•");
        return rule.getLeft() + " -> " + j + " " + lookahead;
    }

    public LrItem mergeLookahead(LrItem t) {
        Set<Symbol> merged = new LinkedHashSet<>(t.lookahead);
        merged.addAll(lookahead);
        return new LrItem(dot, rule, merged);
    }

}
