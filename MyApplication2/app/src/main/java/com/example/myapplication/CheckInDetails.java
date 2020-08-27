package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import entity_class.AdapterEmployee;
import entity_class.AdapterMedia;
import entity_class.Database;
import entity_class.Employee;
import entity_class.MediaDetails;

public class CheckInDetails extends AppCompatActivity {
    final String DATABASE_NAME = "EmployeeDB.sqlite";
    SQLiteDatabase database;
    ImageButton btn_back;
    TextView employeeName;
    int id = -1;

    GridView listView;
    ArrayList<MediaDetails> list;
    AdapterMedia adapterMedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in_details);


        Intent intent = getIntent();
        employeeName = (TextView) findViewById(R.id.employee_name);
        employeeName.setText(intent.getStringExtra("name"));

        id = intent.getIntExtra("ID",-1);

        btn_back = (ImageButton) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        listView = (GridView) findViewById(R.id.listMediaImage);
        list = new ArrayList<>();
        adapterMedia = new AdapterMedia(this, list);
        listView.setAdapter(adapterMedia);

        readData();
    }

    private void readData(){
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM DiemDanh WHERE id_employee = ?", new String[]{id + "",});
        list.clear();
        for (int i=0;i< cursor.getCount();i++){
            cursor.moveToPosition(i);
            int id_attend = cursor.getInt(0);
            int id_employee = cursor.getInt(1);
            String datetime = cursor.getString(2);
            String videoLink = cursor.getString(3);
            String imageLink = cursor.getString(4);
            if(videoLink != null){

            } else if(imageLink != null){
                list.add(new MediaDetails(imageLink,videoLink,datetime,id_attend));
            } else {
                continue;
            }

        }
        adapterMedia.notifyDataSetChanged();

    }
}