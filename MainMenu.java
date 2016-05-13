package com.kedaexpress.com.kedaexpressapp;

import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class MainMenu extends Activity {
    private ImageView pickup;
    private ImageView myhistory;
    private ImageView aboutus;
    private ImageView mysettings;
    private TextView logoutLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        pickup = (ImageView) findViewById(R.id.pickup);
        myhistory = (ImageView) findViewById(R.id.myhistory);
        aboutus = (ImageView) findViewById(R.id.aboutus);
        mysettings = (ImageView) findViewById(R.id.mysettings);
        logoutLink = (TextView) findViewById(R.id.logoutLink);

    logoutLink.setOnClickListener(new View.OnClickListener()

    {@Override
        public void onClick (View v){
        Intent intent = new Intent(getApplicationContext(), Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }});

        pickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Homepage.class));
            }
        });
        myhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MyHistory.class));
            }
        });
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AboutUs.class));
            }
        });
        mysettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MySettings.class));
            }
        });
}
    @Override
    public void onBackPressed() {
    }

}
