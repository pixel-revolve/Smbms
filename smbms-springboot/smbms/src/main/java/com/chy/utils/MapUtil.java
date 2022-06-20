package com.chy.utils;

import java.util.HashMap;

public class MapUtil {

    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<K, V>(100);
    }

    public static <K, V> HashMap<K, V> newHashMap(K k, V v) {
        HashMap<K, V> map = new HashMap<K, V>(100);
        map.put(k, v);
        return map;
    }

    @SuppressWarnings("unchecked")
    public static <K, V> HashMap<K, V> newHashMap(K k, V v,
                                                  Object... extraKeyValues) {
        if (extraKeyValues.length % 2 != 0) {
            throw new IllegalArgumentException();
        }
        HashMap<K, V> map = new HashMap<K, V>(100);
        map.put(k, v);
        for (int i = 0; i < extraKeyValues.length; i += 2) {
            k = (K) extraKeyValues[i];
            v = (V) extraKeyValues[i + 1];
            map.put(k, v);
        }
        return map;
    }
}
