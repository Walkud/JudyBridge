package com.zly.module.b;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.zly.judy.lib.base.BaseActivity;
import com.zly.judy.lib.common.JudyHelper;

/**
 * 模块B引用模块A中Fragment示例
 * Created by Zhuliya on 2018/9/10
 */
public class FragmentDemoActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_demo);

        //获取模块A的Fragment
        Fragment fragment = JudyHelper.getModuleAJudyBridge().getModuleAFragment();

        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.frame_layout, fragment);
        mFragmentTransaction.commit();
    }
}
