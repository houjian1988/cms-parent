package com.cms.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射工具类
 * Created by houjian on 2015/8/9.
 */
public class ReflectUtils {

    public static Object getFieldValue(Object condition, Field field) throws Exception {
        String firstLetter = field.getName().substring(0, 1).toUpperCase();
        String getMethodName = "get" + firstLetter + field.getName().substring(1);
        Method getMethod = condition.getClass().getMethod(getMethodName, new Class[]{});
        return getMethod.invoke(condition, new Object[]{});
    }

    public static Object getFieldValue(Object condition, String fieldName) throws Exception {
        String firstLetter = fieldName.substring(0, 1).toUpperCase();
        String getMethodName = "get" + firstLetter + fieldName.substring(1);
        Method getMethod = condition.getClass().getMethod(getMethodName, new Class[]{});
        return getMethod.invoke(condition, new Object[]{});
    }
}
