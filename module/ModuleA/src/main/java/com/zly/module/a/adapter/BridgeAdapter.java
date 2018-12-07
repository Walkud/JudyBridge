package com.zly.module.a.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zly.module.a.R;

import java.util.List;

/**
 * 测试方法缓存导致内存泄漏Bug的Adapter
 * <p>
 * Created by Zhuliya on 2018/12/7
 */
public class BridgeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<String> datas;

    public BridgeAdapter(Context context, List<String> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.ma_cell_bridge_item, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        
        Glide.with(context).load(datas.get(i)).into(myViewHolder.itemIv);
        myViewHolder.itemName.setText("测试方法缓存导致内存泄漏Bug的Adapter");
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    private static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView itemIv;
        TextView itemName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemIv = itemView.findViewById(R.id.item_iv);
            itemName = itemView.findViewById(R.id.item_name);
        }

    }


}
