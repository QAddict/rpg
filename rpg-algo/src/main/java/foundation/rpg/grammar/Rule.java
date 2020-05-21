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

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toSet;

public final class Rule {

    private final Symbol left;
    private final SymbolString right;
    private final int priority;

    public Rule(Symbol left, SymbolString right, int priority) {
        this.left = left;
        this.right = right;
        this.priority = priority;
    }

    public Rule(Symbol left, List<Symbol> right, int priority) {
        this(left, new SymbolString(right), priority);
    }

    public Rule(Symbol left, List<Symbol> right) {
        this(left, right, 0);
    }

    public Symbol getLeft() {
        return left;
    }

    public SymbolString getRight() {
        return right;
    }

    public static Rule rule(Symbol left, List<Symbol> right, int priority) {
        return new Rule(left, right, priority);
    }

    public static Rule rule(Symbol left, List<Symbol> right) {
        return new Rule(left, right, 0);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rule rule = (Rule) o;
        return Objects.equals(left, rule.left) && Objects.equals(right, rule.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    @Override
    public String toString() {
        return left + " -> " + right;
    }

    public int getPriority() {
        return priority;
    }

    public static PriorityBuilder rule(Symbol left) {
        return priority -> right -> new Rule(left, asList(right), priority);
    }

    public interface Builder {
        Rule to(Symbol... right);

        @SafeVarargs
        static <T> Set<T> of(T... items) {
            return Stream.of(items).collect(toSet());
        }
    }

    public interface PriorityBuilder extends Builder {
        Builder priority(int priority);
        @Override default Rule to(Symbol... right) {
            return priority(0).to(right);
        }
    }
}
