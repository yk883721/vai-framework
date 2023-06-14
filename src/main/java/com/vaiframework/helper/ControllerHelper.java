package com.vaiframework.helper;

import com.vaiframework.annotaion.Action;
import com.vaiframework.bean.Handler;
import com.vaiframework.bean.Request;
import com.vaiframework.utils.ArrayUtil;
import com.vaiframework.utils.CollectionUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Controller 助手类
 * @author admin
 */
public class ControllerHelper {

    private static final Map<Request, Handler> ACTION_MAP = new HashMap<>();

    static {

        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (CollectionUtil.isNotNullOrEmpty(controllerClassSet)) {

            for (Class<?> controllerClass : controllerClassSet) {

                Method[] controllerMethods = controllerClass.getDeclaredMethods();
                if (ArrayUtil.isNotNullOrBlank(controllerMethods)) {

                    for (Method controllerMethod : controllerMethods) {

                        if (controllerMethod.isAnnotationPresent(Action.class)) {

                            Action action = controllerMethod.getAnnotation(Action.class);
                            String mapping = action.value();

                            if (mapping.matches("\\w+:(/\\w+)+")){

                                String[] array = mapping.split(":");
                                if (ArrayUtil.isNotNullOrBlank(array) && array.length == 2) {

                                    String requestMethod = array[0];
                                    String requestPath = array[1];

                                    Request request = new Request(requestMethod, requestPath);
                                    Handler handler = new Handler(controllerClass, controllerMethod);

                                    ACTION_MAP.put(request, handler);
                                }
                            }
                        }
                    }
                }
            }
        }


    }


    public static Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }




}
