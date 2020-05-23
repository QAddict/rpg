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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static java.util.Objects.hash;
import static java.util.stream.Collectors.joining;

public final class SymbolString implements Iterable<Symbol> {
    private final List<Symbol> symbols;
    private final int size;
    private final int hash;

    public SymbolString(List<Symbol> symbols) {
        this.symbols = unmodifiableList(new ArrayList<>(symbols));
        this.size = symbols.size();
        this.hash = hash(symbols, size);
    }

    public List<Symbol> getSymbols() {
        return symbols;
    }

    public Symbol get(int i) {
        return symbols.get(i);
    }

    public int size() {
        return size;
    }

    public Stream<Symbol> stream() {
        return symbols.stream();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SymbolString that = (SymbolString) o;
        return size == that.size &&
                Objects.equals(symbols, that.symbols);
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public String toString() {
        return symbols.stream().map(Object::toString).collect(joining(","));
    }

    @Override
    public Iterator<Symbol> iterator() {
        return symbols.iterator();
    }

    public SymbolString substring(int from) {
        return new SymbolString(from < size ? symbols.subList(from, size) : emptyList());
    }

}
