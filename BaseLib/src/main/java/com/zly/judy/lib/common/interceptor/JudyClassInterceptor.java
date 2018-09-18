package com.zly.judy.lib.common.interceptor;

import android.util.Log;

import com.zly.judy.api.Interceptor;
import com.zly.judy.api.ServiceMethod;
import com.zly.judy.lib.common.annontations.ClassAnn;

import java.lang.annotation.Annotation;

/**
 * 拦截指定类注解，用于处理具体事件
 * Created by Zhuliya on 2018/9/18
 */
public class JudyClassInterceptor implements Interceptor {
    @Override
    public Object intercept(Chain chain) {
        ServiceMethod serviceMethod = chain.getServiceMethod();
        Class<?> cls = serviceMethod.getCls();
        Annotation annotation = cls.getAnnotation(ClassAnn.class);
        if (annotation != null) {
            //处理具体类注解拦截，打印该类下所有被调用的方法名称
            Log.d("JudyClassInterceptor", "ClassAnn注解拦截，call:" + serviceMethod.getMethod().getName());
        }

        return chain.proceed();
    }
}
