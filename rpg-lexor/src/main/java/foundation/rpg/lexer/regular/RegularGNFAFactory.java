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

package foundation.rpg.lexer.regular;

import foundation.rpg.StartSymbol;
import foundation.rpg.SymbolPart;
import foundation.rpg.common.symbols.*;
import foundation.rpg.gnfa.GNFA;
import foundation.rpg.gnfa.Thompson;

import java.util.stream.Stream;

import static java.util.stream.IntStream.rangeClosed;
import static java.util.stream.Stream.concat;
import static java.util.stream.Stream.of;

public class RegularGNFAFactory {
    private final Thompson thompson;

    public RegularGNFAFactory(Thompson thompson) { this.thompson = thompson; }

    @StartSymbol
    @Pattern GNFA            is  ()                                                  { return thompson.empty(); }
    @Pattern GNFA            is  (@Chain Stream<GNFA> l)                             { return thompson.alternation(l); }
    @Chain Stream<GNFA>      is  (@Chain GNFA c)                                     { return of(c); }
    @Chain Stream<GNFA>      is  (@Chain Stream<GNFA> l, Pipe p, @Chain GNFA c)      { return concat(l, of(c)); }
    @Chain GNFA              is1 (@Node Stream<GNFA> l)                              { return thompson.chain(l); }
    @Node Stream<GNFA>       is1 (@Node GNFA g)                                      { return of(g); }
    @Node Stream<GNFA>       is1 (@Node Stream<GNFA> l, @Node GNFA g)                { return concat(l, of(g)); }

    @Node GNFA               is  (LPar l, @Pattern GNFA p, RPar r)                   { return p; }
    @Node GNFA               is  (@Node GNFA a, Star t)                              { return thompson.repetition(a); }
    @Node GNFA               is  (@Node GNFA a, Plus p)                              { return thompson.chain(of(a, thompson.repetition(a))); }
    @Node GNFA               is  (Character c)                                       { return thompson.transition(c); }
    @Node GNFA               is  (Dot d)                                             { return thompson.any(); }
    @Node GNFA               is  (Bs b, Character g)                                 { return thompson.group(g); }
    @Node GNFA               is  (Bs b, Bs g)                                        { return is('\\'); }
    @Node GNFA               is  (Bs b, Dot d)                                       { return is('.'); }
    @Node GNFA               is  (Bs b, Star s)                                      { return is('*'); }
    @Node GNFA               is  (Bs b, LBr s)                                       { return is('['); }
    @Node GNFA               is  (Up u)                                              { return is('^'); }
    @Node GNFA               is  (LBr l, @Chars Stream<Character> i, RBr r)          { return thompson.transitions(i); }
    @Node GNFA               is  (LBr l, Up t, @Chars Stream<Character> i, RBr r)    { return thompson.inversions(i); }
    @Chars Stream<Character> is2 (Stream<Character> s)                               { return s; }
    @Chars Stream<Character> is  (@Chars Stream<Character> s1, Stream<Character> s2) { return concat(s1, s2); }
    Stream<Character>        is1 (Character c)                                       { return of(c); }
    Stream<Character>        is1 (Dot dot)                                           { return is1('.'); }
    Stream<Character>        is1 (Star s)                                            { return is1('*'); }
    Stream<Character>        is1 (LBr s)                                             { return is1('['); }
    Stream<Character>        is1 (Pipe s)                                            { return is1('|'); }
    Stream<Character>        is1 (Bs bs, Bs b)                                       { return is1('\\'); }
    Stream<Character>        is1 (Bs bs, RBr b)                                      { return is1(']'); }
    Stream<Character>        is  (Character s, Minus m, Character e)                 { return rangeClosed(s, e).mapToObj(i -> (char) i); }

    @SymbolPart @interface Pattern {}
    @SymbolPart @interface Chain {}
    @SymbolPart @interface Node {}
    @SymbolPart @interface Chars {}
}
