package com.example.myapplication;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Popup_ComfirmCheckIn extends Activity {
    Button confirm;
    ImageButton btn_back;
    TextView date;
    TextView time;
    TextView user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_popup__comfirm_check_in);

        user = (TextView) findViewById(R.id.employee_name);
        user.setText(MyApp.user.getName());

        date = (TextView) findViewById(R.id.date);
        time = (TextView) findViewById(R.id.time);

        date.setText(getCurrentDate());
        time.setText(getCurrentTime());

        confirm = (Button) findViewById(R.id.btn_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_back = (ImageButton) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
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

    public String getCurrentDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        String date = sdf.format(c.getTime());
        return date;
    }
    public String getCurrentTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Calendar c = Calendar.getInstance();
        String time = sdf.format(c.getTime());
        return time;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradiant(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.drawable.bg_header);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(background);
        }
    }
}