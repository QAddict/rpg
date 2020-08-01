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

package foundation.rpg.sample.json;

import foundation.rpg.Match;
import foundation.rpg.Name;
import foundation.rpg.StartSymbol;
import foundation.rpg.common.symbols.*;
import foundation.rpg.parser.Token;

import java.util.List;
import java.util.Map;

import static foundation.rpg.common.AstUtils.*;
import static foundation.rpg.common.Patterns.*;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

@SuppressWarnings("unused")
public class JsonFactory {

    String  matchQuotedString(@Name("string") @Match(ANY_QUOTED_STRING)  Token t)        { return t.toString().substring(1, t.length() - 1); }
    String  matchIdString    (@Name("identifier") @Match(IDENTIFIER) Token t)            { return t.toString(); }
    Integer matchInt         (@Name("integer") @Match(INTEGER)  Token t)                 { return parseInt(t.toString()); }
    Double  matchDouble      (@Name("double") @Match(DOUBLE)  Token t)                   { return parseDouble(t.toString()); }

    @StartSymbol(parserClassName = "JsonParser", lexerClassName = "JsonLexer")
    Object              is (String v)                                                    { return v; }
    Object              is (Integer v)                                                   { return v; }
    Object              is (Double v)                                                    { return v; }
    Object              is (LBr o, List<Object> l, RBr c)                                { return l; }
    Object              is (LBr o, RBr c)                                                { return emptyList(); }
    Object              is (LCurl o, Map<String, Object> m, RCurl c)                     { return m; }
    Object              is (LCurl o, RCurl c)                                            { return emptyMap(); }
    List<Object>        is (Object v)                                                    { return list(v); }
    List<Object>        is (List<Object> l, Comma c, Object v)                           { return addTo(l, v); }
    Map<String, Object> is (String k, Colon c, Object v)                                 { return map(k, v); }
    Map<String, Object> is (Map<String, Object> m, Comma s, String k, Colon c, Object v) { return putUniqueIn(m, k, v, "Duplicate key: " + k); }

    void ignore(WhiteSpace w) { }

}
