package com.example.myapplication;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import java.io.File;

import entity_class.Employee;

public class MyApp extends Application {
    public static Employee user;
    public static Context context;
    public static String DATABASE_NAME = "EmployeeDB.sqlite";
    public static int stateFragment = 1;
    public static String pathFile;

    @Override
    public void onCreate() {
        super.onCreate();
        //user = new Employee();
        pathFile = getFilesDir() + "/";
        /*context = getApplicationContext();
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);*/
    }
}
