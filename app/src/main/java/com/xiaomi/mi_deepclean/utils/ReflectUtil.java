package com.xiaomi.mi_deepclean.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectUtil {
    /**
     * 162     * 对象方法调用
     * 163     *
     * 164     * @param target 调用目标对象
     * 165     * @param returnType 返回类型
     * 166     * @param method 方法名称
     * 167     * @param parameterTypes 方法参数类型
     * 168     * @param values 参数
     * 169     * @return 反射调用返回值
     * 170     * @throws NoSuchMethodException
     * 171     * @throws SecurityException
     * 172     * @throws IllegalAccessException
     * 173     * @throws IllegalArgumentException
     * 174     * @throws InvocationTargetException
     * 175
     */
    public static <T> T callObjectMethod(Object target, Class<T> returnType, String method, Class<?>[] parameterTypes, Object... values)
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class<? extends Object> clazz = target.getClass();
        Method declaredMethod = clazz.getDeclaredMethod(method, parameterTypes);
        declaredMethod.setAccessible(true);
        return (T) declaredMethod.invoke(target, values);
    }

    public static Object callObjectMethod(Object target, String method, Class<?>[] parameterTypes, Object... values)
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class<? extends Object> clazz = target.getClass();
        Method declaredMethod = clazz.getDeclaredMethod(method, parameterTypes);
        declaredMethod.setAccessible(true);
        return declaredMethod.invoke(target, values);
    }

    public static Object callObjectMethod(Class cls, Object target, String method, Class<?>[] parameterTypes, Object... values)
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class<? extends Object> clazz = cls;
        Method declaredMethod = clazz.getDeclaredMethod(method, parameterTypes);
        declaredMethod.setAccessible(true);
        return declaredMethod.invoke(target, values);
    }

    public static Object callObjectMethod(Object target, String method, Class<?> clazz, Class<?>[] parameterTypes, Object... values)
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method declaredMethod = clazz.getDeclaredMethod(method, parameterTypes);
        declaredMethod.setAccessible(true);
        return declaredMethod.invoke(target, values);
    }

    /**
     * 对象获取值
     *
     * @param target
     * @param field
     * @return
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static Object getObjectField(Object target, String field) throws NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException {
        Class<? extends Object> clazz = target.getClass();
        Field declaredField = clazz.getDeclaredField(field);
        declaredField.setAccessible(true);
        return declaredField.get(target);
    }

    public static <T> T getObjectField(Object target, String field, Class<T> returnType) throws NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException {
        Class<? extends Object> clazz = target.getClass();
        Field declaredField = clazz.getDeclaredField(field);
        declaredField.setAccessible(true);
        return (T) declaredField.get(target);
    }

}
