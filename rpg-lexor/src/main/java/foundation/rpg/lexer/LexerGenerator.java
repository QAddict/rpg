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

package foundation.rpg.lexer;

import foundation.rpg.lexer.pattern.Char;
import foundation.rpg.lexer.pattern.Occurrence;
import foundation.rpg.lexer.pattern.Option;
import foundation.rpg.lexer.pattern.Unit;
import foundation.rpg.Name;
import foundation.rpg.parser.ParseErrorException;
import foundation.rpg.Pattern;

import java.io.IOException;
import java.util.*;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

public class LexerGenerator implements Comparator<Class<?>> {

    private final PatternParser parser = new PatternParser();

    private final class LexState {

    }

    public void generate(List<Class<?>> tokens) throws IOException, ParseErrorException {
        Map<foundation.rpg.lexer.pattern.Pattern, Class<?>> patterns = new LinkedHashMap<>();
        tokens.sort(this);
        for(Class<?> token : tokens)
            patterns.put(patternOf(token), token);

        patterns.forEach((p, t) -> {
            p.getOptions().forEach(o -> {
                Set<LexState> states = Collections.singleton(new LexState());
                for(Unit u : o.getUnits()) {
                }
            });
        });
    }

    private LexState drill(Set<LexState> s, Option o, int i) {
        o.getUnits().get(i).getR();
        throw new UnsupportedOperationException("drill");
    }

    private foundation.rpg.lexer.pattern.Pattern patternOf(Class<?> token) throws IOException, ParseErrorException {
        Pattern pattern = token.getAnnotation(Pattern.class);
        if(nonNull(pattern))
            return parser.parse(pattern.value());
        Name name = token.getAnnotation(Name.class);
        if(isNull(name))
            throw new IllegalArgumentException(token.getSimpleName());
        return new foundation.rpg.lexer.pattern.Pattern(Collections.singletonList(new Option(name.value().chars().mapToObj(c -> new Unit(new Char((char) c), Occurrence.ONE)).collect(toList()))));
    }

    @Override
    public int compare(Class<?> o1, Class<?> o2) {
        return 0;
    }
}
