package com.zly.judy.lib;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.zly.judy.api.Judy;
import com.zly.judy.lib.base.BaseApplication;
import com.zly.judy.lib.bean.User;
import com.zly.judy.lib.common.JudyTest;
import com.zly.judy.lib.utils.SpUtil;

/**
 * Created by Zhuliya on 2018/9/21
 */
public class TestApplication extends BaseApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //关闭代理
        Judy.instance().setProxyEnable(false);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //将宿主登录信息保存在独立运行业务模块环境中，需要在宿主app和独立业务模块的AndroidManifest.xml添加相同的sharedUserId配置
        try {
            //构建宿主环境
            Context hostContext = createPackageContext("com.zly.judy.bridge", Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY);
            //拿到宿主的登录缓存信息
            String userJson = SpUtil.getSp(hostContext).getString("userInfo", null);
            if (!TextUtils.isEmpty(userJson)) {
                //将登录信息保存至当前业务模块环境中
                User user = new Gson().fromJson(userJson, User.class);
                JudyTest.setUser(user);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


    }
}
