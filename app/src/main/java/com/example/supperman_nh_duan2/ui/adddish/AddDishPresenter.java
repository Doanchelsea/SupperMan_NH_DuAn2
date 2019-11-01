package com.example.supperman_nh_duan2.ui.adddish;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.supperman_nh_duan2.R;
import com.example.supperman_nh_duan2.api.Server;
import com.example.supperman_nh_duan2.ui.main.MainActivity;

import java.util.HashMap;
import java.util.Map;

public class AddDishPresenter {

    AddDishContract addDishContract;
    Context context;

    public AddDishPresenter(AddDishContract addDishContract,Context context) {
        this.addDishContract = addDishContract;
        this.context = context;
    }
    public void postMonAn(String name,String price, String description, String idmonan,String images,String dates){

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanaddmenu,
                response -> {
                        addDishContract.showSuccer();
                },error -> {
                     addDishContract.ShowError(R.string.error);

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
                hashMap.put("idnhahang", MainActivity.ID);
                hashMap.put("namenh",MainActivity.NAME);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
