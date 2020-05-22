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
import foundation.rpg.common.rules.List4;
import foundation.rpg.common.rules.ListRules;
import foundation.rpg.common.symbols.*;
import foundation.rpg.lexer.regular.ast.*;

import java.util.List;

import static foundation.rpg.common.AstUtils.addTo;
import static foundation.rpg.common.AstUtils.list;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

public class RegularExpressionFactory implements ListRules {

    @StartSymbol
    Pattern     is  ()                                        { return new Pattern(emptyList()); }
    Pattern     is  (List<Chain> l)                           { return new Pattern(l); }
    List<Chain> is  (Chain c)                                 { return list(c); }
    List<Chain> is  (List<Chain> l, Pipe p, Chain c)          { return addTo(l, c); }
    Chain       is1 (@List4 List <Node> l)                    { return new Chain(l); }
    Node        is  (LPar l, Pattern p, RPar r)               { return p; }
    Node        is  (Node a, Star t)                          { return new Repetition(a); }
    Node        is  (Node a, Plus p)                          { return new Chain(asList(a, new Repetition(a))); }
    Node        is  (Character c)                             { return new Char(c); }
    Node        is  (Bs b, Character g)                       { return new Group(g); }
    Node        is  (Bs b, Bs g)                              { return new Char('\\'); }
    Node        is  (Bs b, Dot d)                             { return new Char('.'); }
    Node        is  (Bs b, Star s)                            { return new Char('*'); }
    Node        is  (Bs b, LBr s)                             { return new Char('['); }
    Node        is  (Dot d )                                  { return new Group('.'); }
    Node        is  (Up u)                                    { return new Char('^'); }
    Node        is  (LBr l, @List4 List<Item> i, RBr r)       { return new CharClass(i); }
    Node        is  (LBr l, Up t, @List4 List<Item> i, RBr r) { return new Inversion(new CharClass(i)); }
    Item        is1 (Character c)                             { return new Char(c); }
    Item        is1 (Dot dot)                                 { return new Char('.'); }
    Item        is1 (Star s)                                  { return new Char('*'); }
    Item        is1 (LBr s)                                   { return new Char('['); }
    Item        is1 (Pipe s)                                  { return new Char('|'); }
    Item        is1 (Bs bs, Bs b)                             { return new Char('\\'); }
    Item        is1 (Bs bs, RBr b)                            { return new Char(']'); }
    Item        is  (Character s, Minus m, Character e)       { return new Range(s, e); }

}
