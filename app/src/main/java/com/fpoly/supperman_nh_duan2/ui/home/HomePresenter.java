package com.fpoly.supperman_nh_duan2.ui.home;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fpoly.supperman_nh_duan2.R;
import com.fpoly.supperman_nh_duan2.api.Server;
import com.fpoly.supperman_nh_duan2.model.manage.Manage;
import com.fpoly.supperman_nh_duan2.ui.main.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomePresenter {

    Context context;
    HomeContract homeContract;


    public HomePresenter(Context context, HomeContract homeContract) {
        this.context = context;
        this.homeContract = homeContract;
    }

    public void getData(List<Manage> list,String sta){

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanmanage,
                response -> {
                    int id;
                    String date;
                    int idname;
                    String image;
                    String name;
                    int price;
                    String status;
                    String time;
                    int discounts;
                    int tables;
                    String soluong;
                    int idnhahang;
                    String songuoi;
                    int iduser;

                    if (response != null && response.length() != 2) {
                        list.clear();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                id = jsonObject.getInt("id");
                                date = jsonObject.getString("dates");
                                idname = jsonObject.getInt("idtime");
                                image = jsonObject.getString("images");
                                name = jsonObject.getString("names");
                                price = jsonObject.getInt("prices");
                                status = jsonObject.getString("statusd");
                                time = jsonObject.getString("times");
                                discounts = jsonObject.getInt("discounts");
                                tables = jsonObject.getInt("tables");
                                soluong = jsonObject.getString("soluong");
                                idnhahang = jsonObject.getInt("idnhahang");
                                songuoi = jsonObject.getString("songuoi");
                                iduser = jsonObject.getInt("iduser");
                                list.add(new Manage(id,date,idname,image,name,price,status,time,discounts,tables,soluong,idnhahang,songuoi,iduser));
                            }
                            homeContract.loadData(list);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else {
                        homeContract.ShowError();
                    }

        },error ->{
            homeContract.ShowErorr(R.string.error);
        } ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("idnhahang", MainActivity.ID);
                hashMap.put("statusd",sta);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void delete(int id){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,Server.duongdandeletemanage,response -> {
            homeContract.ShowDelete();
        },error -> {
            homeContract.ShowErorr(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("idmanage",""+id);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void thanhtoan(Manage manage){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,Server.duongdanaddthanhtoan,response -> {
            homeContract.addThanhtoan(manage.getId());
        },error -> {
            homeContract.ShowErorr(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("namemonan",manage.getName());
                hashMap.put("images",manage.getImage());
                hashMap.put("price",""+manage.getPrice());
                hashMap.put("banan",""+manage.getTables());
                hashMap.put("discounts","0");
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
