package com.zly.judy.login;

import com.zly.judy.lib.bean.User;

/**
 * 注意：这个类是为了方便，用于存放缓存数据，实际项目不应该这么写
 * Created by Zhuliya on 2018/8/22
 */
public class JudyTest {

    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        JudyTest.user = user;
    }

    public static boolean isLogin() {
        return user != null;
    }
}
