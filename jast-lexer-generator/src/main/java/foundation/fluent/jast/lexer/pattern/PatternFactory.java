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

package foundation.fluent.jast.lexer.pattern;

import foundation.fluent.jast.StartSymbol;
import foundation.fluent.jast.common.*;

import java.util.List;

import static foundation.fluent.jast.lexer.pattern.Occurrence.*;
import static foundation.fluent.jast.parser.AstUtils.addTo;
import static foundation.fluent.jast.parser.AstUtils.list;

public interface PatternFactory {
    @StartSymbol
    static  Pattern       is  (List<Option> l, End e)                { return new Pattern(l); }
    static  List<Option>  is  (Option o)                             { return list(o); }
    static  List<Option>  is  (List<Option> l, Pipe p, Option o)     { return addTo(l, o);}
    static  Option        is  (List<Unit> l)                         { return new Option(l); }
    static  List<Unit>    is  ()                                     { return list(); }
    static  List<Unit>    is  (List<Unit> l, Unit u)                 { return addTo(l, u); }
    static  Unit          is  (Chunk c, Occurrence r)                { return new Unit(c, r); }
    static  Chunk         is  (Character c)                          { return new Char(c); }
    static  Chunk         is  (Dot d)                                { return new Any(); }
    static  Chunk         is  (Minus d)                              { return new Any(); }
    static  Chunk         is  (Tilda d)                              { return new Any(); }
    static  Chunk         is  (RPar d)                               { return new Any(); }
    static  Chunk         is  (RBr d)                                { return new Any(); }
    static  Chunk         is  (Bs b, Tilda c)                        { return is('~'); }
    static  Chunk         is  (Bs b, Times c)                        { return is('*'); }
    static  Chunk         is  (Bs b, Plus c)                         { return is('+'); }
    static  Chunk         is  (Bs b, Dot d)                          { return is('.'); }
    static  Chunk         is  (Bs b, LPar l)                         { return is('('); }
    static  Chunk         is  (Bs b, LBr l)                          { return is('('); }
    static  Chunk         is  (Bs b, Character g)                    { return new Group(g); }
    static  Chunk         is  (LBr o, List<Item> l, RBr c)           { return new Chars(l); }
    static  Chunk         is  (LBr o, Tilda t, List<Item> l, RBr c)  { return new Not(new Chars(l)); }
    static  List<Item>    is1 ()                                     { return list(); }
    static  List<Item>    is  (List<Item> l, Item i)                 { return addTo(l, i); }
    static  Item          is1 (Character c)                          { return new Char(c); }
    static  Item          is  (Character l, Minus m, Character h)    { return new Range(l, h); }
    static  Chunk         is  (LPar l, List<Option> o, RPar r)       { return new Pattern(o); }
    static  Occurrence    is2 ()                                     { return ONE; }
    static  Occurrence    is  (Times t)                              { return ANY; }
    static  Occurrence    is  (Plus p)                               { return AT_LEAST_ONE; }
}
