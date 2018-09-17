package com.zly.module.b;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.zly.judy.lib.base.BaseActivity;
import com.zly.judy.lib.common.JudyHelper;

/**
 * 调用app提供的服务
 * Created by Zhuliya on 2018/9/17
 */
public class CallAppActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_app);
        TextView textView = findViewById(R.id.class_app_tv);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JudyHelper.getAppJudyBridge().showAppToast(CallAppActivity.this);
            }
        });
    }
}
