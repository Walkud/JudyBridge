package com.zly.module.a;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zly.judy.lib.base.BaseActivity;
import com.zly.judy.lib.bean.User;
import com.zly.judy.lib.common.JudyHelper;

/**
 * 个人中心
 * Created by Zhuliya on 2018/8/23
 */
public class UserCenterActivity extends BaseActivity {

    private TextView userNameTv;
    private TextView nickTv;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);

        bindView();
        init();
        addListener();
    }

    /**
     * 绑定View
     */
    private void bindView() {
        userNameTv = findViewById(R.id.user_name_tv);
        nickTv = findViewById(R.id.nick_tv);
        button = findViewById(R.id.logou_btn);
    }

    /**
     * 初始化
     */
    private void init() {
        User user = JudyHelper.getLoginJudyBridge().getUser();

        userNameTv.setText(user.getName());
        nickTv.setText(user.getNick());
    }

    /**
     * 添加事件
     */
    private void addListener() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    /**
     * 注销
     */
    private void logout() {
        JudyHelper.getLoginJudyBridge().logout();
        showToast("注销成功");
        finish();
    }
}
