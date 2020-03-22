package foundation.fluent.jast.util;

import java.util.*;
import java.util.function.BiConsumer;

import static java.util.Collections.emptySet;

public final class MapOfSets<K, V> {

    private final Map<K, Set<V>> map = new HashMap<>();

    public Set<V> get(K key) {
        return map.getOrDefault(key, emptySet());
    }

    public boolean add(K key, V value) {
        return map.computeIfAbsent(key, k -> new HashSet<>()).add(value);
    }

    public void forEach(BiConsumer<? super K, ? super Set<V>> consumer) {
        map.forEach(consumer);
    }
}
