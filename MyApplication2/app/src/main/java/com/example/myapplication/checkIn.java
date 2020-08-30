package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import entity_class.AttendanceInfo;
import entity_class.Database;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link checkIn#newInstance} factory method to
 * create an instance of this fragment.
 */
public class checkIn extends Fragment {
    Button btn_checkIn;
    TextView message;
    TextView lastcheckin;
    ConstraintLayout bgStatus;

    final String DATABASE_NAME = "EmployeeDB.sqlite";
    SQLiteDatabase database;
    ArrayList<AttendanceInfo> list;
    private int CAMERA_PERMISSION_CODE = 1;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public checkIn() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment checkIn.
     */
    // TODO: Rename and change types and number of parameters
    public static checkIn newInstance(String param1, String param2) {
        checkIn fragment = new checkIn();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MyApp.stateFragment = 3;
        View view = inflater.inflate(R.layout.fragment_check_in, container, false);


        addControls(view);
        readData();

        return view;
    }



    private void addControls(View view) {
        message = view.findViewById(R.id.message);
        list = new ArrayList<>();

        bgStatus = (ConstraintLayout) view.findViewById(R.id.bg_status);
        lastcheckin = (TextView) view.findViewById(R.id.last_checkin);

        btn_checkIn = view.findViewById(R.id.btn_checkin);
        btn_checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(getActivity(), CheckInCapture.class);
                    startActivity(intent);
                    //Log.d("my tag", "click");
                } else {
                    requestCameraPermission();
                }
            }
        });
    }

    private void readData(){
        database = Database.initDatabase(getActivity(), DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM DiemDanh WHERE id_employee = " + MyApp.user.getId()+ " limit 20", null);
        list.clear();
        for (int i=0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            int id_employee = cursor.getInt(1);
            String datetime = cursor.getString(2);
            String linkImage = cursor.getString(3);
            String linkVideo = cursor.getString(4);
            list.add(new AttendanceInfo(id_employee, datetime, linkImage, linkVideo));
        }

        // render screen again while finish read data
        //adapterEmployee.notifyDataSetChanged();
        if (list.isEmpty()){

        } else {
            lastcheckin.setVisibility(View.VISIBLE);
            bgStatus.setBackgroundResource(R.drawable.bg_status_checkin);
            //bgStatus.setBackground(R.drawable.bg_status_checkin);
            AttendanceInfo attendanceInfo = list.get(list.size()-1);
            message.setText(attendanceInfo.getDatetime());
        }
    }


    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);

    }
}




//in requestCameraPermission()
/*if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)){
            new AlertDialog.Builder(getActivity()).setTitle("Yeu cau cap quyen").setMessage("This permission is needed because of using camera").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                }
            }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.dismiss();
                }
            }).create().show();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        }*/