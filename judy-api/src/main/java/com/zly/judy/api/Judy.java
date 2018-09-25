package com.zly.judy.api;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
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
     * 方法调用拦截器
     */
    private final List<Interceptor> interceptors = new ArrayList<>();

    private Judy() {
    }

    /**
     * 单例
     */
    private static class Hold {
        private static final Judy INSTANCE = new Judy();
    }

    /**
     * 获取Judy实例
     *
     * @return
     */
    public static Judy instance() {
        return Hold.INSTANCE;
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
     * 添加拦截器
     *
     * @param interceptor 拦截器
     */
    public Judy addInterceptor(Interceptor interceptor) {
        if (interceptor == null) {
            throw new IllegalArgumentException("interceptor == null");
        }
        Hold.INSTANCE.interceptors.add(interceptor);
        return this;
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
                        ServiceMethod<?> serviceMethod = getServiceMethod(method, args);
                        return execute(serviceMethod);
                    }
                });
    }

    /**
     * 获取代理的方法
     *
     * @param method 被调用的方法
     * @return 返回代理的方法
     */
    private ServiceMethod<?> getServiceMethod(Method method, Object[] args) {
        ServiceMethod<?> serviceMethod = serviceMethodCache.get(method);
        if (serviceMethod == null) {
            synchronized (serviceMethodCache) {
                serviceMethod = serviceMethodCache.get(method);
                if (serviceMethod == null) {
                    serviceMethod = new ServiceMethod(method, args);
                    serviceMethodCache.put(method, serviceMethod);
                }
            }
        }
        return serviceMethod;
    }

    /**
     * 执行调用
     */
    private Object execute(ServiceMethod<?> serviceMethod) {
        //构建拦截器集合
        List<Interceptor> interceptors = new ArrayList<>(this.interceptors);
        interceptors.add(new CallMethodInterceptor());//添加实际调用服务类方法的拦截器

        RealChain chain = new RealChain(interceptors, serviceMethod);
        return chain.proceed();//执行
    }
}
