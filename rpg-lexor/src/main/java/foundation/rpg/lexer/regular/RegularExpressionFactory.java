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

public interface RegularExpressionFactory {

    @StartSymbol
    static Pattern is () { return null; }
    static Pattern is (LPar l, Pattern p, RPar r) { return p; }
    static Pattern is (Pattern p, Times t) { return p; }
    static Pattern is (Pattern l, Pipe p, Pattern r) { return r; }
    static Pattern is (Atom l, Pattern r) { return r; }
    static Atom    is (Atom a, Times t) { return new Repetition(a); }
    static Atom    is (Character c) { return new Char(c); }
    static Atom    is (Bs b, Character g) {return new Group(g); }
    static Atom    is (Dot d) { return new Group('.'); }
    static Atom    is (LBr l, @List1 List<Item> i, RBr r) { return new CharClass(i); }
    static Atom    is (LBr l, Tilda t, @List1 List<Item> i, RBr r) { return new Inversion(new CharClass(i)); }
    static Item    is1(Character c) { return new Char(c); }
    static Item    is (Character s, Minus m, Character e) { return new Range(s, e); }

}
