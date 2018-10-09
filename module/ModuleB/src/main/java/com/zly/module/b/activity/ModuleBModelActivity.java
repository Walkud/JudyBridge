package com.zly.module.b.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.zly.judy.lib.base.BaseActivity;
import com.zly.judy.lib.bean.User;
import com.zly.judy.lib.common.JudyTest;
import com.zly.module.b.R;
import com.zly.module.b.model.MbModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * 模块B的独立业务模块
 * Created by Zhuliya on 2018/9/30
 */
public class ModuleBModelActivity extends BaseActivity {

    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mb_activity_model);

        textView = findViewById(R.id.data_tv);
        findViewById(R.id.query_data_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryMoney();
            }
        });
    }

    /**
     * 查询
     */
    @SuppressLint("CheckResult")
    private void queryMoney() {
        textView.setText("查询中...");
        final User user = JudyTest.getUser();
        new MbModel().queryMoneyByUserId(user.getUserId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        String text = "用户:" + user.getName() + "，账户余额： " + aLong.toString() + " 元";

                        textView.setText(text);
                    }
                });
    }
}
