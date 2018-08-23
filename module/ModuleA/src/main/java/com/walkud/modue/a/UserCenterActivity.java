package com.walkud.modue.a;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.walkud.judy.api.Judy;
import com.walkud.judy.lib.bean.User;
import com.walkud.judy.lib.judy.LoginJudyBridge;

/**
 * 个人中心
 * Created by Zhuliya on 2018/8/23
 */
public class UserCenterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);


        TextView userNameTv = findViewById(R.id.user_name_tv);
        TextView nickTv = findViewById(R.id.nick_tv);


        User user = Judy.getBridge(LoginJudyBridge.class).getUser();

        userNameTv.setText(user.getName());
        nickTv.setText(user.getNick());
    }
}
