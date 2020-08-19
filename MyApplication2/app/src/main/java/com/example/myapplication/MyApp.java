package com.example.myapplication;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import entity_class.Employee;

public class MyApp extends Application {
    public static Employee user;
    public static int role;
    public static Context context;

    public static int stateFragment = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        user = new Employee();
        user.setId(1);
        user.setRole(3);
        /*context = getApplicationContext();
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);*/
    }
}
