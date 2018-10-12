package com.zly.module.b;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.zly.judy.lib.base.BaseActivity;
import com.zly.judy.lib.common.JudyHelper;
import com.zly.module.b.activity.ModuleBModelActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 调用app提供的服务
 * Created by Zhuliya on 2018/9/17
 */
public class CallAppActivity extends BaseActivity {

    @BindView(R2.id.call_app_btn)
    Button callAppBtn;
    @BindView(R2.id.query_user_money_btn)
    Button queryUserMoneyBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_app);
        ButterKnife.bind(this);
//        Button callAppBtn = findViewById(R.id.call_app_btn);
        callAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JudyHelper.getAppJudyBridge().showAppToast(CallAppActivity.this);
            }
        });

        queryUserMoneyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forword(ModuleBModelActivity.class);
            }
        });
    }
}
