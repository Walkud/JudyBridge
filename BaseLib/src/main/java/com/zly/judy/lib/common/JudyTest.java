package com.zly.judy.lib.common;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.zly.judy.lib.bean.User;
import com.zly.judy.lib.utils.SpUtil;

/**
 * 注意：这个类是为了方便，用于存放缓存数据
 * Created by Zhuliya on 2018/8/22
 */
public class JudyTest {

    public static final String KEY_USER_INFO = "userInfo";

    private static User user;

    public static User getUser() {
        if (user == null) {
            synchronized (JudyTest.class) {
                if (user == null) {
                    String userJson = SpUtil.getSp().getString(KEY_USER_INFO, null);
                    if (!TextUtils.isEmpty(userJson)) {
                        user = new Gson().fromJson(userJson, User.class);
                    }
                }
            }
        }

        return user;
    }

    public static void setUser(User user) {
        if (user == null) {
            JudyTest.user = null;
            SpUtil.getSp().edit().remove(KEY_USER_INFO).apply();
        } else {
            JudyTest.user = user;
            String userJson = new Gson().toJson(user);
            SpUtil.getSp().edit().putString(KEY_USER_INFO, userJson).apply();
        }
    }

    public static boolean isLogin() {
        return getUser() != null;
    }


}
