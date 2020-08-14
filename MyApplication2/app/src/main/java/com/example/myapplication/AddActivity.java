package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
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
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import entity_class.Database;

public class AddActivity extends AppCompatActivity {
    final String DATABASE_NAME = "EmployeeDB.sqlite";
    final int REQUEST_TAKE_PHOTO = 123;
    final int REQUEST_CHOOSE_PHOTO = 321;

    ImageButton btnSelectPhoto;
    ImageButton btnTakePhoto;
    Button btnSave;
    Button btnCancel;
    EditText editName;
    EditText editPhoneNum;
    ImageView imgAva;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        addControls();
        addEvents();
    }

    private void addControls() {
        btnSelectPhoto = (ImageButton) findViewById(R.id.btnSelectPhoto);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnTakePhoto = (ImageButton) findViewById(R.id.btn_takePhoto);
        editName = (EditText) findViewById(R.id.editName);
        editPhoneNum = (EditText) findViewById(R.id.editPhone);
        imgAva = (ImageView) findViewById(R.id.img_avatar);

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
                insert();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                cancel();
            }
        });
    }

    private void insert(){
        String ten = editName.getText().toString();
        String sdt = editPhoneNum.getText().toString();
        byte[] anh = getByteArrayFromImageView(imgAva);
         if (ten.length() == 0){
             Toast.makeText(this, "Your name is null", Toast.LENGTH_SHORT).show();
             return;
         }
         if (sdt.length() == 0){
             Toast.makeText(this, "Your phone is null", Toast.LENGTH_SHORT).show();
             return;
         }


        ContentValues contentValues = new ContentValues();
        contentValues.put("Ten", ten);
        contentValues.put("SDT", sdt);
        contentValues.put("Anh", anh);

        SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
        database.insert("NhanVien",null, contentValues);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);


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
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream );
                    byte[] byteArray = stream.toByteArray();
                    bitmap = BitmapFactory.decodeByteArray(byteArray,0, byteArray.length);
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