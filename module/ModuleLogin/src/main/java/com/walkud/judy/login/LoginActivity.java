package com.walkud.judy.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.walkud.judy.lib.bean.User;

/**
 * Created by Zhuliya on 2018/8/22
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button button = findViewById(R.id.login_btn);

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
        EditText userNameEt = findViewById(R.id.user_name_et);
        EditText passwordEt = findViewById(R.id.password_et);

        String userName = userNameEt.getText().toString();
        String password = passwordEt.getText().toString();

        User user = new User(userName, "JudyBridge");
        JudyTest.setUser(user);

        Toast.makeText(this, "登录成功：" + userName + "," + password, Toast.LENGTH_SHORT).show();
        finish();
    }
}
