package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import entity_class.AdapterEmployee;
import entity_class.Database;
import entity_class.Employee;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link basic_feature#newInstance} factory method to
 * create an instance of this fragment.
 */
public class basic_feature extends Fragment {
    final String DATABASE_NAME = "EmployeeDB.sqlite";
    SQLiteDatabase database;

    ListView listView;
    ArrayList<Employee> list;
    AdapterEmployee adapterEmployee;
    Button btnAdd;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public basic_feature() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment basic_feature.
     */
    // TODO: Rename and change types and number of parameters
    public static basic_feature newInstance(String param1, String param2) {
        basic_feature fragment = new basic_feature();
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
        MyApp.stateFragment =2;
        View view = inflater.inflate(R.layout.fragment_basic_feature, container, false);

        addControls(view);
        readData();

        return view;
    }
    private void addControls(View view){
        listView = (ListView) view.findViewById(R.id.listView);
        list = new ArrayList<>();
        adapterEmployee = new AdapterEmployee(getActivity(), list);
        listView.setAdapter(adapterEmployee);
        btnAdd = (Button) view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddActivity.class);
                startActivity(intent);
                //Log.d("my tag", "click");
            }
        });

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("my tag", "click");
            }
        });*/
    }
    private void readData(){
        database = Database.initDatabase(getActivity(), DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM NhanVien", null);
        list.clear();
        for (int i=0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String phone_num = cursor.getString(2);
            byte[] img_Ava = cursor.getBlob(3);
            list.add(new Employee(id, name, phone_num, img_Ava));
        }

        // render screen again while finish read data
        adapterEmployee.notifyDataSetChanged();
    }


}