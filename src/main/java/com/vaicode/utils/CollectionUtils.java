package org.yk.cus.util;

import java.util.Collection;
import java.util.Map;

public class CollectionUtils {

    public static boolean isNullOrEmpty(Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }

    public static boolean isNotNullOrEmpty(Collection<?> coll) {
        return !isNullOrEmpty(coll);
    }

    public static boolean isNullOrEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotNullOrEmpty(Map<?, ?> map) {
        return !isNullOrEmpty(map);
    }

}
