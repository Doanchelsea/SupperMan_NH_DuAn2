package com.fpoly.supperman_nh_duan2.ui.login;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fpoly.supperman_nh_duan2.R;
import com.fpoly.supperman_nh_duan2.api.Server;
import com.fpoly.supperman_nh_duan2.model.local.DataManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginPresenter {

    LoginContract loginContract;
    Context context;
    private DataManager dataManager;

    public LoginPresenter(LoginContract loginContract, Context context,DataManager dataManager) {
        this.loginContract = loginContract;
        this.context = context;
        this.dataManager = dataManager;
    }

    public void Login(final String phone){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,Server.duongdanlogin,
                response -> {
                    int id;
                    String phones;
                    String name;
                    String image;
                    String lat;
                    String lng;
                    if (response != null) {
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

                                if (phone.equals(phones)){
                                    loginContract.ShowSuccer();
                                    dataManager.updateUserInfoSharedPreference(String.valueOf(id),name,image,true);
                                    return;
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        loginContract.ShowError(R.string.error_user);
                    }
                },error -> {
            loginContract.ShowError(R.string.error);
        });

        requestQueue.add(stringRequest);
    }
}
