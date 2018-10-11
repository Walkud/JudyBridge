package com.zly.judy.api;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;

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
     * 代理开关(主要用于业务模块独立运行开关，默认开启代理)
     */
    private boolean proxyEnable = true;

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
        return instance().createBridge(cls);
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
     * 开启独立运行模式，禁用动态代理
     * 注意：该方法只有当各业务模块需要独立运行时开启
     *
     * @param context
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public Context openAloneRun(Context context) {
        this.proxyEnable = false;

        //将宿主登录信息保存在独立运行业务模块环境中，需要在宿主app和独立业务模块的AndroidManifest.xml添加相同的sharedUserId配置
        try {
            //构建宿主环境

            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);

            String sharedUserId = packageInfo.sharedUserId;
            if (TextUtils.isEmpty(sharedUserId)) {
                throw new IllegalArgumentException("请在业务模块中AndroidManifest配置sharedUserId属性");
            }

            return context.createPackageContext(sharedUserId, Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("请先在手机中安装宿主apk,再运行独立业务模块");
        }
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

                        if (!proxyEnable) {
                            //关闭代理
                            return null;
                        }

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
