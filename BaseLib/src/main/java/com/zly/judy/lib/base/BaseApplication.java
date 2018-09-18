package com.zly.judy.lib.base;

import android.app.Application;

import com.zly.judy.api.Judy;
import com.zly.judy.lib.common.JudyHelper;
import com.zly.judy.lib.common.interceptor.JudyClassInterceptor;
import com.zly.judy.lib.common.interceptor.JudyLogInterceptor;
import com.zly.judy.lib.common.interceptor.JudyMethodInterceptor;

/**
 * Created by Zhuliya on 2018/9/18
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //添加拦截器
        Judy.instance()
                .addInterceptor(new JudyLogInterceptor())//添加日志拦截器
                .addInterceptor(new JudyClassInterceptor())//添加指定类注解拦截器
                .addInterceptor(new JudyMethodInterceptor());//添加指定方法注解拦截器

        //执行各模块初始化
        JudyHelper.getLoginJudyBridge().initSdk();
        JudyHelper.getModuleCJudyBridge().initSdk();
    }
}
