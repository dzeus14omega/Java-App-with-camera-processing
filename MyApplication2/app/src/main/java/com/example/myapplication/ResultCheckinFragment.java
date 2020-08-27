package com.example.myapplication;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

import entity_class.AdapterCheckinResult;
import entity_class.Database;
import entity_class.EmployeeCheckinResult;

/**
 * A fragment representing a list of Items.
 */
public class ResultCheckinFragment extends Fragment {
    ArrayList<EmployeeCheckinResult> list;
    final String DATABASE_NAME = "EmployeeDB.sqlite";
    SQLiteDatabase database;
    AdapterCheckinResult adapterCheckinResult;

    String querryDB;

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ResultCheckinFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ResultCheckinFragment newInstance(int columnCount) {
        ResultCheckinFragment fragment = new ResultCheckinFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_result_checkin_list, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        querryDB = "SELECT dd.id_employee, nv.Ten, dd.time, dd.video_link, dd.image_link, nv.Anh FROM DiemDanh dd LEFT join NhanVien nv ON nv.ID = dd.id_employee GROUP by dd.id_employee Order by dd.time";
        adapterCheckinResult = new AdapterCheckinResult(getActivity(), list);
        recyclerView.setAdapter(adapterCheckinResult);
        readData();

        return recyclerView;
    }

    private void readData(){
        database = Database.initDatabase(getActivity(), DATABASE_NAME);
        Cursor cursor = database.rawQuery(querryDB, null);
        list.clear();

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
        //Toast.makeText(getContext(), list.size() + " ", Toast.LENGTH_SHORT).show();
        adapterCheckinResult.notifyDataSetChanged();
    }
}