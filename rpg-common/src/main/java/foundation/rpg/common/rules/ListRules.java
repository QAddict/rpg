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

package foundation.rpg.common.rules;

import foundation.rpg.common.symbols.Comma;

import java.util.List;

import static foundation.rpg.common.AstUtils.addTo;
import static foundation.rpg.common.AstUtils.list;
import static java.util.Collections.emptyList;

public interface ListRules {

    @List1 static <T> List<T> isList1 ()                       { return list(); }
    @List1 static <T> List<T> isList1 (@List1 List<T> l, T t)  { return addTo(l, t); }

    @List2 static <T> List<T> isList2(T p)                     { return list(p); }
    @List2 static <T> List<T> isList2(@List2 List<T> l, Comma c, T p) { return addTo(l, p); }

    @List3 static <T> List<T> isList3()                        { return emptyList(); }
    @List3 static <T> List<T> isList3(@List2 List<T> l)        { return l; }

    @List4 static <T> List<T> isList4 (T t)                    { return list(t); }
    @List4 static <T> List<T> isList4 (@List4 List<T> l, T t)  { return addTo(l, t); }

    @Opt static <T> T notPresent()                             { return null; }

    @CommaSeparated static <T> List<T> commaSeparated()                                  { return emptyList(); }
    @CommaSeparated static <T> List<T> commaSeparated(@CommaSeparatedNonEmpty List<T> l) { return l; }
    @CommaSeparatedNonEmpty static <T> List<T> commaSeparated(T p)                       { return list(p); }
    @CommaSeparatedNonEmpty static <T> List<T> commaSeparated(@CommaSeparatedNonEmpty List<T> l, Comma c, T p)   { return addTo(l, p); }

}
