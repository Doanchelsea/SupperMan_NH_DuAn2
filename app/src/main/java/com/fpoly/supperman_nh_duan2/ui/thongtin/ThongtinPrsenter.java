package com.fpoly.supperman_nh_duan2.ui.thongtin;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fpoly.supperman_nh_duan2.R;
import com.fpoly.supperman_nh_duan2.api.Server;
import com.fpoly.supperman_nh_duan2.ui.login.LoginActivity;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class ThongtinPrsenter {
    Context context;
    ThongtinContract contract;

    public ThongtinPrsenter(Context context, ThongtinContract contract) {
        this.context = context;
        this.contract = contract;
    }

    public void updateToken(String token,String idnhahang){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanupdatetoken, response -> {
            contract.success();
        },error -> {
            contract.error(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("idnhahang",idnhahang);
                hashMap.put("tokennh",token);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);

    }
}
