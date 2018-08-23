package com.walkud.judy.api;

import android.support.annotation.Nullable;

import java.lang.reflect.Method;

/**
 * 通过反射调用目标方法
 * Created by Zhuliya on 2018/7/2
 */
class ServiceMethodInvoke<T> extends ServiceMethod<T> {

    private MethodInfo methodInfo;

    ServiceMethodInvoke(MethodInfo methodInfo) {
        this.methodInfo = methodInfo;
    }

    /**
     * 反射调用目标类方法
     *
     * @param args
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    T invoke(@Nullable Object[] args) {
        Class<?> cls = methodInfo.getCls();
        try {
            Method method = methodInfo.getMethod();
            return (T) method.invoke(cls.newInstance(), args);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Invoke method failed!", e);
        }
    }
}
