package com.example.ui_template;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    Activity context;
    ArrayList<Employee> list;

    public AdapterEmployee(Activity context, ArrayList<Employee> list) {
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

        final Employee employee = list.get(i);
        txtId.setText(employee.getId() + " ");
        txtName.setText(employee.getName() + " ");
        txtPhone_num.setText(employee.getPhone_num() + " ");

        Bitmap bm_imgAva = BitmapFactory.decodeByteArray(employee.getAvatar(), 0, employee.getAvatar().length);
        imgAva.setImageBitmap(bm_imgAva);

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("ID", employee.getId());
                context.startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setTitle("Delete confirm!");
                builder.setMessage("Do you want to permanently delete this employee?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        delete(employee.getId());
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        return row;
    }

    private void delete(int idEmployee) {
        SQLiteDatabase database = Database.initDatabase(context, "EmployeeDB.sqlite");
        database.delete("NhanVien", "ID = ?", new String[]{idEmployee + ""});
        list.clear();
        Cursor cursor = database.rawQuery("SELECT * FROM NhanVien", null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String ten = cursor.getString(1);
            String sdt = cursor.getString(2);
            byte[] anh = cursor.getBlob(3);

            list.add(new Employee(id, ten, sdt, anh));
        }
        notifyDataSetChanged();
    }


}
