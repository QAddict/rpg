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

package foundation.rpg.common;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.unmodifiableList;

public class AstUtils {

    public static <T> List<T> addTo(List<T> list, T item) {
        list.add(item);
        return list;
    }

    public static <T> List<T> list() {
        return new ArrayList<>();
    }

    public static <T> List<T> list(T item) {
        return addTo(new ArrayList<>(), item);
    }

    public static <T> List<T> copy(List<T> list) {
        return unmodifiableList(new ArrayList<>(list));
    }

    public static <K, V> Map<K, V> map(K key, V value) {
        return putIn(new LinkedHashMap<>(), key, value);
    }

    public static <K, V> Map<K, V> putIn(Map<K, V> map, K key, V value) {
        map.put(key, value);
        return map;
    }

    public static String quotedBsEscapedString(String s) {
        return s.substring(1, s.length() - 2).replace("\\" + s.charAt(0), "" + s.charAt(0));
    }

    public static <K, V> Map<K, V> putUniqueIn(Map<K, V> map, K key, V value, String errorMessage) {
        if(map.containsKey(key)) {
            throw new IllegalStateException(errorMessage);
        }
        return putIn(map, key, value);
    }

}
