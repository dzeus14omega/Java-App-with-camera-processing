package com.example.myapplication;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import entity_class.AdapterCheckinResult;
import entity_class.Database;
import entity_class.EmployeeCheckinResult;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link homebase#newInstance} factory method to
 * create an instance of this fragment.
 */
public class homebase extends Fragment {
    ArrayList<EmployeeCheckinResult> list;
    final String DATABASE_NAME = "EmployeeDB.sqlite";
    AdapterCheckinResult adapterCheckinResult;
    String querryDB;

    ImageButton btnFilter;
    EditText datePicker;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public homebase() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment homebase.
     */
    // TODO: Rename and change types and number of parameters
    public static homebase newInstance(String param1, String param2) {
        homebase fragment = new homebase();
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
        MyApp.stateFragment =1;
        View view = inflater.inflate(R.layout.fragment_homebase, container, false);

        datePicker = (EditText) view.findViewById(R.id.date_picker);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(calendar.getTime());
        datePicker.setText(date);
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseDate();
            }
        });

        btnFilter = (ImageButton) view.findViewById(R.id.btn_filter);
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String time = datePicker.getText().toString();
                querryDB = "SELECT dd.id_employee, nv.Ten, dd.time, dd.video_link, dd.image_link, nv.Anh " +
                        "FROM DiemDanh dd LEFT join NhanVien nv ON nv.ID = dd.id_employee " +
                        "WHERE date(dd.time) = date('" + time + "') GROUP by dd.id_employee";
                readData();
            }
        });

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.listResult);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        querryDB = "SELECT dd.id_employee, nv.Ten, dd.time, dd.video_link, dd.image_link, nv.Anh FROM DiemDanh dd LEFT join NhanVien nv ON nv.ID = dd.id_employee GROUP by dd.id_employee Order by dd.time";
        adapterCheckinResult = new AdapterCheckinResult(getActivity(), list);
        recyclerView.setAdapter(adapterCheckinResult);
        readData();

        return view;
    }

    private void chooseDate(){
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this.getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker _datePicker, int i, int i1, int i2) {
                calendar.set(i, i1, i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                datePicker.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, year,month,day);
        datePickerDialog.show();
    }

    private void readData(){
        SQLiteDatabase database = Database.initDatabase(getActivity(), DATABASE_NAME);
        Cursor cursor = database.rawQuery(querryDB, null);
        list.clear();
        //Toast.makeText(this.getActivity(), cursor.getCount()+"", Toast.LENGTH_SHORT).show();

        for (int i=0 ;i< cursor.getCount();i++){
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String datetime = cursor.getString(2);
            String videoLink = cursor.getString(3);
            String imageLink = cursor.getString(4);
            byte[] img_Ava = cursor.getBlob(5);
            list.add(new EmployeeCheckinResult(id, datetime, name, videoLink, imageLink, img_Ava));
        }

        adapterCheckinResult.notifyDataSetChanged();
    }
}