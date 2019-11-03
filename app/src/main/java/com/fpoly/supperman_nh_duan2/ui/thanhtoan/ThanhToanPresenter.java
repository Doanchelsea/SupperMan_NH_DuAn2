package com.fpoly.supperman_nh_duan2.ui.thanhtoan;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fpoly.supperman_nh_duan2.R;
import com.fpoly.supperman_nh_duan2.api.Server;
import com.fpoly.supperman_nh_duan2.model.ThanhToan;
import com.fpoly.supperman_nh_duan2.ui.main.MainActivity;
import com.fpoly.supperman_nh_duan2.untils.FormatUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ThanhToanPresenter  {
    Context context;
    ThanhToanContract thanhToanContract;

    public ThanhToanPresenter(Context context, ThanhToanContract thanhToanContract) {
        this.context = context;
        this.thanhToanContract = thanhToanContract;
    }

    public void getData(List<ThanhToan> thanhToans,int ban){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanthanhtoan, response -> {
            int id;
            String namemonan;
            String images;
            int price;
            int banan;
            int discounts;
            int soluong;
            int iduser;
            int tong = 0;
            if (response != null && response.length() != 2) {
                thanhToans.clear();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        id = jsonObject.getInt("id");
                        namemonan = jsonObject.getString("namemonan");
                        images = jsonObject.getString("images");
                        price = jsonObject.getInt("price");
                        banan = jsonObject.getInt("banan");
                        discounts = jsonObject.getInt("discounts");
                        soluong = jsonObject.getInt("soluong");
                        iduser = jsonObject.getInt("iduser");
                        thanhToans.add(new ThanhToan(id,namemonan,images,price,banan,discounts,soluong,iduser));
                        tong = tong + (price*soluong) ;
                        if (i == jsonArray.length()-1){
                            double tong1 = tong;
                            thanhToanContract.ShowPrice(tong1,thanhToans.get(0).getDiscounts(),thanhToans.get(0).getIduer());
                        }
                    }
                    thanhToanContract.ShowList(thanhToans);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }else {
                thanhToanContract.Error();
            }
        },error -> {
            thanhToanContract.ShowError(R.string.error);
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("banan",String.valueOf(ban));
                hashMap.put("idtrangthai","0");
                hashMap.put("idnhahang",MainActivity.ID);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void addHistory(int iduser, double price, int ban){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest  = new StringRequest(Request.Method.POST,Server.duongdanaddhistory,response -> {
            thanhToanContract.ShowHistory();
        },error -> {

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Calendar calendar = Calendar.getInstance();
                CharSequence date = FormatUtils.convertDate(calendar.getTime());
                Random random = new Random();
                String idtrangthai = random.nextLong() + String.valueOf(calendar.getTimeInMillis() + ban);
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("iduser",String.valueOf(iduser));
                hashMap.put("idnhahang",String.valueOf(MainActivity.ID));
                hashMap.put("price",String.valueOf(price));
                hashMap.put("date",String.valueOf(date));
                hashMap.put("namenh",MainActivity.NAME);
                hashMap.put("images",MainActivity.IMAGE);
                hashMap.put("tables",String.valueOf(ban));
                hashMap.put("idtrangthai",idtrangthai);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void delete(int id){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest  = new StringRequest(Request.Method.POST,Server.duongdandeletethantoan,response -> {
            thanhToanContract.showDelete();
        },error -> {

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("id",String.valueOf(id));
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
