package com.zly.judy.bridge;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zly.judy.bridge.adapter.MainAdapter;
import com.zly.judy.lib.base.BaseActivity;
import com.zly.judy.lib.common.JudyHelper;
import com.zly.judy.lib.common.JudyTest;
import com.zly.module.b.FragmentDemoActivity;
import com.zly.module.b.activity.TestBridgeAdapterActivity;

import java.util.Arrays;
import java.util.List;

/**
 * 主页
 */
public class MainActivity extends BaseActivity {

    private List<String> list = Arrays.asList("登录", "个人中心", "模块B使用其它模块视图", "模块B调用app服务", "模块B 获取BridgeAdapter", "进入模块C主页");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.zly.judy.bridge.R.layout.activity_main);

        RecyclerView recyclerView = findViewById(com.zly.judy.bridge.R.id.recycler_view);
        MainAdapter mainAdapter = new MainAdapter(this, list);
        recyclerView.setAdapter(mainAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));


        mainAdapter.setItemClick(new MainAdapter.ItemClick() {
            @Override
            public void onClick(View view, int position) {
                switch (position) {
                    case 0://登录
                        if (JudyTest.isLogin()) {
                            showToast("已登录");
                        } else {
                            JudyHelper.getLoginJudyBridge().forwrodLogin(MainActivity.this);
                        }
                        break;
                    case 1://个人中心
                        JudyHelper.getModuleAJudyBridge().forwordUserCenter(MainActivity.this);
                        break;
                    case 2://显示模块A Fragment
                        forword(FragmentDemoActivity.class);
                        break;
                    case 3://模块B调用app服务
                        JudyHelper.getModuleBJudyBridge().forwordCallAppActivity(MainActivity.this);
                        break;
                    case 4://模块B调用app服务
                        forword(TestBridgeAdapterActivity.class);
                        break;
                    case 5://进入模块C主页
                        JudyHelper.getModuleCJudyBridge().forwordModuleCActivity(MainActivity.this);
                        break;
                }
            }
        });
    }
}
