package com.zly.judy.lib.common.interceptor;

import android.util.Log;

import com.zly.judy.api.Interceptor;
import com.zly.judy.api.ServiceMethod;
import com.zly.judy.lib.common.annontations.MethodAnn;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 拦截指定方法注解，用于处理具体事件
 * Created by Zhuliya on 2018/9/18
 */
public class JudyMethodInterceptor implements Interceptor {
    @Override
    public Object intercept(Chain chain) {

        Object result = chain.proceed();

        ServiceMethod serviceMethod = chain.getServiceMethod();
        Method method = serviceMethod.getMethod();
        Annotation annotation = method.getAnnotation(MethodAnn.class);
        if (annotation != null) {
            //处理具体方法注解拦截,打印指定注解返回数据
            Log.d("JudyMethodInterceptor", "MethodAnn注解拦截:" + result);
        }

        return result;
    }
}
