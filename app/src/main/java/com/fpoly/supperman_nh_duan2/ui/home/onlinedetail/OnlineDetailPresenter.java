package com.fpoly.supperman_nh_duan2.ui.home.onlinedetail;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fpoly.supperman_nh_duan2.R;
import com.fpoly.supperman_nh_duan2.api.Server;
import com.fpoly.supperman_nh_duan2.model.manage.Manage;
import com.fpoly.supperman_nh_duan2.ui.main.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OnlineDetailPresenter {

    Context context;
    OnlineDetailContract contract;

    public OnlineDetailPresenter(Context context, OnlineDetailContract contract) {
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
                   int id = jsonObject.getInt("id");
                   String username = jsonObject.getString("username");
                   String password = jsonObject.getString("password");
                   String name = jsonObject.getString("name");
                   String phone = jsonObject.getString("phone");
                   String images = jsonObject.getString("images");
                   contract.showUser(name,phone,images);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        },error -> {
            contract.ShowError(R.string.error);
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

    public void delete(int id){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,Server.duongdandeletemanage,response -> {
            contract.showDelete();
        },error -> {
            contract.ShowError(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("idmanage",""+id);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void soban(){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanbanan,
                response -> {
                    if (response != null) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            int i = jsonArray.length();
                            contract.showSoban(i);
                            return;
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
                hashMap.put("idnhahang", MainActivity.ID);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void thanhtoan(Manage manage,int ban){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,Server.duongdanaddthanhtoan,response -> {
            contract.showthanhtoan(manage.getId());
        },error -> {
            contract.ShowError(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("namemonan",manage.getName());
                hashMap.put("images",manage.getImage());
                hashMap.put("price",""+manage.getPrice());
                hashMap.put("banan",""+ban);
                hashMap.put("discounts","15");
                hashMap.put("iduser",""+manage.getIduser());
                hashMap.put("idtrangthai","0");
                hashMap.put("idnhahang",MainActivity.ID);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
