package com.zly.judy.api;

/**
 * 方法调用拦截器
 * 参考okhttp 拦截器责任链设计模式
 * Created by Zhuliya on 2018/9/17
 */
public interface Interceptor {

    /**
     * 拦截
     *
     * @param chain 链
     * @return 返回当前服务类方法的返回值
     */
    Object intercept(Chain chain);

    /**
     * 链
     */
    interface Chain {

        /**
         * 获取当前服务类方法
         *
         * @return 返回解析的服务类方法信息
         */
        ServiceMethod getServiceMethod();

        /**
         * 这里不能对解析出的方法进行修改，所有为无参，只能继续进行
         * 注意：该方法每个实例只能执行一次
         *
         * @return 返回当前服务类方法的返回值
         */
        Object proceed();
    }
}
