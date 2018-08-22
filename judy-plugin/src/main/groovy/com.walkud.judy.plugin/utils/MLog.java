package com.walkud.judy.plugin.utils;

import org.gradle.api.logging.Logger;

/**
 * 日志输入类
 * Created by Zhuliya on 2018/8/14
 */
public class MLog {

    private static Logger logger;
    private static boolean debug;

    public static void setLogger(Logger logger, boolean debug) {
        MLog.logger = logger;
        MLog.debug = debug;
    }


    /**
     * 输出Debug日志
     *
     * @param msg
     */
    public static void d(String msg) {
        if (debug && logger != null) {
            logger.debug(msg);
        }
    }

    /**
     * 输出信息日志
     *
     * @param msg
     */
    public static void i(String msg) {
        if (logger != null) {
            logger.info(msg);
        }
    }

    /**
     * 输出错误日志
     *
     * @param msg
     */
    public static void e(String msg) {
        if (logger != null) {
            logger.error(msg);
        }
    }

}
