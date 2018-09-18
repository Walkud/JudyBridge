package com.zly.judy.api;

/**
 * 实际调用服务类方法的拦截器
 * Created by Zhuliya on 2018/9/18
 */
public class CallMethodInterceptor implements Interceptor {

    @Override
    public Object intercept(Chain chain) {
        //调用服务类方法
        return chain.getServiceMethod().invoke();
    }
}
