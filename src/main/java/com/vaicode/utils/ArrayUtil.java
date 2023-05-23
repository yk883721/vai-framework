package com.vaicode.utils;

/**
 * @author admin
 */
public class ArrayUtil {

    public static boolean isNullOrBlank(Object[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isNotNullOrBlank(Object[] array) {
        return !isNullOrBlank(array);
    }

}
