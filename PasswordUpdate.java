package com.kedaexpress.com.kedaexpressapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

public class PasswordUpdate extends AppCompatActivity {
    final Data app = (Data)getApplication();
    String username, userPass;
    private static final String URL = "http://120.26.47.6/update_password.php";
    private EditText oldPass, newPass, confnewPass;
    private Button updPassBtn;
    private StringRequest request;
    AlertDialog.Builder builder;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_update);

        final Data app = (Data)getApplication();
        requestQueue = Volley.newRequestQueue(this);
        oldPass = (EditText) findViewById(R.id.oldPassword);
        newPass = (EditText) findViewById(R.id.updnewPassword);
        confnewPass = (EditText) findViewById(R.id.updconfnewPassword);
        updPassBtn = (Button) findViewById(R.id.updPassBn);
        username = app.getB();
        userPass = app.getX();

        updPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        if (oldPass.getText().toString().equals("") || newPass.getText().toString().equals("")|| confnewPass.getText().toString().equals(""))
                        {
                            String msg1 = (String) getText(R.string.Ooops);
                            String msg2 = (String) getText(R.string.fillFields);
                            String msg3 = (String) getText(R.string.ok);
                            builder = new AlertDialog.Builder(PasswordUpdate.this);
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
                        else if (oldPass.length()<6 || newPass.length()<6 || confnewPass.length()<6){
                            String msg = (String) getText(R.string.passwordMinLenght);
                            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                            oldPass.setText("");
                            newPass.setText("");
                            confnewPass.setText("");}

                        else if (!(newPass.getText().toString().equals(confnewPass.getText().toString())))
                        {
                            String msg = (String) getText(R.string.unmatchingPasswords);
                            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                            newPass.setText("");
                            confnewPass.setText("");}

                        else if (!(oldPass.getText().toString().equals(userPass.toString()))){
                            String msg = (String) getText(R.string.WrongPassword);
                            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                            oldPass.setText("");
                            newPass.setText("");
                            confnewPass.setText("");
                        }

                        else if(response.indexOf("msg")!=-1)
                        {
                            String msg = (String) getText(R.string.Updatefailed);
                            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                            oldPass.setText("");
                            newPass.setText("");
                            confnewPass.setText("");}
                        else
                        {
                            String msg = (String) getText(R.string.Passwordupdated);
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                            finish();}
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> hashMap = new HashMap<String, String>();
                        hashMap.put("oldPassword", oldPass.getText().toString());
                        hashMap.put("newPassword", newPass.getText().toString());
                        hashMap.put("confnewPass", confnewPass.getText().toString());
                        hashMap.put("username", username.toString());

                        return hashMap;
                    }
                };
                requestQueue.add(request);
            }
        });
    }
}
