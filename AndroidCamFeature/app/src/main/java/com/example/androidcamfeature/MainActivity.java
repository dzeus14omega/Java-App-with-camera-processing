package com.example.androidcamfeature;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private MediaRecorder mMediaRecorder;
    private Camera mCamera;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mHolder;
    private View mToggleButton;
    private boolean mInitSuccesful;

    private String pathFile;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_recorder_recipe);

        // we shall take the video in landscape orientation
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        mHolder = mSurfaceView.getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        mToggleButton = (ToggleButton) findViewById(R.id.toggleRecordingButton);
        mToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            // toggle video recording
            public void onClick(View v) {
                if (((ToggleButton)v).isChecked()) {
                    mMediaRecorder.start();
                    try {
                        Thread.sleep(10 * 1000); // This will recode for 10 seconds, if you don't want then just remove it.
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finish();
                }
                else {
                    mMediaRecorder.stop();
                    mMediaRecorder.reset();
                    try {
                        initRecorder(mHolder.getSurface());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    /* Init the MediaRecorder, the order the methods are called is vital to
     * its correct functioning */
    private void initRecorder(Surface surface) throws IOException {
        // It is very important to unlock the camera before doing setCamera
        // or it will results in a black preview
        if(mCamera == null) {
            mCamera = Camera.open();
            mCamera.unlock();
        }

        if(mMediaRecorder == null)  mMediaRecorder = new MediaRecorder();
        mMediaRecorder.setPreviewDisplay(surface);
        mMediaRecorder.setCamera(mCamera);

        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
        //       mMediaRecorder.setOutputFormat(8);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        mMediaRecorder.setVideoEncodingBitRate(512 * 1000);
        mMediaRecorder.setVideoFrameRate(30);
        mMediaRecorder.setVideoSize(640, 480);

        pathFile = getFilesDir()+ "/"+ System.currentTimeMillis() +".mp4";
        mMediaRecorder.setOutputFile(pathFile);
        Toast.makeText(this, pathFile,Toast.LENGTH_LONG).show();
        try {
            mMediaRecorder.prepare();
        } catch (IllegalStateException e) {
            // This is thrown if the previous calls are not called with the
            // proper order
            e.printStackTrace();
        }

        mInitSuccesful = true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            if(!mInitSuccesful)
                initRecorder(mHolder.getSurface());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        shutdown();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    private void shutdown() {
        // Release MediaRecorder and especially the Camera as it's a shared
        // object that can be used by other applications
        mMediaRecorder.reset();
        mMediaRecorder.release();
        mCamera.release();

        // once the objects have been released they can't be reused
        mMediaRecorder = null;
        mCamera = null;
    }
}