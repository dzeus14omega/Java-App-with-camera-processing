package com.example.opencvcppfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StartPage extends AppCompatActivity {
    Button btn_threshold;
    Button btn_faceDetect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        btn_faceDetect = (Button) findViewById(R.id.btn_faceDetect);
        btn_threshold = (Button) findViewById(R.id.btn_threshold);

        btn_threshold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        btn_faceDetect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FaceDetection.class);
                startActivity(intent);
            }
        });


    }
}