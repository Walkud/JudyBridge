package com.zly.judy.bridge.judy;

import android.content.Context;
import android.widget.Toast;

import com.zly.judy.api.annontations.JudyBridge;
import com.zly.judy.lib.base.BaseApplication;

/**
 * app对外暴露的服务
 * Created by Zhuliya on 2018/9/17
 */
@JudyBridge
public class AppJudy {

    /**
     * 显示AppToast
     */
    public void showAppToast(Context context) {
        Toast.makeText(context, "App Toast", Toast.LENGTH_SHORT).show();
    }

    /**
     * 加载Class
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    public Class loadClass(String name) throws ClassNotFoundException {
        return Class.forName(name);
    }

    public Object getContext(){
        return BaseApplication.app;
    }

}
