package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.example.myapplication.eventAdvanced.SwipeToDeleteCallback;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import entity_class.AdapterMedia;
import entity_class.Database;
import entity_class.MediaDetails;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link imagesChecker#newInstance} factory method to
 * create an instance of this fragment.
 */
public class imagesChecker extends Fragment {
    final String DATABASE_NAME = "EmployeeDB.sqlite";
    SQLiteDatabase database;
    int id = -1;

    RecyclerView recyclerView;
    ArrayList<MediaDetails> list;
    AdapterMedia adapterMedia;
    FrameLayout frameLayout;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public imagesChecker() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment imagesChecker.
     */
    // TODO: Rename and change types and number of parameters
    public static imagesChecker newInstance(String param1, String param2) {
        imagesChecker fragment = new imagesChecker();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_images_checker, container, false);
        frameLayout = (FrameLayout) view.findViewById(R.id.imageChecker);

        Intent intent = getActivity().getIntent();
        id = intent.getIntExtra("ID",-1);

        recyclerView = (RecyclerView) view.findViewById(R.id.list_image);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        list = new ArrayList<>();
        adapterMedia = new AdapterMedia(getActivity(), list);
        recyclerView.setAdapter(adapterMedia);

        readData();

        enableSwipeToDeleteAndUndo();

        return view;

    }

    private void readData(){
        database = Database.initDatabase(getActivity(), DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM DiemDanh WHERE id_employee = ? ORDER BY time DESC", new String[]{id + "",});
        list.clear();
        Toast.makeText(getContext(), cursor.getCount()+ "", Toast.LENGTH_SHORT).show();
        for (int i=0;i< cursor.getCount();i++){
            cursor.moveToPosition(i);
            int id_attend = cursor.getInt(0);
            int id_employee = cursor.getInt(1);
            String datetime = cursor.getString(2);
            String videoLink = cursor.getString(3);
            String imageLink = cursor.getString(4);
            if(videoLink != null){

            } else if(imageLink != null){
                list.add(new MediaDetails(imageLink, videoLink, datetime, id_attend, id_employee));
            } else {
                continue;
            }

        }
        adapterMedia.notifyDataSetChanged();

    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getActivity()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                final MediaDetails item = adapterMedia.getList().get(position);

                adapterMedia.removeItem(position);


                Snackbar snackbar = Snackbar.make(frameLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        adapterMedia.restoreItem(item, position);
                        recyclerView.scrollToPosition(position);
                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }



}