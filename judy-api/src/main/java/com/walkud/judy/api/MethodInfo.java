package com.walkud.judy.api;

import com.walkud.judy.annontations.TargetClass;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 目标方法解析信息
 * Created by Zhuliya on 2018/7/2
 */
public class MethodInfo {

    private Class<?> cls;
    private Annotation[] annotations;
    private Method method;

    MethodInfo(Method method) {
        annotations = method.getAnnotations();
        TargetClass targetClass = method.getDeclaringClass().getAnnotation(TargetClass.class);
        try {
            cls = Class.forName(targetClass.value());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (cls == null) {
            //目标方法不存在TargetClass注解，查看生成的Java文件
            throw new IllegalArgumentException("Not found tragetClass");
        }

        try {
            this.method = cls.getMethod(method.getName(), method.getParameterTypes());
            this.method.setAccessible(true);//优化反射
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        if (this.method == null) {
            //目标方法不存在，查看生成的Java文件
            throw new IllegalArgumentException("Not found tragetMethod");
        }


    }

    public Class<?> getCls() {
        return cls;
    }

    public Annotation[] getAnnotations() {
        return annotations;
    }

    public Method getMethod() {
        return method;
    }
}
