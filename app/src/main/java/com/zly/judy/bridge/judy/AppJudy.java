package com.zly.judy.bridge.judy;

import android.content.Context;
import android.widget.Toast;

import com.zly.judy.api.annontations.JudyBridge;

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

}
