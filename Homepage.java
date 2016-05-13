package com.kedaexpress.com.kedaexpressapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import org.w3c.dom.UserDataHandler;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class Homepage extends Activity {
    Spinner spinner, date_spinner, time_spinner, building_spinner;
    ArrayAdapter<CharSequence> adapter;
    Button bSubmit;
    CheckBox checkBox;
    TextView regulationLink;
    EditText ownerPhone;
    EditText packOwner;
    EditText pickUpCode;
    EditText room;
    String username;
    AlertDialog.Builder builder;
    private static final String URL = "http://120.26.47.6/homepage.php";
    private static final String URL2 = "http://120.26.47.6/getDefault.php";
    private RequestQueue requestQueue, requestQueue2;
    private StringRequest request, request2;
    final Data app = (Data)getApplication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        requestQueue = Volley.newRequestQueue(this);
        requestQueue2 = Volley.newRequestQueue(this);
        final Data app = (Data)getApplication();
        room = (EditText) findViewById(R.id.room);
        date_spinner = (Spinner) findViewById(R.id.date_spinner);
        time_spinner = (Spinner) findViewById(R.id.time_spinner);
        building_spinner = (Spinner) findViewById(R.id.building_spinner);
        spinner = (Spinner) findViewById(R.id.company_spinner);
        packOwner = (EditText) findViewById(R.id.packowner);
        ownerPhone = (EditText) findViewById(R.id.ownerPhoneNumber);
        pickUpCode = (EditText) findViewById(R.id.pickUpCode);
        bSubmit = (Button) findViewById(R.id.submitBn);
        checkBox = (CheckBox) findViewById(R.id.agreementCheckBox);
        regulationLink = (TextView) findViewById(R.id.regulationLink);

        username = app.getB();

        request2 = new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {
            @Override
            public void onResponse(String res) {
                Log.v("debug", res);
                try
                {JSONObject temp = new JSONObject(res);
                    packOwner.setText(temp.getString("def_owner"));
                    ownerPhone.setText(temp.getString("def_phoneNumber"));
                    room.setText(temp.getString("def_room"));}
                catch
                        (JSONException e) {
                    Log.v("debug", "can't get json");
                    e.printStackTrace();}}},

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {}}) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap2 = new HashMap<String, String>();
                hashMap2.put("username", username.toString());

                return hashMap2;}};

        requestQueue2.add(request2);

        adapter = ArrayAdapter.createFromResource(this, R.array.company_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        adapter = ArrayAdapter.createFromResource(this, R.array.date_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        date_spinner.setAdapter(adapter);

        adapter = ArrayAdapter.createFromResource(this, R.array.time_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time_spinner.setAdapter(adapter);

        adapter = ArrayAdapter.createFromResource(this, R.array.building_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        building_spinner.setAdapter(adapter);
        action();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}});

        date_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}});

        time_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}});

        building_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}});

        regulationLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg1 = (String) getText(R.string.agreementText);
                String msg2 = (String) getText(R.string.agreement);
                String msg3 = (String) getText(R.string.ok);
                builder = new AlertDialog.Builder(Homepage.this);
                builder.setTitle(msg1);
                builder.setMessage(msg2);
                builder.setPositiveButton(msg3, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();}});
                AlertDialog alertDialog = builder.create();
                alertDialog.show();}});}

    public void action() {
        bSubmit = (Button) findViewById(R.id.submitBn);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(((CheckBox) v).isChecked())) {
                    String msg1 = (String) getText(R.string.agrementMust);
                    Toast.makeText(Homepage.this, msg1, Toast.LENGTH_SHORT).show();
                    bSubmit.setClickable(false);}

                else {String msg1 = (String) getText(R.string.agreementAcc);
                    Toast.makeText(Homepage.this, msg1, Toast.LENGTH_SHORT).show();
                    bSubmit.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>(){
                                @Override
                                public void onResponse(String response){
                                    Log.v("debug", response);
                                    try
                                        {JSONObject temp = new JSONObject(response);
                                        Log.v("debug", temp.getString("SendingState"));}
                                    catch
                                            (JSONException e) {
                                        Log.v("debug", "can't get json");
                                        e.printStackTrace();}

                                    if (packOwner.getText().toString().equals("") || ownerPhone.getText().toString().equals("") || pickUpCode.getText().toString().equals("") || room.getText().toString().equals("")) {
                                        String msg1 = (String) getText(R.string.Ooops);
                                        String msg2 = (String) getText(R.string.fillFields);
                                        String msg3 = (String) getText(R.string.ok);
                                        builder = new AlertDialog.Builder(Homepage.this);
                                        builder.setTitle(msg1);
                                        builder.setMessage(msg2);
                                        builder.setPositiveButton(msg3, new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();}});
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();}

                                    else if (ownerPhone.length() < 11) {
                                        String msg1 = (String) getText(R.string.invalidPhoneNumber);
                                        Toast.makeText(getApplicationContext(), msg1, Toast.LENGTH_SHORT).show();}

                                    else if (pickUpCode.length() < 3) {
                                        String msg1 = (String) getText(R.string.wrongPUC);
                                        Toast.makeText(getApplicationContext(), msg1, Toast.LENGTH_SHORT).show();
                                        pickUpCode.setText("");}

                                    else if (room.length() < 3) {
                                        String msg1 = (String) getText(R.string.wrongRoomNum);
                                        Toast.makeText(getApplicationContext(), msg1, Toast.LENGTH_SHORT).show();
                                        room.setText("");}

                                    else if (response.indexOf("Error") != -1) {
                                        String msg1 = (String) getText(R.string.DetailsReceived);
                                        Toast.makeText(getApplicationContext(), msg1, Toast.LENGTH_SHORT).show();
                                        pickUpCode.setText("");}

                                    else {String msg1 = (String) getText(R.string.PackDetailsSent);
                                        Toast.makeText(getApplicationContext(), msg1, Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), Succeed.class));}}},

                                    new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {}}) {

                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    HashMap<String, String> hashMap = new HashMap<String, String>();
                                    hashMap.put("company", spinner.getSelectedItem().toString());
                                    hashMap.put("owner", packOwner.getText().toString());
                                    hashMap.put("phoneNumber", ownerPhone.getText().toString());
                                    hashMap.put("pickUpCode", pickUpCode.getText().toString());
                                    hashMap.put("delivery_date", date_spinner.getSelectedItem().toString());
                                    hashMap.put("delivery_time", time_spinner.getSelectedItem().toString());
                                    hashMap.put("delivery_building", building_spinner.getSelectedItem().toString());
                                    hashMap.put("delivery_room", room.getText().toString());
                                    hashMap.put("username", username.toString());

                                    return hashMap;}};

                            requestQueue.add(request);}});}}});}}