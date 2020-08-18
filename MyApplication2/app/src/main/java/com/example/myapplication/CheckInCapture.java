package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

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

public class CheckInCapture extends AppCompatActivity {
    Camera camera;
    FrameLayout frameLayout;
    CameraCustom cameraCustom;
    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {
            String fileName = System.currentTimeMillis() + ".jpg";

            try {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                ByteArrayOutputStream blob = new ByteArrayOutputStream();

                FileOutputStream out = openFileOutput(fileName, MODE_PRIVATE);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                Toast.makeText(getApplicationContext(), "image saved", Toast.LENGTH_SHORT).show();
                out.flush();
                out.close();
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

/*    private File getOutputMediaFile() {
        String state = Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED)){
            return null;
        } else {
            File folder_gui = new File(Environment.getExternalStorageDirectory() + File.separator + "GUI");
            if(!folder_gui.exists()){
                folder_gui.mkdir();

            }
            File outputfile = new File(folder_gui, "temp.jpg");
            return outputfile;
        }

    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}