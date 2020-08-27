package entity_class;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class AdapterMedia extends BaseAdapter {
    Activity context;
    ArrayList<MediaDetails> list;

    public AdapterMedia(Activity context, ArrayList<MediaDetails> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
        View row = inflater.inflate(R.layout.list_image_checkin, null);
        ImageView imgCheckin = row.findViewById(R.id.image_checkin);
        TextView imageName = row.findViewById(R.id.image_name);
        TextView datetime = row.findViewById(R.id.datetime);
        Button btnDelete = row.findViewById(R.id.btn_delete);

        MediaDetails mediaDetails = list.get(i);
        imageName.setText(mediaDetails.getImageLink());
        datetime.setText(mediaDetails.getDatetime());

        if (mediaDetails.getImageLink() != ""){
            try {
                File f = new File(context.getFilesDir(),mediaDetails.getImageLink());
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                imgCheckin.setImageBitmap(b);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        return null;
    }
}
