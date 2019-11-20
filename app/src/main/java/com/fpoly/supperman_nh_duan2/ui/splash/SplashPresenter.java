package com.fpoly.supperman_nh_duan2.ui.splash;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fpoly.supperman_nh_duan2.R;
import com.fpoly.supperman_nh_duan2.api.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SplashPresenter {
    Context context;
    SplashContract contract;

    public SplashPresenter(Context context, SplashContract contract) {
        this.context = context;
        this.contract = contract;
    }

    public void getData(String idnhahang){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.tokennh, response -> {
            if (response.length() != 2) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String token = jsonObject.getString("tokennh");
                        contract.showSucces(token);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                contract.showerror(R.string.error_error);
            }
        },error -> {
            contract.showerror(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("idnhahang",idnhahang);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
