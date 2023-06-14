package com.vaiframework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author admin
 */
public class PropsUtil {

    private static final Logger logger = LoggerFactory.getLogger(PropsUtil.class);

    public static Properties loadProps(String filename) {
        Properties properties = null;

        try(InputStream ins = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename)) {

            if (ins == null) {
                throw new FileNotFoundException(filename + " file is not found");
            }

            properties = new Properties();
            properties.load(ins);

        }catch (Exception e){
            logger.error("load properties file failure", e);
        }

        return properties;
    }

    public static String getString(Properties props, String key) {
        return props.getProperty(key);
    }

    public static String getString(Properties props, String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }

    public static Integer getInt(Properties props, String key) {
        return getInt(props, key, null);
    }

    public static Integer getInt(Properties props, String key, Integer defaultValue) {
        Integer value = defaultValue;
        if (props.contains(key)) {
            value = CastUtil.castInt(props.getProperty(key));
        }
        return value;
    }

    public static Boolean getBoolean(Properties props, String key) {
        return getBoolean(props, key, null);
    }

    public static Boolean getBoolean(Properties props, String key, Boolean defaultValue) {
        Boolean value = defaultValue;
        if (props.contains(key)) {
            value = CastUtil.castBoolean(props.getProperty(key));
        }
        return value;
    }


}
