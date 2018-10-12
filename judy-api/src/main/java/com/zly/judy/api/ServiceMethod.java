package com.zly.judy.api;

import com.zly.judy.api.annontations.TargetClass;

import java.lang.reflect.Method;

/**
 * 目标服务方法解析及调用
 * Created by Zhuliya on 2018/7/2
 */
public class ServiceMethod<T> {

    private Class<?> cls;//目标服务类Class
    private Method method;//目标服务类方法
    private Object[] args;//调用实参

    ServiceMethod(Method method, Object[] args) {
        this.args = args;
        //拿到中间层接口的目标服务类Class
        TargetClass targetClass = method.getDeclaringClass().getAnnotation(TargetClass.class);

        try {
            cls = Class.forName(targetClass.value());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (cls == null) {
            //目标方法不存在TargetClass注解，检查模块间依赖关系，是否正确依赖
            throw new IllegalArgumentException(method.getDeclaringClass() + " not found tragetClass , please check module dependencies . " +
                    "If you turn on confusion, you need to implement KeepSource");
        }

        try {
            //反射获取对应方法
            this.method = cls.getMethod(method.getName(), method.getParameterTypes());
            this.method.setAccessible(true);//优化反射
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        if (this.method == null) {
            //目标方法不存在，查看生成的Java文件
            throw new IllegalArgumentException("Not found tragetMethod . If you turn on confusion check that the obfuscation configuration is correct");
        }
    }

    @SuppressWarnings("unchecked")
    T invoke() {
        try {
            return (T) method.invoke(cls.newInstance(), args);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Invoke method failed!", e);
        }
    }

    /**
     * 目标服务类Class
     *
     * @return 返回目标服务类Class
     */
    public Class<?> getCls() {
        return cls;
    }

    /**
     * 获取目标服务类方法
     *
     * @return 返回目标服务类方法
     */
    public Method getMethod() {
        return method;
    }

    /**
     * 获取调用实参
     *
     * @return 返回调用实参
     */
    public Object[] getArgs() {
        return args;
    }

}
