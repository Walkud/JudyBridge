package com.zly.module.b;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zly.judy.api.Judy;
import com.zly.judy.lib.base.BaseActivity;
import com.zly.judy.lib.common.JudyHelper;

/**
 * 独立模块测试UI
 * Created by Zhuliya on 2018/9/20
 */
public class TestActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mb_activity_test);

        Judy.init(getApplicationContext());


        findViewById(R.id.action1_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Log.d(Judy.TAG, "curr path:" + getPackageResourcePath());
//                ad();
//                JudyHelper.getModuleAJudyBridge().forwordUserCenter(Judy.instance().hostContext);
//                Intent intent = new Intent();
//                intent.setComponent(new ComponentName((Judy.hostContext),"com.zly.judy.bridge.MainActivity"));
//                startActivity(intent);

                Judy.instance().openIndependentMode(getApplication());


            }
        });

        findViewById(R.id.action2_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent = getPackageManager().getLaunchIntentForPackage("com.zly.judy.bridge");
//                startActivity(intent);

//                Log.d(Judy.TAG, "curr path:" + getPackageResourcePath());
//                                JudyHelper.getModuleCJudyBridge().forwordModuleCActivity(Judy.hostContext);
//                Class cls = FragmentDemoActivity.class;
//                forword(cls);

                JudyHelper.getModuleAJudyBridge().forwordUserCenter(Judy.hostContext);
//                showToast(obj);
            }
        });
    }
}
