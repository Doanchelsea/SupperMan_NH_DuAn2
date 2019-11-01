package com.example.supperman_nh_duan2.ui.thongtin.detail;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.supperman_nh_duan2.R;
import com.example.supperman_nh_duan2.api.Server;
import com.example.supperman_nh_duan2.model.Banan;
import com.example.supperman_nh_duan2.ui.main.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThongTinDetailPresenter {
    Context context;
    ThongTinDetailContract contract;

    public ThongTinDetailPresenter(Context context, ThongTinDetailContract contract) {
        this.context = context;
        this.contract = contract;
    }

    public void Save(String name,String image){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanupdatenh, response -> {
            contract.ShowSucces();
        },error -> {
            contract.ShowError(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("names",name);
                hashMap.put("images",image);
                hashMap.put("id",MainActivity.ID);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void UpdateNH(){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanrequestnh, response -> {
            int id;
            String names;
            String images;
            if (response != null) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        id = jsonObject.getInt("id");
                        names = jsonObject.getString("names");
                        images = jsonObject.getString("images");
                        contract.ShowUpdate(names,images);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },error -> {
            contract.ShowError(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("id",MainActivity.ID);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
