package com.fpoly.supperman_nh_duan2.ui.updatemenu;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fpoly.supperman_nh_duan2.R;
import com.fpoly.supperman_nh_duan2.api.Server;

import java.util.HashMap;
import java.util.Map;

public class UpdatePresenter {
    Context context;
    UpdateContract contract;

    public UpdatePresenter(Context context, UpdateContract contract) {
        this.context = context;
        this.contract = contract;
    }

    public void postMonAn(String name, String price, String description, String idmonan, String images, String dates,int id){

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanupdatemenu,
                response -> {
            contract.ShowSuccer();
                },error -> {
            contract.ShowError(R.string.error);

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("idmonan",idmonan);
                hashMap.put("dates",dates);
                hashMap.put("descriptions",description);
                hashMap.put("images",images);
                hashMap.put("names",name);
                hashMap.put("prices",price);
                hashMap.put("id",""+id);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
