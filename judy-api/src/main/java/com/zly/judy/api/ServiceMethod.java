package com.zly.judy.api;

import java.lang.reflect.Method;

/**
 * 目标方法解析及调用
 * Created by Zhuliya on 2018/7/2
 */
public abstract class ServiceMethod<T> {

    /**
     * 解析方法信息
     *
     * @param method 调用的方法
     * @param <T>    泛型
     * @return 返回代理的方法
     */
    static <T> ServiceMethod<T> parse(Method method) {
        MethodInfo methodInfo = new MethodInfo(method);
        return new ServiceMethodInvoke<>(methodInfo);
    }

    /**
     * 执行调用目标方法
     *
     * @param args 调用参数
     * @return 返回被调方法的返回类型
     */
    abstract T invoke(Object[] args);

}
