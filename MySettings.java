package com.kedaexpress.com.kedaexpressapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MySettings extends Activity {
    private EditText dftOwner, dftPhoneNumber, dftRoom;
    private Button saveSettingsButton;
    private ImageView updPassImage;
    private String username;
    AlertDialog.Builder builder;
    private static final String URL = "http://120.26.47.6/default_info.php";
    final Data app = (Data)getApplication();
    private RequestQueue requestQueue;
    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_settings);

        requestQueue = Volley.newRequestQueue(this);
        final Data app = (Data)getApplication();
        dftOwner = (EditText) findViewById(R.id.dft_owner);
        dftRoom =(EditText) findViewById(R.id.dft_room);
        dftPhoneNumber = (EditText) findViewById(R.id.dft_phoneNumber);
        saveSettingsButton = (Button) findViewById(R.id.saveSettingsBn);
        updPassImage = (ImageView) findViewById(R.id.updPassImg);
        username = app.getB();
        setDefault ();

        updPassImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),PasswordUpdate.class));}
        });}

    public void setDefault () {
        final Data app = (Data)getApplication();
        saveSettingsButton = (Button) findViewById(R.id.saveSettingsBn);
        saveSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("debug", response);
                        try {JSONObject temp = new JSONObject(response);
                            Log.v("debug", temp.getString("UpdateState"));}

                        catch (JSONException e) {
                            Log.v("debug", "can't get json");
                            e.printStackTrace();}

                        if (dftOwner.getText().toString().equals("") || dftPhoneNumber.getText().toString().equals("") || dftRoom.getText().toString().equals("")) {
                            String msg1 = (String) getText(R.string.Ooops);
                            String msg2 = (String) getText(R.string.fillFields);
                            String msg3 = (String) getText(R.string.ok);
                            builder = new AlertDialog.Builder(MySettings.this);
                            builder.setTitle(msg1);
                            builder.setMessage(msg2);
                            builder.setPositiveButton(msg3, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();}});
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();}

                        else if (dftPhoneNumber.length() < 11) {
                            String msg1 = (String) getText(R.string.invalidPhoneNumber);
                            Toast.makeText(getApplicationContext(), msg1, Toast.LENGTH_SHORT).show();}

                        else if (dftRoom.length() < 3) {
                            String msg1 = (String) getText(R.string.wrongRoomNum);
                            Toast.makeText(getApplicationContext(), msg1, Toast.LENGTH_SHORT).show();}

                        else if (response.indexOf("Error") != -1) {
                            String msg1 = (String) getText(R.string.Couldnotsetdefaultinfo);
                        Toast.makeText(getApplicationContext(), msg1, Toast.LENGTH_SHORT).show();
                        dftOwner.setText("");
                        dftPhoneNumber.setText("");
                        dftRoom.setText("");}

                        else if (response.indexOf("Updated") != -1) {
                            String msg1 = (String) getText(R.string.Defaultinfoupdated);
                            Toast.makeText(getApplicationContext(), msg1, Toast.LENGTH_SHORT).show();
                            finish();}

                        else{String msg1 = (String) getText(R.string.Defaultinfosettled);
                            Toast.makeText(getApplicationContext(), msg1, Toast.LENGTH_SHORT).show();
                            finish();}}},

                        new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {}}){

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("def_owner", dftOwner.getText().toString());
                        hashMap.put("def_phoneNumber", dftPhoneNumber.getText().toString());
                        hashMap.put("def_room", dftRoom.getText().toString());
                        hashMap.put("username", username.toString());
                        return hashMap;}};
                requestQueue.add(request);}});}}
