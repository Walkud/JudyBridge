package com.zly.module.b.judy;

import android.app.Activity;
import android.content.Intent;

import com.zly.judy.api.annontations.JudyBridge;
import com.zly.module.b.CallAppActivity;

/**
 * 模块B对外暴露的服务
 * Created by Zhuliya on 2018/9/17
 */
@JudyBridge
public class ModuleBJudy {

    /**
     * 进入CallAppActivity
     *
     * @param activity
     */
    public void forwordCallAppActivity(Activity activity) {
        activity.startActivity(new Intent(activity, CallAppActivity.class));

    }
}
