package com.vaiframework;

import com.vaiframework.helper.BeanHelper;
import com.vaiframework.helper.ClassHelper;
import com.vaiframework.helper.ControllerHelper;
import com.vaiframework.helper.IocHelper;
import com.vaiframework.utils.ClassUtil;

/**
 * 加载相应的 helper 类
 */
public class HelperLoader {

    public static void init() {

        Class<?>[] classList = new Class[]{

                ClassHelper.class,
                BeanHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };

        for (Class<?> clazz: classList){
            ClassUtil.loadClass(clazz.getName(), true);
        }

    }


}