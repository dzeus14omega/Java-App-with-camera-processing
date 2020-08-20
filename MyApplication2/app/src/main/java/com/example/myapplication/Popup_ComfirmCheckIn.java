package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Popup_ComfirmCheckIn extends Activity {
    Button confirm;
    TextView datetime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup__comfirm_check_in);

        datetime = (TextView) findViewById(R.id.datetime);
        datetime.setText(getCurrentDateTime());

        confirm = (Button) findViewById(R.id.btn_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)width,(int)height);


    }

    public String getCurrentDateTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        String date = sdf.format(c.getTime());
        sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(c.getTime());
        String result = time+" ngay "+ date;
        return result;
    }

}