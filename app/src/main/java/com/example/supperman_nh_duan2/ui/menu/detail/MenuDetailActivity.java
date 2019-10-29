package com.example.supperman_nh_duan2.ui.menu.detail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.supperman_nh_duan2.R;
import com.example.supperman_nh_duan2.api.Server;
import com.example.supperman_nh_duan2.base.BaseActivity;
import com.example.supperman_nh_duan2.model.Menu;
import com.example.supperman_nh_duan2.ui.ratting.DiglogAdd;
import com.example.supperman_nh_duan2.ui.updatemenu.UpdateMenuActivity;
import com.example.supperman_nh_duan2.untils.FormatUtils;
import com.example.supperman_nh_duan2.untils.ImageConverter;
import com.jakewharton.rxbinding3.view.RxView;
import com.novoda.merlin.Bindable;
import com.novoda.merlin.Connectable;
import com.novoda.merlin.Disconnectable;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.NetworkStatus;

import java.util.Calendar;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

public class MenuDetailActivity extends BaseActivity implements Connectable, Disconnectable, Bindable,DetailContract,ListennerDetail,DeleteMenuLisenner {
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.img_mon_an)
    ImageView img_mon_an;
    @BindView(R.id.spiner_mon_an)
    Spinner spinner;
    @BindView(R.id.tv_time_date)
    TextView tv_time_date;
    @BindView(R.id.tv_chi_tiet_mon_an)
    TextView tv_chi_tiet_mon_an;
    @BindView(R.id.tv_name_mon_an)
    TextView tv_name_mon_an;
    @BindView(R.id.activity_register_iv_back_dat_mon)
    ImageView imgback_dat_mon;
    @BindView(R.id.tv_dat_mon)
    TextView tv_dat_mon;
    @BindView(R.id.tv_so_luong)
    TextView tv_so_luong;
    @BindView(R.id.activity_popup_menu)
    ImageView activity_popup_menu;
    Menu menu;
    String spinerSL;
    Calendar calendar = Calendar.getInstance();

    private DetailPresenter detailPresenter;
    private static final String EXTRA_MENU = "EXTRA_MENU";

    public static void startActivity(Activity context, Menu menu){
        context.startActivity(new Intent(context,MenuDetailActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .putExtra(EXTRA_MENU, menu));
    }
    @Override
    protected void onResume() {
        super.onResume();
        registerBindable(this);
        registerConnectable(this);
        registerDisconnectable(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_menu_detail;
    }

    @Override
    protected Merlin initMerlin() {
        return new Merlin.Builder()
                .withConnectableCallbacks()
                .withBindableCallbacks()
                .withDisconnectableCallbacks().build(this);
    }

    @Override
    protected void initData() {
        detailPresenter = new DetailPresenter(this,this);
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void addEvents() {
         menu = getIntent().getParcelableExtra(EXTRA_MENU);
        if (menu == null){
            return;
        }
        detailPresenter.Spiner(spinner,menu.getIdmonan());
        loadAvatar(Server.duongdananh+menu.getImages(),img_mon_an);
        if (menu.getDates().equals("day")){
            tv_time_date.setText("Cả ngày");
        }else if (menu.getDates().equals("lunch")){
            tv_time_date.setText("Bữa trưa");
        }else if (menu.getDates().equals("dinner")){
            tv_time_date.setText("Bữa tối");
        }
        tv_name_mon_an.setText(menu.getNames());
        tv_chi_tiet_mon_an.setText(menu.getDescriptions());
        if (menu.getIdmonan() == 1){
            tv_so_luong.setText("Số người");
        }else {
            tv_so_luong.setText("Số lượng");
        }


        addDisposable(RxView.clicks(imgback_dat_mon).subscribe(unit -> {
            onBackPressed();
            finish();
        }));

        addDisposable(RxView.clicks(tv_dat_mon).subscribe(unit -> {

            String time = FormatUtils.convertEstimatedDate3(calendar.getTime());
            if (menu.getDates().equals("day")){
                detailPresenter.soban();
            }else if (menu.getDates().equals("lunch")){
                if (10 <= Integer.valueOf(time) && Integer.valueOf(time) < 14){
                    detailPresenter.soban();
                }else {
                    Toasty.warning(this,"Món ăn hết giờ làm").show();
                }
            }else if (menu.getDates().equals("dinner")){

                if (19 <= Integer.valueOf(time) && Integer.valueOf(time) < 23){
                    detailPresenter.soban();
                }else {
                    Toasty.warning(this,"Món ăn hết giờ làm").show();
                }
            }
        }));
        addDisposable(RxView.clicks(activity_popup_menu).subscribe(unit -> {
            ShowMenu();
        }));
    }

    @Override
    public void onBind(NetworkStatus networkStatus) {
        if (!networkStatus.isAvailable()){
            onDisconnect();
        }
    }

    @Override
    public void onConnect() {

    }

    @Override
    public void onDisconnect() {
        showToastDisconnect();
    }

    @Override
    public void showSpiner(String spiner,String time) {
        spinerSL = spiner;
        if (menu.getIdmonan() == 1){
            tv_date.setText("Số người : "+spiner+" - Ngày "+time);
        }else {
            tv_date.setText("Số lượng : "+spiner+" - Ngày "+time);
        }
    }

    @Override
    public void showSoban(int ban) {
        DigLogDetail dialog = DigLogDetail.newInstance(ban);
        dialog.show(getSupportFragmentManager(), dialog.getTag());
    }

    @Override
    public void showBanerror(int error) {
        Toasty.error(this,error).show();
    }

    @Override
    public void ShowSuccess() {
        Toasty.success(this,"Đặt món thành công").show();
        finish();
    }

    @Override
    public void showXoaSuccess() {
        finish();
    }


    @Override
    public void success(String soban) {
        detailPresenter.Sucess(soban,spinerSL,menu.getPrices(),menu.getNames(),menu.getImages(),menu.getIdmonan());
    }

    private void ShowMenu(){
        PopupMenu popupMenu = new PopupMenu(context,activity_popup_menu);
        popupMenu.getMenuInflater().inflate(R.menu.menu1,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(menuItem -> {

            switch (menuItem.getItemId()){
                case R.id.menu_sua:
                    UpdateMenuActivity.startActivity(this,menu);
                    break;

                case R.id.menu_xoa:
                    DiglogComfimMenu dialog = DiglogComfimMenu.newInstance(menu);
                    dialog.show(getSupportFragmentManager(), dialog.getTag());
                    break;
            }
            return false;
        });
        popupMenu.show();
    }

    @Override
    public void onClick(int id) {
        detailPresenter.xoaMenu(id);
    }
}
