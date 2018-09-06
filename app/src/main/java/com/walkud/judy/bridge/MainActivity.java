package com.walkud.judy.bridge;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.walkud.judy.bridge.adapter.MainAdapter;
import com.walkud.judy.lib.base.BaseActivity;
import com.walkud.judy.lib.common.JudyHelper;

import java.util.Arrays;
import java.util.List;

/**
 * 主页
 */
public class MainActivity extends BaseActivity {

    private List<String> list = Arrays.asList("登录", "个人中心");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        MainAdapter mainAdapter = new MainAdapter(this, list);
        recyclerView.setAdapter(mainAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        mainAdapter.setItemClick(new MainAdapter.ItemClick() {
            @Override
            public void onClick(View view, int position) {
                switch (position) {
                    case 0://登录
                        if (JudyHelper.getLoginJudyBridge().isLogin()) {
                            showToast("已登录");
                        } else {
                            JudyHelper.getLoginJudyBridge().forwrodLogin(MainActivity.this);
                        }
                        break;
                    case 1://个人中心
                        JudyHelper.getModuleAJudyBridge().forwordUserCenter(MainActivity.this);
                        break;
                }
            }
        });
    }
}
