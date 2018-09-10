package com.zly.judy.lib.common;

import com.zly.judy.api.Judy;
import com.zly.judy.lib.judy.LoginJudyBridge;
import com.zly.judy.lib.judy.ModuleAJudyBridge;

/**
 * 自定义JudyBridge统一封装
 * Created by Zhuliya on 2018/8/30
 */
public class JudyHelper {

    /**
     * 获取登录组件 JudyBridge
     *
     * @return
     */
    public static LoginJudyBridge getLoginJudyBridge() {
        return Judy.getBridge(LoginJudyBridge.class);
    }

    /**
     * 获取模块A组件 JudyBridge
     *
     * @return
     */
    public static ModuleAJudyBridge getModuleAJudyBridge() {
        return Judy.getBridge(ModuleAJudyBridge.class);
    }

}
