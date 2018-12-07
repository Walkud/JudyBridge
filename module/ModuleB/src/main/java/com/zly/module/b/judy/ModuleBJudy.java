package com.zly.module.b.judy;

import android.app.Activity;
import android.content.Intent;

import com.zly.judy.api.KeepSource;
import com.zly.judy.api.annontations.JudyBridge;
import com.zly.judy.lib.common.JudyHelper;
import com.zly.judy.lib.common.JudyTest;
import com.zly.module.b.CallAppActivity;

/**
 * 模块B对外暴露的服务
 * Created by Zhuliya on 2018/9/17
 */
@JudyBridge
public class ModuleBJudy implements KeepSource {

    /**
     * 进入CallAppActivity
     *
     * @param activity
     */
    public void forwordCallAppActivity(Activity activity) {

        if (JudyTest.isLogin()) {
            Intent intent = new Intent(activity, CallAppActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //进入个人中心
            activity.startActivity(intent);
        } else {
            //未登录，先进入登录页面
            JudyHelper.getLoginJudyBridge().forwrodLogin(activity, CallAppActivity.class.getName());
        }
    }
}
