package com.fpoly.supperman_nh_duan2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.supperman_nh_duan2.R;
import com.fpoly.supperman_nh_duan2.lisenner.BanLisenner;
import com.fpoly.supperman_nh_duan2.model.Banan;

import java.util.List;

public class BananAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Banan> list;
    Context context;
    private BanLisenner banLisenner;

    public BananAdapter(List<Banan> list, Context context, BanLisenner banLisenner) {
        this.list = list;
        this.context = context;
        this.banLisenner = banLisenner;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ban_an,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Banan banan = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.textView.setText("Bàn : "+banan.getSoban());
        viewHolder.itemView.setOnClickListener(view -> {
            banLisenner.onBan(banan.getSoban());
        });
        if (banan.getTrangthai().equals("true")){
            viewHolder.itemView.setBackgroundResource(R.drawable.bg_dialog);
        }else {
            viewHolder.itemView.setBackgroundResource(R.drawable.bg_ban_an_do);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_ban_an);
        }
    }
}
