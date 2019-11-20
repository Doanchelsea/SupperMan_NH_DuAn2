package com.fpoly.supperman_nh_duan2.ui.home.diglog.presenter;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fpoly.supperman_nh_duan2.R;
import com.fpoly.supperman_nh_duan2.api.Server;
import com.fpoly.supperman_nh_duan2.model.People;
import com.fpoly.supperman_nh_duan2.ui.home.diglog.contract.OnlineDialogContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OnlineDialogPresenter {
    Context context;
    OnlineDialogContract contract;

    public OnlineDialogPresenter(Context context, OnlineDialogContract contract) {
        this.context = context;
        this.contract = contract;
    }

    public void getData(int idnhahang, List<People> list){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.banannh, response -> {

            if (response != null && response.length() != 2){
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        list.add(new People(jsonObject.getInt("soban")));
                    }
                    contract.showSuccess();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },error -> {
            contract.error(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("idnhahang",String.valueOf(idnhahang));
                hashMap.put("trangthai","true");
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
