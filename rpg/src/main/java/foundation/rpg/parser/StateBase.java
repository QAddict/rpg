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

package foundation.rpg.parser;

import foundation.rpg.Name;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

public class StateBase<R> {

    public boolean accepted() {
        return false;
    }

    public <T> T error(Object symbol) throws UnexpectedInputException {
        throw new UnexpectedInputException(getClass().getSimpleName(), stack(), symbol, Stream.of(getClass().getDeclaredMethods()).filter(m -> m.getName().startsWith("visit") && m.getParameterCount() == 1).map(m -> expected(m.getParameterTypes()[0], m.getParameterAnnotations()[0])).collect(toList()));
    }

    public R result() {
        throw new IllegalStateException("End not reached.");
    }

    public List<Object> stack() {
        return Collections.emptyList();
    }

    private static String expected(Class<?> of, Annotation[] annotations) {
        return Stream.of(annotations).filter(a -> a.annotationType().equals(Named.class)).map(a -> (Named) a).map(Named::value).findFirst().orElseGet(() -> {
            Name name = of.getAnnotation(Name.class);
            return isNull(name) ? "<" + of.getSimpleName() + ">" : name.value();
        });
    }

}
