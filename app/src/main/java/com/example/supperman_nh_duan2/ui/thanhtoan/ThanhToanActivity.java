package com.example.supperman_nh_duan2.ui.thanhtoan;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.supperman_nh_duan2.R;
import com.example.supperman_nh_duan2.adapter.ThanhToanAdapter;
import com.example.supperman_nh_duan2.api.eventbus.HistoryEvent;
import com.example.supperman_nh_duan2.api.eventbus.NewEvent;
import com.example.supperman_nh_duan2.base.BaseActivity;
import com.example.supperman_nh_duan2.lisenner.LisennerDeleteThanhToan;
import com.example.supperman_nh_duan2.lisenner.ThanhToanLisenner;
import com.example.supperman_nh_duan2.model.ThanhToan;
import com.example.supperman_nh_duan2.ui.main.MainActivity;
import com.example.supperman_nh_duan2.ui.thanhtoan.dialog.DiglogDelete;
import com.example.supperman_nh_duan2.ui.thanhtoan.dialog.DiglogThanhToan;
import com.example.supperman_nh_duan2.untils.FormatUtils;
import com.jakewharton.rxbinding3.view.RxView;
import com.novoda.merlin.Bindable;
import com.novoda.merlin.Connectable;
import com.novoda.merlin.Disconnectable;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.NetworkStatus;
import com.tapadoo.alerter.Alerter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ThanhToanActivity extends BaseActivity implements Connectable, Disconnectable, Bindable,ThanhToanContract, ThanhToanLisenner, LisennerDeleteThanhToan {
    int ban;
    double tong1;
    @BindView(R.id.tv_ban_thanh_toan)
    TextView tv_ban_thanh_toan;
    @BindView(R.id.activity_register_iv_back_thanh_toan)
    ImageView img_thanh_toan;
    @BindView(R.id.recy_thanh_toan)
    RecyclerView recy_thanh_toan;
    @BindView(R.id.tv_price_thanh_toan)
    TextView tv_price_thanh_toan;
    @BindView(R.id.tv_thanh_toan)
    TextView tv_thanh_toan;
    private ThanhToanPresenter presenter;
    List<ThanhToan> thanhToans1 = new ArrayList<>();
    LinearLayoutManager manager = new LinearLayoutManager(this);
    ThanhToanAdapter adapter;
    boolean isOnClick = true;


    public static void startActivity(Activity context, int ban){
        context.startActivity(new Intent(context, ThanhToanActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .putExtra("BAN", ban));
    }

    @Override
    protected void onResume() {
        registerConnectable(this);
        registerDisconnectable(this);
        registerBindable(this);
        super.onResume();
    }



    @Override
    protected int getLayoutId() {
        return R.layout.activity_thanh_toan;
    }

    @Override
    protected Merlin initMerlin() {
        return new Merlin.Builder().withConnectableCallbacks()
                .withBindableCallbacks()
                .withDisconnectableCallbacks()
                .build(this);
    }

    @Override
    protected void initData() {
        presenter = new ThanhToanPresenter(this,this);
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void addEvents() {
        ban = getIntent().getIntExtra("BAN",0);
        tv_ban_thanh_toan.setText("Bàn "+ban);
        addDisposable(RxView.clicks(img_thanh_toan)
                .throttleFirst(2,TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(unit -> {
            onBackPressed();
            finish();
        }));
        presenter.getData(thanhToans1,ban);


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
    public void ShowList(List<ThanhToan> thanhToans) {
        recy_thanh_toan.setVisibility(View.VISIBLE);
        adapter = new ThanhToanAdapter(this,thanhToans,this);
        recy_thanh_toan.setHasFixedSize(true);
        recy_thanh_toan.setNestedScrollingEnabled(false);
        recy_thanh_toan.setLayoutManager(manager);
        recy_thanh_toan.setAdapter(adapter);
    }

    @Override
    public void ShowPrice(double tong,double dir, int iduser) {
         tong1 = tong - tong*(dir/100);
        tv_price_thanh_toan.setText(FormatUtils.convertEstimatedPrice(tong1)+ " VNĐ");
        addDisposable(RxView.clicks(tv_thanh_toan).subscribe(
                unit -> {
                    if (isOnClick == true) {
                        DiglogThanhToan dialog = DiglogThanhToan.getInstance(tong1, iduser, ban);
                        dialog.show(getSupportFragmentManager(), dialog.getTag());
                    }
                }
        ));
    }

    @Override
    public void ShowHistory() {
        Alerter.create(this)
                .setTitle(MainActivity.NAME)
                .setText("Thanh toán thành công "+FormatUtils.convertEstimatedPrice(tong1)+ " VNĐ")
                .setBackgroundColorRes(R.color.bg_color_alert_dialog)
                .setDuration(1500)
                .show();

        isOnClick = false;
        recy_thanh_toan.setVisibility(View.GONE);
        addDisposable(Observable.just(0).delay(1600, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aVoid -> {
                    finish();
                }));
    }

    @Override
    public void ShowError(int error) {
        Toasty.error(context,error).show();
    }

    @Override
    public void showDelete() {
        presenter.getData(thanhToans1,ban);
    }

    @Override
    public void Error() {
        tv_price_thanh_toan.setText("0 VNĐ");
        isOnClick = false;
        recy_thanh_toan.setVisibility(View.GONE);
    }

    @Override
    public void onClick(int iduser, double price, int ban) {
        presenter.addHistory(iduser,price,ban);
    }

    @Override
    public void delete(int id) {
            DiglogDelete dialog = DiglogDelete.getInstance(id);
            dialog.show(getSupportFragmentManager(), dialog.getTag());

    }

    @Override
    public void onClick(int id) {
        presenter.delete(id);
    }
}
