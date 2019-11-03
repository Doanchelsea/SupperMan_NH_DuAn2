package com.fpoly.supperman_nh_duan2.ui.menu;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fpoly.supperman_nh_duan2.R;
import com.fpoly.supperman_nh_duan2.api.Server;
import com.fpoly.supperman_nh_duan2.model.Menu;
import com.fpoly.supperman_nh_duan2.ui.main.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuPresenter {
    Context context;
    MenuContract menuContract;

    public MenuPresenter(Context context, MenuContract menuContract) {
        this.context = context;
        this.menuContract = menuContract;
    }

    public void getData(List<Menu> list, String idmo){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanmenu, response ->{
            int id;
            int idmonan;
            String dates;
            String descriptions;
            String images;
            String names;
            int prices;
            int idnhahang;
            String namenh;
            if (response != null && response.length() != 2) {
                list.clear();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        id = jsonObject.getInt("id");
                        idmonan = jsonObject.getInt("idmonan");
                        dates = jsonObject.getString("dates");
                        descriptions = jsonObject.getString("descriptions");
                        images = jsonObject.getString("images");
                        names = jsonObject.getString("names");
                        prices = jsonObject.getInt("prices");
                        idnhahang = jsonObject.getInt("idnhahang");
                        namenh = jsonObject.getString("namenh");
                        list.add(new Menu(id,idmonan,dates,descriptions,images,names,prices,idnhahang,namenh));
                    }
                    menuContract.showList(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
               menuContract.showEror(Integer.valueOf(idmo));
            }
        } , error -> {
            menuContract.showError(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("page","1");
                hashMap.put("space","12");
                hashMap.put("idmonan",idmo);
                hashMap.put("idnhahang", MainActivity.ID);
                return hashMap ;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void getData1(List<Menu> list, String idmo){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanmenu, response ->{
            int id;
            int idmonan;
            String dates;
            String descriptions;
            String images;
            String names;
            int prices;
            int idnhahang;
            String namenh;
            if (response != null && response.length() != 2) {
                list.clear();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        id = jsonObject.getInt("id");
                        idmonan = jsonObject.getInt("idmonan");
                        dates = jsonObject.getString("dates");
                        descriptions = jsonObject.getString("descriptions");
                        images = jsonObject.getString("images");
                        names = jsonObject.getString("names");
                        prices = jsonObject.getInt("prices");
                        idnhahang = jsonObject.getInt("idnhahang");
                        namenh = jsonObject.getString("namenh");
                        list.add(new Menu(id,idmonan,dates,descriptions,images,names,prices,idnhahang,namenh));
                    }
                    menuContract.showList1(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                menuContract.showEror(Integer.valueOf(idmo));
            }
        } , error -> {
            menuContract.showError(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("page","1");
                hashMap.put("space","12");
                hashMap.put("idmonan",idmo);
                hashMap.put("idnhahang", MainActivity.ID);
                return hashMap ;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void getData2(List<Menu> list, String idmo){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanmenu, response ->{
            int id;
            int idmonan;
            String dates;
            String descriptions;
            String images;
            String names;
            int prices;
            int idnhahang;
            String namenh;
            if (response != null && response.length() != 2) {
                list.clear();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        id = jsonObject.getInt("id");
                        idmonan = jsonObject.getInt("idmonan");
                        dates = jsonObject.getString("dates");
                        descriptions = jsonObject.getString("descriptions");
                        images = jsonObject.getString("images");
                        names = jsonObject.getString("names");
                        prices = jsonObject.getInt("prices");
                        idnhahang = jsonObject.getInt("idnhahang");
                        namenh = jsonObject.getString("namenh");
                        list.add(new Menu(id,idmonan,dates,descriptions,images,names,prices,idnhahang,namenh));
                    }
                    menuContract.showList2(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                menuContract.showEror(Integer.valueOf(idmo));
            }
        } , error -> {
            menuContract.showError(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("page","1");
                hashMap.put("space","12");
                hashMap.put("idmonan",idmo);
                hashMap.put("idnhahang", MainActivity.ID);
                return hashMap ;
            }
        };
        requestQueue.add(stringRequest);
    }
}
