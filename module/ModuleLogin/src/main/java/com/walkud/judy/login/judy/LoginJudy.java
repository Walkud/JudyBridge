package com.walkud.judy.login.judy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.walkud.judy.api.annontations.JudyBridge;
import com.walkud.judy.lib.bean.User;
import com.walkud.judy.login.JudyTest;
import com.walkud.judy.login.LoginActivity;

/**
 * 登录模块对外暴露的Api
 * Created by Zhuliya on 2018/8/23
 */
@JudyBridge
public class LoginJudy {

    /**
     * 进入登录页面
     *
     * @param context
     */
    public void forwrodLogin(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
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
    public boolean isLogin() {
        return JudyTest.isLogin();
    }

    /**
     * 获取用户信息
     *
     * @return
     */
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
