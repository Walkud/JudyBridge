package com.walkud.judy.api;

import android.support.annotation.Nullable;

import java.lang.reflect.Method;

/**
 * 目标方法解析及调用
 * Created by Zhuliya on 2018/7/2
 */
public abstract class ServiceMethod<T> {

    /**
     * 解析方法信息
     *
     * @param method
     * @param <T>
     * @return
     */
    static <T> ServiceMethod<T> parse(Method method) {
        MethodInfo methodInfo = new MethodInfo(method);
        return new ServiceMethodInvoke<>(methodInfo);
    }

    /**
     * 执行调用目标方法
     *
     * @param args
     * @return
     */
    abstract T invoke(@Nullable Object[] args);

}
