package com.kedaexpress.com.kedaexpressapp;

/**
 * Created by Cedric on 5/7/16.
 */
import android.app.Application;
import android.text.format.DateFormat;

import org.json.JSONArray;

import java.sql.Date;
import java.sql.Struct;

public class Data extends Application{
    private String b,x;

    public String getB(){
        return this.b;
    }
    public String getX(){return this.x;}

    public void setB(String c){this.b= c;}
    public void setX(String d){this.x= d;}
    @Override
    public void onCreate(){
        b = "";
        x = "";
        super.onCreate();
    }
}

