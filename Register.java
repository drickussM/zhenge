package com.kedaexpress.com.kedaexpressapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Register extends Activity {

    EditText Username, PhoneNumber, Password, confPassword;
    Button registerButton;
    private RequestQueue requestQueue;
    private static final String URL = "http://120.26.47.6/reg_control.php";
    private StringRequest request;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Username = (EditText) findViewById(R.id.new_username);
        PhoneNumber = (EditText) findViewById(R.id.new_phoneNumber);
        Password = (EditText) findViewById(R.id.new_password);
        confPassword = (EditText) findViewById(R.id.confirm_new_password);
        registerButton = (Button) findViewById(R.id.registerButton);

        requestQueue = Volley.newRequestQueue(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (Username.getText().toString().equals("") || PhoneNumber.getText().toString().equals("")|| Password.getText().toString().equals("")|| confPassword.getText().toString().equals(""))
                        {
                            String msg1 = (String) getText(R.string.Ooops);
                            String msg2 = (String) getText(R.string.fillFields);
                            String msg3 = (String) getText(R.string.ok);
                            builder = new AlertDialog.Builder(Register.this);
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

                        else if (Password.length()<6){
                            String msg = (String) getText(R.string.passwordMinLenght);
                            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                            Password.setText("");
                        }
                        else if (PhoneNumber.length()<11){
                            String msg = (String) getText(R.string.invalidPhoneNumber);
                            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                        }

                        else if (Username.length()<5){
                            String msg = (String) getText(R.string.shortUsername);
                            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                        }

                        else if (!(Password.getText().toString().equals(confPassword.getText().toString())))
                        {
                            String msg = (String) getText(R.string.unmatchingPasswords);
                            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                            Password.setText("");
                            confPassword.setText("");
                        }

                        else if(response.indexOf("error")!=-1)
                        {
                            String msg = (String) getText(R.string.userExists);
                            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            String msg = (String) getText(R.string.regSucc);
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> hashMap = new HashMap<String, String>();
                        hashMap.put("username", Username.getText().toString());
                        hashMap.put("phoneNumber", PhoneNumber.getText().toString());
                        hashMap.put("password", Password.getText().toString());
                        hashMap.put("confPassword", confPassword.getText().toString());

                        return hashMap;
                    }
                };
                requestQueue.add(request);
            }


        });

    }
}