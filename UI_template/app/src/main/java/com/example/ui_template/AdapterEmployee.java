package com.example.ui_template;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterEmployee extends BaseAdapter {
    Context context;
    ArrayList<Employee> list;

    public AdapterEmployee(Context context, ArrayList<Employee> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();     // can chinh lai khi nhan vien qua nhieu, return ve top 10-20 element cua array
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.listview_row, null);

        // Anh xa tu listview_row.xml
        ImageView imgAva = row.findViewById(R.id.img_avatar);
        TextView txtId = (TextView) row.findViewById(R.id.id);
        TextView txtName = (TextView) row.findViewById(R.id.name);
        TextView txtPhone_num = (TextView) row.findViewById(R.id.phone_num);
        Button btnDelete = (Button) row.findViewById(R.id.delete);
        Button btnModify = (Button) row.findViewById(R.id.modify);

        Employee employee = list.get(i);
        txtId.setText(employee.getId() + " ");
        txtName.setText(employee.getName() + " ");
        txtPhone_num.setText(employee.getPhone_num() + " ");

        Bitmap bm_imgAva = BitmapFactory.decodeByteArray(employee.getAvatar(), 0, employee.getAvatar().length);
        imgAva.setImageBitmap(bm_imgAva);

        return row;
    }



}
