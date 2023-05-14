package org.yk.cus.util;

public class CastUtil {

    public static String castString(Object value) {
        return castString(value, "");
    }

    public static String castString(Object value, String defaultValue) {
        return value == null ? defaultValue : String.valueOf(value);
    }

    public static Double castDouble(Object value) {
        return castDouble(value, 0D);
    }

    public static Double castDouble(Object value, Double defaultValue) {
        Double doubleValue = defaultValue;

        if (value != null) {
            String strValue = castString(value, "");
            if (StringUtils.isNotNullOrBlank(strValue)) {
                try {
                    doubleValue = Double.valueOf(strValue);
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return doubleValue;
    }

    public static Long castLong(Object value) {
        return castLong(value, 0L);
    }

    public static Long castLong(Object value, Long defaultValue) {
        Long longValue = defaultValue;

        if (value != null) {
            String strValue = castString(value, "");
            if (StringUtils.isNotNullOrBlank(strValue)) {
                try {
                    longValue = Long.valueOf(strValue);
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return longValue;
    }

    public static Integer castInt(Object value) {
        return castInt(value, 0);
    }

    public static Integer castInt(Object value, Integer defaultValue) {
        Integer intValue = defaultValue;

        if (value != null) {
            String strValue = castString(value);
            if (StringUtils.isNotNullOrBlank(strValue)) {
                try {
                    intValue = Integer.valueOf(strValue);
                } catch (NumberFormatException ignored) {
                }
            }
        }

        return intValue;
    }

    public static boolean castBoolean(String value) {
        return castBoolean(value, false);
    }

    public static boolean castBoolean(Object value, boolean defaultValue) {
        boolean booleanValue = defaultValue;

        if (value != null) {
            String strValue = castString(value);
            if (StringUtils.isNotNullOrBlank(strValue)) {
                booleanValue = Boolean.parseBoolean(strValue);
            }
        }

        return booleanValue;
    }

}
