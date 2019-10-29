package com.example.supperman_nh_duan2.ui.login;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.supperman_nh_duan2.R;
import com.example.supperman_nh_duan2.api.Server;
import com.example.supperman_nh_duan2.model.manage.Manage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

public class LoginPresenter {

    LoginContract loginContract;
    Context context;

    public LoginPresenter(LoginContract loginContract, Context context) {
        this.loginContract = loginContract;
        this.context = context;
    }

    public void Login(final String phone){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,Server.duongdanlogin,
                response -> {
                    int id;
                    String phones;
                    String name;
                    String image;
                    if (response != null) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                id = jsonObject.getInt("id");
                                phones = jsonObject.getString("phones");
                                image = jsonObject.getString("images");
                                name = jsonObject.getString("names");
                                if (phone.equals(phones)){
                                    loginContract.ShowSuccer(id,name,image);
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
