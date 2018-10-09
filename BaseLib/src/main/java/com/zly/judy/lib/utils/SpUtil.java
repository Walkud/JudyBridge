package com.zly.judy.lib.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.zly.judy.lib.base.BaseApplication;

/**
 * Created by Zhuliya on 2018/9/30
 */
public class SpUtil {

    private static SharedPreferences sp;

    public static SharedPreferences getSp(Context context) {
        if (sp == null) {
            synchronized (SpUtil.class) {
                if (sp == null) {
                    sp = context.getSharedPreferences("Judy", Context.MODE_PRIVATE);
                }
            }
        }
        return sp;
    }

    public static SharedPreferences getSp() {
        return getSp(BaseApplication.app);
    }
}
