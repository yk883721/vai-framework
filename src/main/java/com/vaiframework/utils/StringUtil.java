package com.vaiframework.utils;

public class StringUtil {

    public static int length(CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }

    public static boolean isNullOrBlank(CharSequence cs) {
        int strLen = length(cs);
        if (strLen == 0) {
            return true;
        } else {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean isNotNullOrBlank(CharSequence cs) {
        return !isNullOrBlank(cs);
    }

    public static String[] splitString(String str, String delimiter) {
        return str.split(delimiter);
    }

}
