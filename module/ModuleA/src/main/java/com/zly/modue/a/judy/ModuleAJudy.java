package com.zly.modue.a.judy;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.zly.judy.api.KeepSource;
import com.zly.judy.api.annontations.JudyBridge;
import com.zly.judy.lib.common.JudyHelper;
import com.zly.modue.a.ModuleAFragment;
import com.zly.modue.a.UserCenterActivity;

/**
 * 模块A对外暴露的Api
 * Created by Zhuliya on 2018/8/23
 */
@JudyBridge
public class ModuleAJudy implements KeepSource {

    /**
     * 进入个人中心
     *
     * @param activity
     */
    public void forwordUserCenter(Activity activity) {
        if (JudyHelper.getLoginJudyBridge().isLogin()) {
            //进入个人中心
            activity.startActivity(new Intent(activity, UserCenterActivity.class));
        } else {
            //未登录，先进入登录页面
            JudyHelper.getLoginJudyBridge().forwrodLogin(activity, UserCenterActivity.class.getName());
        }
    }

    /**
     * 获取模块A Fragment
     *
     * @return
     */
    public Fragment getModuleAFragment() {
        return new ModuleAFragment();
    }
}
