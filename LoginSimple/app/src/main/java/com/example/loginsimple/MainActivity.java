package com.example.loginsimple;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.loginsimple.entity.Database;

public class MainActivity extends AppCompatActivity {
    EditText name;
    EditText pwd;
    Button btnLogin;
    TextView status;
    final String DATABASE_NAME = "EmployeeDB.sqlite";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (EditText) findViewById(R.id.edt_name);
        pwd = (EditText) findViewById(R.id.edt_pwd);
        btnLogin = (Button) findViewById(R.id.btn_login);
        status = (TextView) findViewById(R.id.status);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation(name.getText().toString(), pwd.getText().toString());
            }
        });
    }

    private void validation(String username, String password){
        SQLiteDatabase db = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = db.rawQuery("SELECT * FROM NhanVien WHERE username = ? and Pass = ?", new String[]{username, password});
        if (cursor.getCount() != 1){
            this.status.setVisibility(View.VISIBLE);
        } else {
            this.status.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(this, home.class);
            startActivity(intent);
        }


    }
}