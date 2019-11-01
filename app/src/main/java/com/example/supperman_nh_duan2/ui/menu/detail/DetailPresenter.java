package com.example.supperman_nh_duan2.ui.menu.detail;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
import com.example.supperman_nh_duan2.untils.FormatUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailPresenter {
    Context context;
    DetailContract detailContract;

    public DetailPresenter(Context context, DetailContract detailContract) {
        this.context = context;
        this.detailContract = detailContract;
    }

    public void Spiner(Spinner spinner,int idmonan){
        Calendar calendar = Calendar.getInstance();
        String time = FormatUtils.convertEstimatedDay(calendar.getTime())
                + "/"+FormatUtils.convertEstimatedMonth(calendar.getTime());
        List<String> list = new ArrayList<>();
        if (idmonan == 1 ){
            list.add("2");
            list.add("3");
            list.add("4");
            list.add("5");
            list.add("6");
        }else {
            list.add("1");
            list.add("2");
            list.add("3");
            list.add("4");
            list.add("5");
            list.add("6");
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context,android.R.layout.simple_spinner_item,list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                detailContract.showSpiner(spinner.getSelectedItem().toString(),time);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public void soban(){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanbanan,
                response -> {

                    if (response != null) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            int i = jsonArray.length();
                            detailContract.showSoban(i);

                            return;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    detailContract.showBanerror(R.string.error_edit_so_ban_an);
                },error -> {
            detailContract.showBanerror(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("idnhahang", MainActivity.ID);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void Sucess(String soban,String songuoi,int price,String name,String images,int idSL){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanaddmanage, response -> {
            detailContract.ShowSuccess();

        },error -> {

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                Calendar calendar = Calendar.getInstance();
                String date = FormatUtils.convertEstimatedDate1(calendar.getTime());
                String time = FormatUtils.convertEstimatedDate2(calendar.getTime());
                hashMap.put("dates",date);
                hashMap.put("idtime",""+idSL);
                hashMap.put("images",images);
                hashMap.put("names",name);
                hashMap.put("prices",""+price);
                hashMap.put("statusd","offline");
                hashMap.put("times",time);
                hashMap.put("discounts",""+0);
                if (idSL == 1){
                    hashMap.put("soluong",""+1);
                    hashMap.put("songuoi",""+songuoi);
                }else {
                    hashMap.put("soluong",""+songuoi);
                    hashMap.put("songuoi",""+1);
                }
                hashMap.put("tables",soban);
                hashMap.put("idnhahang",MainActivity.ID);
                hashMap.put("iduser","0");
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void xoaMenu(int id){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,Server.duongdandeletemenu,response -> {
            detailContract.showXoaSuccess();
        },error -> {
            detailContract.showBanerror(R.string.error);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("id",String.valueOf(id));
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
