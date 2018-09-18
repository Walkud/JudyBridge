package com.zly.judy.api;

import java.util.List;

/**
 * 具体的拦截链
 * 参考okhttp 拦截器责任链设计模式
 * Created by Zhuliya on 2018/9/17
 */
public class RealChain implements Interceptor.Chain {

    private List<Interceptor> interceptors;
    private ServiceMethod serviceMethod;//当前服务类方法
    private int index;//当前拦截器下标

    private RealChain chain;//next Chain

    public RealChain(List<Interceptor> interceptors, ServiceMethod serviceMethod) {
        this(interceptors, serviceMethod, 0);
    }

    public RealChain(List<Interceptor> interceptors, ServiceMethod serviceMethod, int index) {
        this.interceptors = interceptors;
        this.serviceMethod = serviceMethod;
        this.index = index;
    }

    /**
     * 获取服务类方法信息
     *
     * @return 返回解析的服务类方法信息
     */
    @Override
    public ServiceMethod getServiceMethod() {
        return serviceMethod;
    }

    /**
     * 继续进行
     *
     * @return 返回服务类方法的返回值
     */
    @Override
    public Object proceed() {

        if (chain != null) {
            //proceed方法只能被调用一次
            throw new IllegalStateException("Interceptor intercept method " + interceptors.get(index)
                    + " only call proceed() exactly once");
        }

        chain = new RealChain(interceptors, serviceMethod, index + 1);
        return interceptors.get(index).intercept(chain);
    }
}
