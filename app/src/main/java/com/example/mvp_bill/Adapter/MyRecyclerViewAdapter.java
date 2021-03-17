package com.example.mvp_bill.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mvp_bill.R;

import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private Map<String, String> map;
    private List<String> titles;
    private ItemClickListener itemClickListener;

    public MyRecyclerViewAdapter(Map<String, String> map, List<String> titles) {
        this.map = map;
        this.titles = titles;
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        if(this.itemClickListener == null) {
            this.itemClickListener = itemClickListener;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewAdapter.ViewHolder holder, int position) {
        if (itemClickListener != null) {
            holder.linearLayout.setOnClickListener(view -> itemClickListener.onItemClick(view, holder));
        }
        holder.text.setText("总花费：" + map.get(titles.get(position)) + "元");
        holder.iv_text.setText(titles.get(position));
        switch (titles.get(position)) {
            case "餐饮":
                holder.imageView.setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.food, null));
                break;
            case "水果":
                holder.imageView.setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.fruits, null));
                break;
            case "衣服":
                holder.imageView.setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.clothes, null));
                break;
            case "购物":
                holder.imageView.setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.shop, null));
                break;
            case "零食":
                holder.imageView.setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.snacks, null));
                break;
            case "书籍":
                holder.imageView.setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.book,null));
                break;
            case "交通":
                holder.imageView.setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.traffic, null));
                break;
            case "运动":
                holder.imageView.setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.sports, null));
                break;
            case "蔬菜":
                holder.imageView.setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.vegetables, null));
                break;
            case "美容":
                holder.imageView.setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.beauty, null));
                break;
            case "娱乐":
                holder.imageView.setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.entertainment, null));
                break;
            case "住房":
                holder.imageView.setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.house, null));
                break;
            case "旅游":
                holder.imageView.setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.travel,null));
                break;
            case "通讯":
                holder.imageView.setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.communication, null));
                break;
            case "汽车":
                holder.imageView.setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.car, null));
                break;
            case "医疗":
                holder.imageView.setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.medicine, null));
                break;
            case "长辈":
                holder.imageView.setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.elder, null));
                break;
            case "孩子":
                holder.imageView.setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.baby, null));
                break;
            case "数码":
                holder.imageView.setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.digital, null));
                break;
            case "捐款":
                holder.imageView.setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.donate, null));
                break;
            case "礼物":
                holder.imageView.setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.gifts, null));
                break;
            case "学习":
                holder.imageView.setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.learn, null));
                break;
            case "宠物":
                holder.imageView.setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.pets, null));
                break;
            case "工资":
                holder.imageView.setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.wages, null));
                break;
            case "兼职":
                holder.imageView.setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.part_time, null));
                break;
            case "理财":
                holder.imageView.setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.manage, null));
                break;
            case "其他":
                holder.imageView.setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.others, null));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    @NonNull
    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_item, parent, false);
        return new ViewHolder(view);
    }

    public interface ItemClickListener {
        void onItemClick(View view, MyRecyclerViewAdapter.ViewHolder holder);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        public TextView iv_text;
        ImageView imageView;
        LinearLayout linearLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.day_text);
            imageView = itemView.findViewById(R.id.day_iv);
            iv_text = itemView.findViewById(R.id.day_iv_text);
            linearLayout = itemView.findViewById(R.id.item_layout);
        }
    }
}
