package com.walkud.judy.bridge.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.walkud.judy.bridge.R;

import java.util.List;

/**
 * Created by Zhuliya on 2018/8/23
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private Activity activity;
    private List<String> items;

    private ItemClick itemClick;

    public MainAdapter(Activity activity, List<String> items) {
        this.activity = activity;
        this.items = items;
    }

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = activity.getLayoutInflater().inflate(R.layout.cell_main_item, null);
        final MainViewHolder mainViewHolder = new MainViewHolder(view);
        mainViewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClick != null) {
                    itemClick.onClick(view, mainViewHolder.getAdapterPosition());
                }
            }
        });
        return mainViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder mainViewHolder, int i) {
        mainViewHolder.button.setText(items.get(i));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {

        Button button;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.item_btn);
        }
    }

    public interface ItemClick {
        void onClick(View view, int position);
    }
}
