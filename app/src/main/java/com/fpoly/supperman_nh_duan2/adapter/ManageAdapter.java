package com.fpoly.supperman_nh_duan2.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fpoly.supperman_nh_duan2.R;
import com.fpoly.supperman_nh_duan2.api.Server;
import com.fpoly.supperman_nh_duan2.lisenner.ManageLisenner;
import com.fpoly.supperman_nh_duan2.lisenner.OnlineLisenner;
import com.fpoly.supperman_nh_duan2.model.manage.Manage;
import com.fpoly.supperman_nh_duan2.untils.FormatUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class ManageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Manage> list;
    ManageLisenner manageLisenner;
    OnlineLisenner onlineLisenner;

    public ManageAdapter(Context context, List<Manage> list,OnlineLisenner onlineLisenner) {
        this.context = context;
        this.list = list;
        this.onlineLisenner = onlineLisenner;
    }

    public ManageAdapter(Context context, List<Manage> list, ManageLisenner manageLisenner) {
        this.context = context;
        this.list = list;
        this.manageLisenner = manageLisenner;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_manage, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Manage manage = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tvName.setMaxLines(1);
        viewHolder.tvName.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.tvName.setText(manage.getName());

        viewHolder.tvTime.setText("Thời gian: "+manage.getTime()+" - "+manage.getDate());
        viewHolder.tvPrice.setText(FormatUtils.convertEstimatedPrice(manage.getPrice())+" Đồng");
        viewHolder.tvStatus.setTextColor(0xCC00CC00);

        if (manage.getStatus().equals("online")){
            viewHolder.itemView.setOnClickListener(view -> {
                onlineLisenner.Onclick(manage);
            });
                viewHolder.tvStatus.setText("Online " +"- số người : "+manage.getSonguoi());
        }
        if (manage.getStatus().equals("offline")){
            viewHolder.itemView.setOnClickListener(view -> {
                manageLisenner.onClick(manage);
            });
            viewHolder.tvStatus.setText("Bàn : "+manage.getTables() +" - số lượng : "+manage.getSoluong());

        }
        Glide.with(context)
                .load(Server.duongdananh+manage.getImage())
                .placeholder(R.drawable.img_hinhcho)
                .error(R.drawable.img_hinhcho)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontTransform()
                .dontAnimate()
                .into(viewHolder.imgManage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName,tvPrice,tvTime,tvStatus;
        public RoundedImageView imgManage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvName = itemView.findViewById(R.id.tvNameManage);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            imgManage = itemView.findViewById(R.id.imgManage);
        }
    }
}
