package com.fpoly.supperman_nh_duan2.ui.home.onlinedetail;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fpoly.supperman_nh_duan2.R;
import com.fpoly.supperman_nh_duan2.api.PolyRetrofit;
import com.fpoly.supperman_nh_duan2.api.Server;
import com.fpoly.supperman_nh_duan2.model.manage.Manage;
import com.fpoly.supperman_nh_duan2.model.messing.Messing;
import com.fpoly.supperman_nh_duan2.ui.main.MainActivity;
import com.fpoly.supperman_nh_duan2.untils.Constans;
import com.fpoly.supperman_nh_duan2.untils.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnlineDetailPresenter {

    Context context;
    OnlineDetailContract contract;

    public OnlineDetailPresenter(Context context, OnlineDetailContract contract) {
        this.context = context;
        this.contract = contract;
    }

    public void getUser(Manage manage){
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
                hashMap.put("id",String.valueOf(manage.getIduser()));
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void delete(Manage manage){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,Server.duongdandeletemanage,response -> {
            getToken(manage);
        },error -> {
            contract.ShowError(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("idmanage",""+manage.getId());
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void postMessing(String token,int id){
        if (StringUtils.isEmpty(token)){
            return;
        }
        Map<String,String> map = new HashMap<>();
        map.put(Constans.CODE, Constans.USERCANCEL);
        map.put(Constans.MESSAGE,Constans.MESSAGE103);
        map.put(Constans.ID, String.valueOf(id));


        Map<String,Object> map1 = new HashMap<>();
        map1.put(Constans.TO,token);
        map1.put(Constans.DATA,map);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse(Constans.HTTP3),
                (new JSONObject(map1)).toString());

        PolyRetrofit.getInstance().getMesssing(Constans.KEY,body).enqueue(new Callback<Messing>() {
            @Override
            public void onResponse(Call<Messing> call, Response<Messing> response) {
                contract.showDelete();
            }

            @Override
            public void onFailure(Call<Messing> call, Throwable t) {
            }
        });
    }

    private void getToken(Manage manage){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.tokenuser, response -> {
            if (response.length() != 2) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0; i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String token = jsonObject.getString("tokenuser");
                        postMessing(token,manage.getId());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                contract.ShowError(R.string.error);
            }
        },error -> {
            contract.ShowError(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("iduser",String.valueOf(manage.getIduser()));
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
                hashMap.put("banan",String.valueOf(ban));
                hashMap.put("discounts","15");
                hashMap.put("soluong",manage.getSoluong());
                hashMap.put("iduser",""+manage.getIduser());
                hashMap.put("idtrangthai","0");
                hashMap.put("idnhahang",MainActivity.ID);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
