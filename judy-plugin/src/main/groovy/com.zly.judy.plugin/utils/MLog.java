package com.zly.judy.plugin.utils;

/**
 * 日志输入类
 * Created by Zhuliya on 2018/8/14
 */
public class MLog {

    private static boolean debug;

    public static void setLogger(boolean debug) {
        MLog.debug = debug;
    }


    /**
     * 输出Debug日志
     *
     * @param msg 日志信息
     */
    public static void d(String msg) {
        if (debug) {
            System.out.println(msg);
        }
    }

    /**
     * 输出错误日志
     *
     * @param msg 日志信息
     */
    public static void e(String msg) {
        System.err.println(msg);
    }

}
