package com.zly.judy.lib.common;

import com.zly.judy.api.Judy;
import com.zly.judy.lib.judy.AppJudyBridge;
import com.zly.judy.lib.judy.LoginJudyBridge;
import com.zly.judy.lib.judy.ModuleAJudyBridge;
import com.zly.judy.lib.judy.ModuleBJudyBridge;
import com.zly.judy.lib.judy.ModuleCJudyBridge;
import com.zly.judy.lib.judy.SimpleJudyBridge;

/**
 * 自定义JudyBridge统一封装
 * Created by Zhuliya on 2018/8/30
 */
public class JudyHelper {

    /**
     * 获取示例 JudyBridge
     *
     * @return
     */
    public static SimpleJudyBridge getSimpleJudyBridge() {
        return Judy.getBridge(SimpleJudyBridge.class);
    }

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

    /**
     * 获取模块B组件 JudyBridge
     *
     * @return
     */
    public static ModuleBJudyBridge getModuleBJudyBridge() {
        return Judy.getBridge(ModuleBJudyBridge.class);
    }

    /**
     * 获取模块C组件 JudyBridge
     *
     * @return
     */
    public static ModuleCJudyBridge getModuleCJudyBridge() {
        return Judy.getBridge(ModuleCJudyBridge.class);
    }

    /**
     * 获取app JudyBridge
     *
     * @return
     */
    public static AppJudyBridge getAppJudyBridge() {
        return Judy.getBridge(AppJudyBridge.class);
    }
}
