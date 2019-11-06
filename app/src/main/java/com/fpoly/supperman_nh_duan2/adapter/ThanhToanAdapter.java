package com.fpoly.supperman_nh_duan2.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.daimajia.swipe.implments.SwipeItemRecyclerMangerImpl;
import com.daimajia.swipe.interfaces.SwipeAdapterInterface;
import com.daimajia.swipe.interfaces.SwipeItemMangerInterface;
import com.daimajia.swipe.util.Attributes;
import com.fpoly.supperman_nh_duan2.R;
import com.fpoly.supperman_nh_duan2.api.Server;
import com.fpoly.supperman_nh_duan2.lisenner.ThanhToanLisenner;
import com.fpoly.supperman_nh_duan2.model.ThanhToan;
import com.fpoly.supperman_nh_duan2.untils.FormatUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class ThanhToanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SwipeAdapterInterface, SwipeItemMangerInterface {

    Context context;
    List<ThanhToan> list;
    ThanhToanLisenner thanhToanLisenner;
    private SwipeItemRecyclerMangerImpl itemRecyclerManger;

    public ThanhToanAdapter(Context context, List<ThanhToan> list, ThanhToanLisenner thanhToanLisenner) {
        this.context = context;
        this.list = list;
        this.thanhToanLisenner = thanhToanLisenner;
        itemRecyclerManger = new SwipeItemRecyclerMangerImpl(this);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thanh_toan,parent,false);
        return new ViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ThanhToan thanhToan = list.get(position);
        ViewHodel viewHodel = (ViewHodel) holder;
        viewHodel.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        viewHodel.swipeLayout.addDrag(SwipeLayout.DragEdge.Right,viewHodel.relativeLayout);


        viewHodel.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.img_xoa_thanh_toan));
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });


        viewHodel.imgXoa.setOnClickListener(view -> {
            thanhToanLisenner.delete(thanhToan.getId());
        });

        viewHodel.tvName.setMaxLines(1);
        viewHodel.tvName.setEllipsize(TextUtils.TruncateAt.END);
        viewHodel.tvName.setText(thanhToan.getNamemonan());

        viewHodel.tvPrice.setText(FormatUtils.convertEstimatedPrice(thanhToan.getPrice())+"VNĐ");
        viewHodel.tvPrice.setTextColor(0xFFFF0000);

        viewHodel.tvSoluong.setText("Số lượng : "+thanhToan.getSoluong());
        viewHodel.tvSoluong.setTextColor(0xCC00CC00);

        Glide.with(context)
                .load(Server.duongdananh+thanhToan.getImages())
                .placeholder(R.drawable.img_hinhcho)
                .error(R.drawable.img_hinhcho)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontTransform()
                .dontAnimate()
                .into(viewHodel.imageView);

        itemRecyclerManger.bindView(holder.itemView,holder.getAdapterPosition());

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipeLayout;
    }

    @Override
    public void openItem(int position) {

    }

    @Override
    public void closeItem(int position) {

    }

    @Override
    public void closeAllExcept(SwipeLayout layout) {

    }

    @Override
    public void closeAllItems() {

    }

    @Override
    public List<Integer> getOpenItems() {
        return null;
    }

    @Override
    public List<SwipeLayout> getOpenLayouts() {
        return null;
    }

    @Override
    public void removeShownLayouts(SwipeLayout layout) {

    }

    @Override
    public boolean isOpen(int position) {
        return false;
    }

    @Override
    public Attributes.Mode getMode() {
        return null;
    }

    @Override
    public void setMode(Attributes.Mode mode) {

    }

    public class ViewHodel extends RecyclerView.ViewHolder {
        public SwipeLayout swipeLayout;
        public RelativeLayout relativeLayout;
        public TextView tvName,tvPrice,tvSoluong;
        public ImageView imgXoa;
        public RoundedImageView imageView;
        public ViewHodel(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_mon_thanh_toan);
            tvPrice = itemView.findViewById(R.id.tv_item_price_thanh_toan);
            tvSoluong = itemView.findViewById(R.id.tv_item_so_luong_thanh_toan);
            imageView = itemView.findViewById(R.id.img_item_thanh_toan);
            swipeLayout = itemView.findViewById(R.id.swipeLayout);
            relativeLayout = itemView.findViewById(R.id.relativeLayout_swipe);
            imgXoa = itemView.findViewById(R.id.img_xoa_thanh_toan);
        }
    }
}
