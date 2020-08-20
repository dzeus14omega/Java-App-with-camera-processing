package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import entity_class.CameraCustom;
import entity_class.Database;

public class CheckInCapture extends AppCompatActivity {
    final String DATABASE_NAME = "EmployeeDB.sqlite";
    Camera camera;
    FrameLayout frameLayout;
    CameraCustom cameraCustom;
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

            //File imageCapture = getOutputMediaFile();

            /*if (imageCapture == null){
                return;
            }else {
                try {
                    FileOutputStream fos = new FileOutputStream(imageCapture);
                    fos.flush();
                    fos.close();

                    camera.startPreview();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }*/
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.activity_check_in_capture);

        frameLayout = (FrameLayout) findViewById(R.id.frame_capture);
        camera = Camera.open();
        cameraCustom = new CameraCustom(this, camera);
        frameLayout.addView(cameraCustom);


    }



    public void captureImage(View view){
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