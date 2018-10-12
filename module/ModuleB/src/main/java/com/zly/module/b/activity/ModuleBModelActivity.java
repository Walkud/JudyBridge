package com.zly.module.b.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zly.judy.lib.base.BaseActivity;
import com.zly.judy.lib.bean.User;
import com.zly.judy.lib.common.JudyTest;
import com.zly.module.b.R;
import com.zly.module.b.R2;
import com.zly.module.b.model.MbModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * 模块B的独立业务模块
 * Created by Zhuliya on 2018/9/30
 */
public class ModuleBModelActivity extends BaseActivity {

    @BindView(R2.id.query_data_btn)
    Button queryDataBtn;
    @BindView(R2.id.data_tv)
    TextView dataTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mb_activity_model);
        ButterKnife.bind(this);

        queryDataBtn.setOnClickListener(new View.OnClickListener() {
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
        dataTv.setText("查询中...");
        final User user = JudyTest.getUser();
        new MbModel().queryMoneyByUserId(user.getUserId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        String text = "用户:" + user.getName() + "，账户余额： " + aLong.toString() + " 元";

                        dataTv.setText(text);
                    }
                });
    }
}
