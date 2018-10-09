package com.zly.judy.lib;

import android.content.Context;

import com.zly.judy.api.Judy;
import com.zly.judy.lib.base.BaseApplication;
import com.zly.judy.lib.utils.SpUtil;

/**
 * 业务模块独立运行时，在debug AndroidManifest.xml中配置
 * 该类放在基础库的src/debug（默认）目录下，只会存在于debug构建时，不会打进正式(release)包中
 * Created by Zhuliya on 2018/9/21
 */
public class AloneRunApplication extends BaseApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //开启业务模块独立运行，获取到宿主的Context进行缓存数据初始化
        Context hostContext = Judy.instance().openAloneRun(this);
        handleData(hostContext);
    }

    /**
     * 处理数据
     * 在此方法中根据自行需求，初始化业务模块独立运行时需要的相关缓存数据
     */
    private void handleData(Context hostContext) {
        //示例
        //使用宿主Context初始化SharedPreferences缓存，当业务模块读取缓存时，实际获取的是宿主的缓存数据
        SpUtil.getSp(hostContext);
    }
}
