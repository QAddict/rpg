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
import foundation.rpg.common.*;
import foundation.rpg.lexer.regular.ast.*;

import java.util.List;

import static foundation.rpg.common.AstUtils.addTo;
import static foundation.rpg.common.AstUtils.list;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

public interface RegularExpressionFactory extends ListRules {

    @StartSymbol
    static Pattern     is  ()                                         { return new Pattern(emptyList()); }
    static Pattern     is  (List<Chain> l)                            { return new Pattern(l); }
    static List<Chain> is  (Chain c)                                  { return list(c); }
    static List<Chain> is  (List<Chain> l, Pipe p, Chain c)           { return addTo(l, c); }
    static Chain       is1 (@List1 List <Node> l)                     { return new Chain(l); }
    static Node        is  (LPar l, Pattern p, RPar r)                { return p; }
    static Node        is  (Node a, Times t)                          { return new Repetition(a); }
    static Node        is  (Node a, Plus p)                           { return new Chain(asList(a, new Repetition(a))); }
    static Node        is  (Character c)                              { return new Char(c); }
    static Node        is  (Bs b, Character g)                        { return new Group(g); }
    static Node        is  (Bs b, Bs g)                               { return new Char('\\'); }
    static Node        is  (Bs b, Dot d)                              { return new Char('.'); }
    static Node        is  (Dot d )                                   { return new Group('.'); }
    static Node        is  (LBr l, @List1 List<Item> i, RBr r)        { return new CharClass(i); }
    static Node        is  (LBr l, Up t, @List1 List<Item> i, RBr r)  { return new Inversion(new CharClass(i)); }
    static Item        is1 (Character c)                              { return new Char(c); }
    static Item        is  (Bs bs)                                    { return new Char('\\'); }
    static Item        is1 (Dot dot)                                  { return new Char('.'); }
    static Item        is  (Character s, Minus m, Character e)        { return new Range(s, e); }

}
