package com.example.mvp_bill.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mvp_bill.R;
import com.example.mvp_bill.model.InComeBean;
import com.example.mvp_bill.model.OutComeBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder> {
    private List<?> list;
    private String selectType;
    private OnItemClickListener onItemClickListener;

    public HistoryRecyclerViewAdapter(List<?> list, String selectType) {
        this.list = list;
        this.selectType = selectType;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (onItemClickListener != null) {
            holder.linearLayout.setOnClickListener(view -> onItemClickListener.OnItemClick(holder, position));
        }
        if (selectType.equals("支出")) {
            holder.type.setText(((OutComeBean) list.get(position)).getType());
            holder.money.setText(((OutComeBean) list.get(position)).getMoney());
            holder.time.setText(((OutComeBean) list.get(position)).getUpdatedAt());
        } else if (selectType.equals("收入")) {
            holder.type.setText(((InComeBean) list.get(position)).getType());
            holder.money.setText(((InComeBean) list.get(position)).getMoney());
            holder.time.setText(((InComeBean) list.get(position)).getUpdatedAt());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(ViewHolder holder, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        private LinearLayout linearLayout;
        private TextView type;
        private TextView money;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.history_type);
            money = itemView.findViewById(R.id.history_money);
            time = itemView.findViewById(R.id.history_time);
            linearLayout = itemView.findViewById(R.id.history_ll);
        }
    }

}
