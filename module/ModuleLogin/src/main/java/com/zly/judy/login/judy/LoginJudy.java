package com.zly.judy.login.judy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.zly.judy.api.KeepSource;
import com.zly.judy.api.annontations.JudyBridge;
import com.zly.judy.lib.bean.User;
import com.zly.judy.lib.common.annontations.MethodAnn;
import com.zly.judy.login.JudyTest;
import com.zly.judy.login.LoginActivity;

/**
 * 登录模块对外暴露的服务
 * Created by Zhuliya on 2018/8/23
 */
@JudyBridge
public class LoginJudy implements KeepSource {

    /**
     * 初始化SDK，可在Application onCreate调用
     */
    public void initSdk() {
        Log.d("ModuleCJudy", "initSdk");
    }


    /**
     * 进入登录页面
     *
     * @param context
     */
    public void forwrodLogin(Context context) {
        forwrodLogin(context, null);
    }

    /**
     * 进入登录页面，可跳转目标页
     * 想怎么跳转，传什么参数都可以，想怎么写就怎么写
     *
     * @param context
     * @param targetClass 登录成功目标跳转页
     */
    public void forwrodLogin(Context context, String targetClass) {
        Intent intent = new Intent(context, LoginActivity.class);
        if (!TextUtils.isEmpty(targetClass)) {
            intent.putExtra(LoginActivity.EXTRA_LOGIN_TARGET_CLASS, targetClass);
        }

        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    /**
     * 是否登录
     *
     * @return
     */
    @MethodAnn
    public boolean isLogin() {
        return JudyTest.isLogin();
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @MethodAnn
    public User getUser() {
        return JudyTest.getUser();
    }

    /**
     * 注销
     */
    public void logout() {
        JudyTest.setUser(null);
    }

}
