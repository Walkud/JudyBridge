package com.zly.judy.lib.common.interceptor;

import android.util.Log;

import com.zly.judy.api.Interceptor;
import com.zly.judy.api.ServiceMethod;

/**
 * Judy调用日志
 * Created by Zhuliya on 2018/9/18
 */
public class JudyLogInterceptor implements Interceptor {

    private static final String TAG = JudyLogInterceptor.class.getSimpleName();

    @Override
    public Object intercept(Chain chain) {
        ServiceMethod<?> serviceMethod = chain.getServiceMethod();

        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("ClassName:");
        sb.append(serviceMethod.getCls().getName());
        sb.append("\n");
        sb.append("MethodName:");
        sb.append(serviceMethod.getMethod().getName());
        sb.append("\n");
        sb.append("Args:");
        Object[] args = serviceMethod.getArgs();

        if (args == null) {
            sb.append("无");
        } else {
            for (Object arg : args) {
                sb.append(arg);
            }
        }

        sb.append("\n");
        Object result = chain.proceed();

        sb.append("Result:");
        sb.append(result);
        sb.append("\n");

        Log.d(TAG, sb.toString());
        return result;
    }
}
