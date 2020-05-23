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

import java.util.Iterator;
import java.util.stream.Stream;

public class Thompson {

    public static final Object epsilon = null;

    public GNFA empty() {
        return transition(epsilon);
    }

    public GNFA transition(Object input) {
        State start = new State();
        State end = new State();
        start.add(input, end);
        return new GNFA(start, end);
    }

    public GNFA transitions(Stream<Object> inputs) {
        State start = new State();
        State end = new State();
        inputs.forEach(input -> start.add(input, end));
        return new GNFA(start, end);
    }

    public GNFA chain(Stream<GNFA> gnfas) {
        Iterator<GNFA> i = gnfas.iterator();
        if(!i.hasNext()) return transition(epsilon);
        GNFA current = i.next();
        State start = current.getStart();
        while(i.hasNext()) {
            GNFA next = i.next();
            current.getEnd().add(epsilon, next.getStart());
            current = next;
        }
        return new GNFA(start, current.getEnd());
    }

    public GNFA alternation(Stream<GNFA> gnfas) {
        State start = new State();
        State end = new State();
        gnfas.forEach(gnfa -> {
            start.add(epsilon, gnfa.getStart());
            gnfa.getEnd().add(epsilon, end);
        });
        return new GNFA(start, end);
    }

    public GNFA repetition(GNFA gnfa) {
        State start = new State();
        State end = new State();
        start.add(epsilon, end);
        gnfa.getEnd().add(epsilon, gnfa.getStart());
        start.add(epsilon, gnfa.getStart());
        gnfa.getEnd().add(epsilon, end);
        return new GNFA(start, end);

    }

}
