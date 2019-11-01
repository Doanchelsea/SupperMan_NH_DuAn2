package com.example.supperman_nh_duan2.ui.history.detail;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.supperman_nh_duan2.R;
import com.example.supperman_nh_duan2.api.Server;
import com.example.supperman_nh_duan2.model.ThanhToan;
import com.example.supperman_nh_duan2.ui.main.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryDetailPresenter {

    Context context;
    HistoryDetailContract contract;

    public HistoryDetailPresenter(Context context, HistoryDetailContract contract) {
        this.context = context;
        this.contract = contract;
    }

        public void getUser(int Id){
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanuserdetail, response -> {

                if (response == null){
                    return;
                }
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String images = jsonObject.getString("images");
                        contract.ShowUser(name,images);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            },error -> {
                contract.showError(R.string.error);
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("id",""+Id);
                    return hashMap;
                }
            };
            requestQueue.add(stringRequest);
        }

    public void getData(List<ThanhToan> thanhToans,String idtrangthai){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanhistorydetail, response -> {
            int id;
            String namemonan;
            String images;
            int price;
            int banan;
            int discounts;
            int soluong;
            int iduser;
            if (response != null) {
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
                        contract.ShowList(thanhToans);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },error -> {
            contract.showError(R.string.error);
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("idnhahang", MainActivity.ID);
                hashMap.put("idtrangthai",idtrangthai);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
