package com.fpoly.supperman_nh_duan2.ui.ratting;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fpoly.supperman_nh_duan2.R;
import com.fpoly.supperman_nh_duan2.api.Server;
import com.fpoly.supperman_nh_duan2.model.Banan;
import com.fpoly.supperman_nh_duan2.ui.main.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RattingPresenter {
    RattingContract rattingContract;
    Context context;

    public RattingPresenter(RattingContract rattingContract, Context context) {
        this.rattingContract = rattingContract;
        this.context = context;
    }

    public void getData(List<Banan> banans){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanbanan,
                response -> {
                    int id;
                    int soban;
                    int idnhahang;
                    String trangthai;
                    if (response != null && response.length() != 2) {
                        banans.clear();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                id = jsonObject.getInt("id");
                                soban = jsonObject.getInt("soban");
                                idnhahang = jsonObject.getInt("idnhahang");
                                trangthai = jsonObject.getString("trangthai");
                                banans.add(new Banan(id,soban,idnhahang,trangthai));
                            }
                            rattingContract.show(banans);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }else {
                        rattingContract.ShowError();
                    }
                },error -> {
            rattingContract.ShowErorr(R.string.error);
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

    public void soban(){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanbanan,
                response -> {

                    if (response != null) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            int i = jsonArray.length();
                            rattingContract.soban(i);
                            return;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                },error -> {

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
    public void xoaban(int ban){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdandeletebanan,
                response -> {
                  rattingContract.showxoaban();
                },error -> {

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("soban", ""+ban);
                hashMap.put("idnhahang", ""+MainActivity.ID);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }


    public void addData(){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanbanan,
                response -> {

                    if (response != null) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            int i = jsonArray.length();
                            add(i);
                            return;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    add(0);
                },error -> {
            rattingContract.ShowErorr(R.string.error);
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

    public void add(int soban){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanaddbanan,
                response -> {
                    rattingContract.ShowAdd();
                },error -> {
            rattingContract.ShowErorr(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("soban",""+(soban+1));
                hashMap.put("idnhahang", MainActivity.ID);
                hashMap.put("trangthai", "true");
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
