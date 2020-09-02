package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import entity_class.Database;
import entity_class.Employee;


public class UpdateActivity extends AppCompatActivity {
    final String DATABASE_NAME = "EmployeeDB.sqlite";
    final int REQUEST_TAKE_PHOTO = 123;
    final int REQUEST_CHOOSE_PHOTO = 321;

    int id = -1 ;

    ImageButton btnSelectPhoto;
    ImageButton btnTakePhoto;
    Button btnSave;
    Button btnCancel;
    EditText editName;
    EditText editPhoneNum;
    ImageView imgAva;
    EditText editUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        addControls();
        addEvents();
        initUI();
    }

    //this function to update employee's info and push again through UI
    private void initUI() {
        Intent intent = getIntent();
        id = intent.getIntExtra("ID", -1);
        SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT ID, Ten, SDT, Anh, username FROM NhanVien WHERE id = ?", new String[]{id + "",});
        cursor.moveToFirst();
        String name = cursor.getString(1);
        String phone_num = cursor.getString(2);
        byte[] img = cursor.getBlob(3);
        String username = cursor.getString(4);

        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        imgAva.setImageBitmap(bitmap);
        editPhoneNum.setText(phone_num);
        editName.setText(name);
        editUsername.setText(username);
    }

    private void addControls() {
        btnSelectPhoto = (ImageButton) findViewById(R.id.btnSelectPhoto);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnTakePhoto = (ImageButton) findViewById(R.id.btn_takePhoto);
        editName = (EditText) findViewById(R.id.editName);
        editPhoneNum = (EditText) findViewById(R.id.editPhone);
        imgAva = (ImageView) findViewById(R.id.img_avatar);
        editUsername = (EditText) findViewById(R.id.edtUsername);
    }

    private void takePicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
    }
    private void choosePhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CHOOSE_PHOTO);
    }

    private void addEvents(){
        btnSelectPhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                choosePhoto();
            }

        });
        btnTakePhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                takePicture();
            }

        });
        btnSave.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                update();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                cancel();
            }
        });
    }

    private void update(){
        String ten = editName.getText().toString();
        String sdt = editPhoneNum.getText().toString();
        byte[] anh = getByteArrayFromImageView(imgAva);
        String username = editUsername.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put("Ten", ten);
        contentValues.put("SDT", sdt);
        contentValues.put("Anh", anh);
        contentValues.put("username",username);

        SQLiteDatabase database = Database.initDatabase(this, "EmployeeDB.sqlite");
        database.update("NhanVien", contentValues, "id = ?", new String[] {id + ""});

        if(id == MyApp.user.getId()){
            int role = MyApp.user.getRole();
            String pass = MyApp.user.getPass();
            MyApp.user = new Employee(id, ten, sdt, anh, pass,role, username);
        }

        finish();


    }

    private void cancel(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private byte[] getByteArrayFromImageView(ImageView imgv){

        BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
        Bitmap bmp = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CHOOSE_PHOTO) {
                try {
                    Uri imageUri = data.getData();
                    InputStream is = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    imgAva.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_TAKE_PHOTO) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imgAva.setImageBitmap(bitmap);
            }
        }
    }
}