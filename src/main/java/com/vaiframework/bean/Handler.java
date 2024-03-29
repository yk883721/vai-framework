package com.vaiframework.bean;

import java.lang.reflect.Method;

/**
 * 封装 Action 信息
 * @author admin
 */
public class Handler {

    /**
     * Controller 类
     */
    private final Class<?> controllerClass;

    /**
     * Action 方法
     */
    private final Method actionMethod;

    public Handler(Class<?> controllerClass, Method actionMethod) {
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }
}
