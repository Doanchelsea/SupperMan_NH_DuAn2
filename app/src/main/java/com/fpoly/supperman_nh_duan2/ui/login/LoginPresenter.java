package com.fpoly.supperman_nh_duan2.ui.login;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fpoly.supperman_nh_duan2.R;
import com.fpoly.supperman_nh_duan2.api.Server;
import com.fpoly.supperman_nh_duan2.model.local.DataManager;
import com.fpoly.supperman_nh_duan2.untils.StringUtils;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class LoginPresenter {
    String token = "";
    LoginContract loginContract;
    Context context;
    private DataManager dataManager;

    public LoginPresenter(LoginContract loginContract, Context context, DataManager dataManager) {
        this.loginContract = loginContract;
        this.context = context;
        this.dataManager = dataManager;
    }


    public void Login(final String phone) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanlogin,
                response -> {
                    int id;
                    String phones;
                    String name;
                    String image;
                    String lat;
                    String lng;
                    if (response != null && response.length() != 2) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                id = jsonObject.getInt("id");
                                phones = jsonObject.getString("phones");
                                image = jsonObject.getString("images");
                                name = jsonObject.getString("names");
                                lat = jsonObject.getString("lat");
                                lng = jsonObject.getString("lng");
                                setToken(id);
                                dataManager.updateUserInfoSharedPreference(String.valueOf(id), name, image, true);
                                loginContract.ShowSuccer();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }{
                        loginContract.ShowError(R.string.error_user);
                    }
                }, error -> {
            loginContract.ShowError(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("phones",phone);
                return hashMap;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void setToken(int idnhahang){
        if (StringUtils.isEmpty(token)){
            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(task -> {
                        if (task != null){
                            token = task.getResult().getToken();
                            updateToken(token,idnhahang);
                        }
                    })
                    .addOnFailureListener(e -> {

             });
        }
    }

    private void updateToken(String token,int idnhahang){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,Server.duongdanupdatetoken,response -> {
            dataManager.setToken(token);
        },error -> {
           loginContract.ShowError(R.string.error);
           return;
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("idnhahang",String.valueOf(idnhahang));
                hashMap.put("tokennh",token);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}

