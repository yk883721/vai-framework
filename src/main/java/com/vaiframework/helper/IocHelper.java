package com.vaiframework.helper;

import com.vaiframework.annotaion.Inject;
import com.vaiframework.utils.ArrayUtil;
import com.vaiframework.utils.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入 助手类
 * @author admin
 */
public class IocHelper {

    static {

        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();

        beanMap.forEach(
                (beanClass, beanOject) -> {

                    Field[] fields = beanClass.getDeclaredFields();

                    if (ArrayUtil.isNotNullOrBlank(fields)) {
                        for (Field beanField : fields) {
                            if (beanField.isAnnotationPresent(Inject.class)) {

                                Class<?> fieldClass = beanField.getType();
                                Object beanFieldInstance = beanMap.get(fieldClass);

                                if (beanFieldInstance != null) {
                                    ReflectionUtil.setField(beanOject, beanField, beanFieldInstance);
                                }

                            }
                        }
                    }
                }
        );


    }

}
