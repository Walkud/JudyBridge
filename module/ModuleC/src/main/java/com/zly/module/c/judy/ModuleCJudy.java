package com.zly.module.c.judy;

import android.app.Activity;
import android.content.Intent;

import com.zly.judy.api.annontations.JudyBridge;
import com.zly.module.c.ModuleCActivity;

/**
 * 模块C对外暴露的服务
 * Created by Zhuliya on 2018/9/17
 */
@JudyBridge
public class ModuleCJudy {

    /**
     * 进入ModuleC主页
     *
     * @param activity
     */
    public void forwordModuleCActivity(Activity activity) {
        Intent intent = new Intent(activity, ModuleCActivity.class);
        activity.startActivity(intent);
    }
}
