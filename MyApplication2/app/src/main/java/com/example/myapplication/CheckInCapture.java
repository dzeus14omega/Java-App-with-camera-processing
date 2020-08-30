package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;

import android.util.Log;
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

public class CheckInCapture extends AppCompatActivity {
    final String DATABASE_NAME = "EmployeeDB.sqlite";
    Camera camera;
    FrameLayout frameLayout;
    CameraCustom cameraCustom;
    ImageButton btn_record;
    ImageButton btn_capture;
    ImageButton btn_back;

    private MediaRecorder mediaRecorder;
    private boolean isRecording = false;
    String pathFileVideo;

    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {
            String fileName = System.currentTimeMillis() + ".jpg";

            try {
                // try to store image in internal storage
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                ByteArrayOutputStream blob = new ByteArrayOutputStream();

                FileOutputStream out = openFileOutput(fileName, MODE_PRIVATE);

                bitmap= Bitmap.createScaledBitmap(bitmap, 300, 400, false);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 40, out);
                Toast.makeText(getApplicationContext(), "image saved", Toast.LENGTH_SHORT).show();
                out.flush();
                out.close();
                // store image path to sqlite-database if image file store successful
                insert(fileName, 1);


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
        addControl();


    }

    public void addControl(){
        frameLayout = (FrameLayout) findViewById(R.id.frame_capture);
        camera = getCameraInstance();
        mediaRecorder = new MediaRecorder();
        // Create our Preview view and set it as the content of our activity.
        cameraCustom = new CameraCustom(this, camera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.frame_capture);
        preview.addView(cameraCustom);

        btn_back = (ImageButton) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //releaseCamera();
                finish();
            }
        });
        btn_capture = (ImageButton) findViewById(R.id.imageButton);
        btn_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureImage();
            }
        });

        btn_record = (ImageButton) findViewById(R.id.record_video);
        btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRecording) {
                    // stop recording and release camera
                    mediaRecorder.stop();  // stop the recording
                    releaseMediaRecorder(); // release the MediaRecorder object
                    camera.lock();         // take camera access back from MediaRecorder

                    // inform the user that recording has stopped
                    //setCaptureButtonText("Record");
                    setImageButton(R.drawable.ic_baseline_videocam_24);
                    isRecording = false;
                    insert(pathFileVideo, 2);
                    btn_capture.setVisibility(View.VISIBLE);
                } else {
                    btn_capture.setVisibility(View.INVISIBLE);
                    // initialize video camera
                    if (prepareVideoRecorder()) {
                        // Camera is available and unlocked, MediaRecorder is prepared,
                        // now you can start recording

                        mediaRecorder.start();

                        // inform the user that recording has started
                        //setCaptureButtonText("Stop");
                        setImageButton(R.drawable.ic_recording);
                        isRecording = true;
                    } else {
                        // prepare didn't work, release the camera
                        releaseMediaRecorder();
                        // inform user
                        btn_capture.setVisibility(View.VISIBLE);
                    }
                }
            }
        });


        // Create our Preview view and set it as the content of our activity.

    }

    private void setImageButton(int icon) {
        this.btn_record.setImageResource(icon);
    }


    public void captureImage(){
        if (camera != null){
            camera.takePicture(null,null, mPictureCallback);
        }
    }

    private void insert(String filename, int type){
        // type: 1-image; 2-video
        if (type ==1){
            SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
            String insertQuerry = "INSERT INTO DiemDanh (id_employee, time, image_link) VALUES ("+ MyApp.user.getId()+", datetime('now', 'localtime'), '"+ filename +"') ";
            database.execSQL(insertQuerry);
            Toast.makeText(this,insertQuerry,Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, Popup_ComfirmCheckIn.class);
            this.finish();
            //intent.putExtra("datetime", )
            startActivity(intent);
        } else {
            SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
            String insertQuerry = "INSERT INTO DiemDanh (id_employee, time, video_link) VALUES ("+ MyApp.user.getId()+", datetime('now', 'localtime'), '"+ filename +"') ";
            database.execSQL(insertQuerry);
            Toast.makeText(this,insertQuerry,Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, Popup_ComfirmCheckIn.class);
            this.finish();
            //intent.putExtra("datetime", )
            startActivity(intent);
        }

    }



    private boolean prepareVideoRecorder(){
        camera = getCameraInstance();
        mediaRecorder = new MediaRecorder();
        camera.setDisplayOrientation(90);
        // Step 1: Unlock and set camera to MediaRecorder
        camera.unlock();
        mediaRecorder.setCamera(camera);

        // Step 2: Set sources
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        // Step 3: Set a CamcorderProfile (requires API Level 8 or higher)
        mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));

        // Step 4: Set output file
        pathFileVideo = getFilesDir()+ "/"+ System.currentTimeMillis() +".mp4";
        mediaRecorder.setOutputFile(pathFileVideo);
        Toast.makeText(this, pathFileVideo,Toast.LENGTH_LONG).show();

        // Set max duration 60 sec.
        mediaRecorder.setMaxDuration(60000);
        mediaRecorder.setMaxFileSize(50000000);

        // Step 5: Set the preview output
        mediaRecorder.setPreviewDisplay(cameraCustom.getHolder().getSurface());
        mediaRecorder.setOrientationHint(90);
        // Step 6: Prepare configured MediaRecorder
        try {
            mediaRecorder.prepare();
        } catch (IllegalStateException e) {
            Log.d("videoRecord", "IllegalStateException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        } catch (IOException e) {
            Log.d("videoRecord", "IOException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        }
        return true;
    }

    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    private void releaseMediaRecorder(){
        if (mediaRecorder != null) {
            mediaRecorder.reset();   // clear recorder configuration
            mediaRecorder.release(); // release the recorder object
            mediaRecorder = new MediaRecorder();
            camera.lock();           // lock camera for later use
        }
    }

    private void releaseCamera(){
        if (camera != null){
            camera.release();        // release the camera for other applications
            camera = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaRecorder();       // if you are using MediaRecorder, release it first
        releaseCamera();              // release the camera immediately on pause event
    }
}