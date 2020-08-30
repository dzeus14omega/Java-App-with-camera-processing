package entity_class;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
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

import com.example.myapplication.MyApp;
import com.example.myapplication.R;


import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class AdapterMedia extends RecyclerView.Adapter<AdapterMedia.ViewHolder> {
    Activity context;
    ArrayList<MediaDetails> list;


    public Activity getContext() {
        return context;
    }

    public void setContext(Activity context) {
        this.context = context;
    }

    public ArrayList<MediaDetails> getList() {
        return list;
    }

    public void setList(ArrayList<MediaDetails> list) {
        this.list = list;
    }

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

    public void removeItem(int position) {
        MediaDetails item = list.get(position);
        int id = item.getId_media();
        SQLiteDatabase db = Database.initDatabase(getContext(), MyApp.DATABASE_NAME);
        db.execSQL("DELETE FROM DiemDanh where id_attend = ?", new String[]{id+"",});

        list.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(MediaDetails item, int position) {
        list.add(position, item);
        int id = item.getId_media();
        int id_employee = item.getId_employee();
        String time = item.getDatetime();
        String image_link = item.getImageLink();
        SQLiteDatabase db = Database.initDatabase(getContext(), MyApp.DATABASE_NAME);
        db.execSQL("INSERT INTO DiemDanh (id_attend, id_employee, time, image_link) VALUES (?,?,?,?)", new String[]{id+"", id_employee+"", time, image_link});
        notifyItemInserted(position);
    }

}
