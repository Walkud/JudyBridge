package com.zly.judy.api;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * JudyBridge 使用的主要入口类
 * 使用动态代理、反射调用最终目标实现类方法
 * Created by Zhuliya on 2018/6/29
 */
public class Judy {

    /**
     * 方法缓存，避免多次反射导致耗时
     */
    private final Map<Method, ServiceMethod<?>> serviceMethodCache = new ConcurrentHashMap<>();

    /**
     * 单例
     */
    private static class Hold {
        private static final Judy INSTANCE = new Judy();
    }

    /**
     * 获取对应的JudyBridge
     *
     * @param cls 目标Class
     * @param <T> 泛型
     * @return 返回目标对象
     */
    public static <T> T getBridge(Class<T> cls) {
        return Hold.INSTANCE.createBridge(cls);
    }

    /**
     * 创建动态代理
     *
     * @param cls 目标Class
     * @param <T> 泛型
     * @return 返回目标对象
     */
    @SuppressWarnings("unchecked")
    private <T> T createBridge(Class<T> cls) {
        return (T) Proxy.newProxyInstance(cls.getClassLoader(), new Class<?>[]{cls},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) {
                        ServiceMethod<?> serviceMethod = getServiceMethod(method);
                        return serviceMethod.invoke(args);
                    }
                });

    }

    /**
     * 获取代理的方法
     *
     * @param method 被调用的方法
     * @return 返回代理的方法
     */
    private ServiceMethod<?> getServiceMethod(Method method) {
        ServiceMethod<?> serviceMethod = serviceMethodCache.get(method);
        if (serviceMethod == null) {
            synchronized (serviceMethodCache) {
                serviceMethod = serviceMethodCache.get(method);
                if (serviceMethod == null) {
                    serviceMethod = ServiceMethod.parse(method);
                    serviceMethodCache.put(method, serviceMethod);
                }
            }
        }
        return serviceMethod;
    }
}
