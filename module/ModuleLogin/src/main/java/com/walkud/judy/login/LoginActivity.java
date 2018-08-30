package com.walkud.judy.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.walkud.judy.lib.base.BaseActivity;
import com.walkud.judy.lib.bean.User;

/**
 * 登录
 * Created by Zhuliya on 2018/8/22
 */
public class LoginActivity extends BaseActivity {

    private EditText userNameEt;
    private EditText passwordEt;
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bindView();
        addListener();
    }

    /**
     * 绑定View
     */
    private void bindView() {
        userNameEt = findViewById(R.id.user_name_et);
        passwordEt = findViewById(R.id.password_et);
        button = findViewById(R.id.login_btn);
    }

    /**
     * 添加事件
     */
    private void addListener() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    /**
     * 登录
     */
    private void login() {
        String userName = userNameEt.getText().toString();
        String password = passwordEt.getText().toString();

        User user = new User(userName, "JudyBridge");
        JudyTest.setUser(user);

        showToast("登录成功：" + userName + "," + password);
        finish();
    }
}
