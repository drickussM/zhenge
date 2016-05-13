package com.kedaexpress.com.kedaexpressapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONStringer;

import java.util.HashMap;
import java.util.Map;

public class Login extends Activity {

    private EditText Username, Password;
    private ImageView loginImg;
    private RequestQueue requestQueue;
    private TextView registerLink;
    private static final String URL = "http://120.26.47.6/login.php";
    private StringRequest request;
    AlertDialog.Builder builder;
    final Data app = (Data)getApplication();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Data app = (Data)getApplication();
        Username = (EditText) findViewById(R.id.username);
        Password = (EditText) findViewById(R.id.password);
        registerLink = (TextView) findViewById(R.id.registerLinkId);
        loginImg = (ImageView) findViewById(R.id.loginImg);
        requestQueue = Volley.newRequestQueue(this);

        loginImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("debug", response);
                        try {
                            JSONObject temp = new JSONObject(response);
                            app.setB(temp.getString("loggedInUser"));
                            app.setX(temp.getString("userPassword"));
                            Log.v("debug", temp.getString("loginState"));
                        } catch (JSONException e) {
                            Log.v("debug", "can't get json");
                            e.printStackTrace();
                        }

                        if (Username.getText().toString().equals("") || Password.getText().toString().equals(""))
                        {
                            String msg1 = (String) getText(R.string.Ooops);
                            String msg2 = (String) getText(R.string.enterUserInfo);
                            String msg3 = (String) getText(R.string.ok);
                            builder = new AlertDialog.Builder(Login.this);
                            builder.setTitle(msg1);
                            builder.setMessage(msg2);
                            builder.setPositiveButton(msg3, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }

                        else if(response.indexOf("Error")!=-1) {
                            String msg1 = (String) getText(R.string.wrongUser);
                            Toast.makeText(getApplicationContext(),msg1,Toast.LENGTH_SHORT).show();
                            Username.setText("");
                            Password.setText("");}

                        else {
                            String msg2 = (String) getText(R.string.loginSucc);
                            Toast.makeText(getApplicationContext(),msg2+Username.getText().toString(),Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainMenu.class));}}},
                        new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {}}){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> hashMap = new HashMap<String, String>();
                        hashMap.put("username", Username.getText().toString());
                        hashMap.put("password", Password.getText().toString());

                        return hashMap;}};
                requestQueue.add(request);}});

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(Login.this, Register.class);
                Login.this.startActivity(registerIntent);}
        });}}