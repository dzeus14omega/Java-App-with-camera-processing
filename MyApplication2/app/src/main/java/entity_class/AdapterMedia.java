package entity_class;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;


import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class AdapterMedia extends RecyclerView.Adapter<AdapterMedia.ViewHolder> {
    Activity context;
    ArrayList<MediaDetails> list;

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgCheckin;
        TextView imageName;
        TextView datetime;
        Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCheckin = (ImageView) itemView.findViewById(R.id.image_checkin);
            imageName = (TextView) itemView.findViewById(R.id.image_name);
            datetime = (TextView) itemView.findViewById(R.id.datetime);
            btnDelete = (Button) itemView.findViewById(R.id.btn_delete);

        }
    }

    public AdapterMedia(Activity context, ArrayList<MediaDetails> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.list_image_checkin, parent, false);
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MediaDetails mediaDetails = list.get(position);
        if (mediaDetails.getImageLink() != null){
            try {
                File f = new File(context.getFilesDir(),mediaDetails.getImageLink());
                int file_size = Integer.parseInt(String.valueOf(f.length()/1024));
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                holder.imgCheckin.setImageBitmap(b);
                Log.d("imageLoad", file_size + "");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        holder.datetime.setText(mediaDetails.getDatetime());
        holder.imageName.setText(mediaDetails.getImageLink());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /*@Override
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


        long startime = System.currentTimeMillis();
        if (mediaDetails.getImageLink() != null){
            try {

                File f = new File(context.getFilesDir(),mediaDetails.getImageLink());
                int file_size = Integer.parseInt(String.valueOf(f.length()/1024));
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                imgCheckin.setImageBitmap(b);
                Log.d("imageLoad", file_size + "");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        long endtime = System.currentTimeMillis();
        Log.d("duration", "getView: " + (endtime-startime));



        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        return row;
    }*/
}
