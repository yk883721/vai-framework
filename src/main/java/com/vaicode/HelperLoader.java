package com.vaicode;

import com.vaicode.annotaion.Controller;
import com.vaicode.helper.BeanHelper;
import com.vaicode.helper.ClassHelper;
import com.vaicode.helper.ControllerHelper;
import com.vaicode.helper.IocHelper;
import com.vaicode.utils.ClassUtil;

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