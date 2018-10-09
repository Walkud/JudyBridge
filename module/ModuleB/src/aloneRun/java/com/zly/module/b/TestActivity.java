package com.zly.module.b;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.zly.judy.lib.base.BaseActivity;
import com.zly.judy.lib.bean.User;
import com.zly.judy.lib.common.JudyTest;
import com.zly.module.b.activity.ModuleBModelActivity;

/**
 * 独立模块测试UI,主要用于跳转需要测试的业务模块页面
 * Created by Zhuliya on 2018/9/20
 */
public class TestActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mb_activity_test);

        showUserInfo();
        findViewById(R.id.action1_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转至需要测试的独立业务
                Class cls = ModuleBModelActivity.class;
                forword(cls);
            }
        });
    }

    /**
     * 显示用户登录信息
     */
    private void showUserInfo() {
        User user = JudyTest.getUser();
        TextView textView = findViewById(R.id.user_info_tv);

        if (user != null) {
            textView.setText("用户信息：" + user.toString());
        } else {
            textView.setText("请先登录");
        }
    }
}
