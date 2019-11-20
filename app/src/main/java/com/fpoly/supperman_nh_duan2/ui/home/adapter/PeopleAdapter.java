package com.fpoly.supperman_nh_duan2.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.supperman_nh_duan2.R;
import com.fpoly.supperman_nh_duan2.lisenner.PeopleLisenner;
import com.fpoly.supperman_nh_duan2.model.People;


import java.util.List;

public class PeopleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<People> peoples;
    PeopleLisenner peopleLisenner;

    public PeopleAdapter(Context context, List<People> people,PeopleLisenner peopleLisenner) {
        this.context = context;
        this.peoples = people;
        this.peopleLisenner = peopleLisenner;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_online_detail,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        People people = peoples.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;

        if (position == 0){
            viewHolder.view.setVisibility(View.GONE);
        }else {
            viewHolder.view.setVisibility(View.VISIBLE);
        }
        viewHolder.textView.setText("Bàn số : "+people.getBanan());
        viewHolder.itemView.setOnClickListener(view -> {
            peopleLisenner.peoole(peoples.get(position).getBanan());
        });
    }

    @Override
    public int getItemCount() {
        return peoples.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.view_item_ban_an_online_detail);
            textView = itemView.findViewById(R.id.tv_item_ban_an_online_detail);
        }
    }
}
