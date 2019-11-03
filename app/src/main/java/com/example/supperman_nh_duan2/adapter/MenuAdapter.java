package com.example.supperman_nh_duan2.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.supperman_nh_duan2.R;
import com.example.supperman_nh_duan2.api.Server;
import com.example.supperman_nh_duan2.lisenner.DatMonLisenner;
import com.example.supperman_nh_duan2.model.Menu;
import com.example.supperman_nh_duan2.untils.FormatUtils;
import com.makeramen.roundedimageview.RoundedImageView;


import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private DatMonLisenner monLisenner;
    private Context context;
    private List<Menu> lauList;

    public MenuAdapter(DatMonLisenner monLisenner, Context context, List<Menu> lauList) {
        this.monLisenner = monLisenner;
        this.context = context;
        this.lauList = lauList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Menu menu = lauList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tvName.setMaxLines(1);
        viewHolder.tvName.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.tvName.setText(menu.getNames());
        viewHolder.tvPrice.setText(FormatUtils.convertEstimatedPrice(menu.getPrices())+" Đồng");
        Glide.with(context)
                .load(Server.duongdananh+menu.getImages())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontTransform()
                .dontAnimate()
                .into(viewHolder.imageView);
        if (menu.getDates().equals("day")){
            viewHolder.tvTime.setTextColor(0xCC00CC00);
            viewHolder.tvTime.setText("Cả ngày");
        }else if (menu.getDates().equals("lunch")){
            viewHolder.tvTime.setTextColor(0x99FF9900);
//            viewHolder.tvTime.setTextColor(0xFFFF9900);
            viewHolder.tvTime.setText("Bữa trưa");
        }else if (menu.getDates().equals("dinner")){
            viewHolder.tvTime.setTextColor(0x99FF9900);
            viewHolder.tvTime.setText("Bữa tối");
        }
        viewHolder.itemView.setOnClickListener(view -> {
            monLisenner.onclick(lauList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return lauList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName,tvPrice,tvTime;
        public RoundedImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_time_mon_an);
            tvName = itemView.findViewById(R.id.tv_name_mon_an);
            tvPrice = itemView.findViewById(R.id.tv_price_mon_an);
            imageView = itemView.findViewById(R.id.imgAvatar);
        }
    }

}
