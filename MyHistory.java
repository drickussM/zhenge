package com.kedaexpress.com.kedaexpressapp;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyHistory extends Activity {
    final Data app = (Data) getApplication();
    String username;
    private static final String URL = "http://120.26.47.6/user_history_list.php";
    private RequestQueue requestQueue;
    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_history);

        final Data app = (Data)getApplication();
        username = app.getB();
        requestQueue = Volley.newRequestQueue(this);
        push();}

    private void populateListView(JSONArray array) {

        List<Map<String,Object>> data= new ArrayList<Map<String,Object>>();

        try{
            for(int i =0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Map<String,Object> item = new HashMap<String,Object>();
                item.put("time", obj.getString("date_time"));
                item.put("company", obj.getString("company"));
                item.put("phone", obj.getString("phoneNumber"));
                item.put("owner", obj.getString("owner"));
                data.add(item);}}

        catch (Exception e){}

        ListAdapter adapter = new SimpleAdapter(this,data,R.layout.history_items,new String[]{"time","company","phone","owner"},new int[]{
                R.id.time,R.id.company,R.id.phone,R.id.owner});
        ListView list = (ListView) findViewById(R.id.historyListView);
        list.setAdapter(adapter);}

    public void push () {
        request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String res) {
                Log.v("debug", res);

                try {JSONArray temp = new JSONArray(res);
                    populateListView(temp);

                    for(int i = 0;i < temp.length();++i){
                        JSONObject obj = temp.getJSONObject(i);
                        Log.v("info",obj.getString("owner"));}}

                catch (JSONException e) {
                    Log.v("debug", "can't get json");
                    e.printStackTrace();}}},
                new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {}}) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.get("date_time");
                hashMap.get("company");
                hashMap.get("phoneNumber");
                hashMap.get("owner");
                hashMap.put("username", username.toString());

                return hashMap;}};
        requestQueue.add(request);}}
