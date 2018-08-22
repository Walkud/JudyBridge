package com.walkud.judy.api;

import android.support.annotation.Nullable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Zhuliya on 2018/6/29
 */
public class Judy {

    private final Map<Method, ServiceMethod<?>> serviceMethodCache = new ConcurrentHashMap<>();

    private static class Hold {
        private static final Judy INSTANCE = new Judy();
    }

    public static <T> T getService(Class<T> cls) {
        return Hold.INSTANCE.create(cls);
    }

    @SuppressWarnings("unchecked")
    private <T> T create(Class<T> cls) {
        return (T) Proxy.newProxyInstance(cls.getClassLoader(), new Class<?>[]{cls},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, @Nullable Object[] args) {
                        ServiceMethod<?> serviceMethod = getServiceMethod(method);
                        return serviceMethod.invoke(args);
                    }
                });

    }

    /**
     * 获取代理的方法
     *
     * @param method
     * @return
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
