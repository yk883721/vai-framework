package com.vaiframework.helper;

import com.vaiframework.annotaion.Controller;
import com.vaiframework.annotaion.Service;
import com.vaiframework.utils.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * 类操作助手类
 *
 * @author admin
 */
public class ClassHelper {

    private static final Logger logger = LoggerFactory.getLogger(ClassHelper.class);

    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHelper.getAppBasePackage();
        Set<Class<?>> classSet = ClassUtil.getClassSet(basePackage);

        logger.info("{}", classSet);

        CLASS_SET = classSet;
    }

    /**
     * 获取应用包下所有类
     */
    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * 获取应用包下所有 Service 类
     */
    public static Set<Class<?>> getServiceClassSet() {

        Set<Class<?>> serviceClassSet = new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(Service.class)) {
                serviceClassSet.add(clazz);
            }
        }
        return serviceClassSet;
    }

    /**
     * 获取应用包下所有 Controller 类
     */
    public static Set<Class<?>> getControllerClassSet() {

        Set<Class<?>> controllerClassSet = new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(Controller.class)) {
                controllerClassSet.add(clazz);
            }
        }
        return controllerClassSet;
    }

    /**
     * 获取应用包下所有 Bean class 类
     */
    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> classSet = new HashSet<>();
        classSet.addAll(getServiceClassSet());
        classSet.addAll(getControllerClassSet());
        return classSet;
    }


}
