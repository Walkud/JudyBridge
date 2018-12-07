package com.zly.module.b.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zly.judy.api.Judy;
import com.zly.judy.interlayer.ModuleAJudyBridge;
import com.zly.judy.lib.base.BaseActivity;
import com.zly.module.b.R;
import com.zly.module.b.R2;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Zhuliya on 2018/12/7
 */
public class TestBridgeAdapterActivity extends BaseActivity {

    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_bridge_adapter);
        ButterKnife.bind(this);

        List<String> data = Arrays.asList("https://sjbz-fd.zol-img.com.cn/t_s320x510c/g5/M00/05/0D/ChMkJ1hFDiSIDV9AAASfdElPbtwAAYT6gMOAQkABJ-M724.jpg",
                "https://img1.qunarzz.com/travel/d8/1612/57/419d23316df23ab5.jpg_480x360x95_8c14ebee.jpg");

        RecyclerView.Adapter adapter = Judy.getBridge(ModuleAJudyBridge.class).getBridgeAdapter(this, data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
