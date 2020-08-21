package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import entity_class.CameraCustom;
import entity_class.Database;
import entity_class.RecordingCustom;

public class CheckInCapture extends AppCompatActivity {
    final String DATABASE_NAME = "EmployeeDB.sqlite";
    Camera camera;
    FrameLayout frameLayout;
    CameraCustom cameraCustom;
    RecordingCustom recordingCustom;
    ImageButton btn_record;
    ImageButton btn_capture;
    Context context;

    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {
            String fileName = System.currentTimeMillis() + ".jpg";

            try {
                // try to store image in internal storage
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                ByteArrayOutputStream blob = new ByteArrayOutputStream();

                FileOutputStream out = openFileOutput(fileName, MODE_PRIVATE);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                Toast.makeText(getApplicationContext(), "image saved", Toast.LENGTH_SHORT).show();
                out.flush();
                out.close();
                // store image path to sqlite-database if image file store successful
                insert(fileName);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.activity_check_in_capture);
        context = this;
        frameLayout = (FrameLayout) findViewById(R.id.frame_capture);
        camera = Camera.open();
        cameraCustom = new CameraCustom(this, camera);


        frameLayout.addView(cameraCustom);

        btn_capture = (ImageButton) findViewById(R.id.imageButton);
        btn_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureImage();
            }
        });

        btn_record = (ImageButton) findViewById(R.id.record_video);
        /*btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frameLayout.removeAllViews();


                recordingCustom = new RecordingCustom(context,camera);
                frameLayout.addView(recordingCustom);
            }
        });*/


    }



    public void captureImage(){
        if (camera != null){
            camera.takePicture(null,null, mPictureCallback);
        }
    }

    private void insert(String filename){
        SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
        String insertQuerry = "INSERT INTO DiemDanh (id_employee, time, image_link) VALUES ("+ MyApp.user.getId()+", datetime('now', 'localtime'), '"+ filename +"') ";
        database.execSQL(insertQuerry);
        Toast.makeText(this,insertQuerry,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, Popup_ComfirmCheckIn.class);
        this.finish();
        //intent.putExtra("datetime", )
        startActivity(intent);
    }


}