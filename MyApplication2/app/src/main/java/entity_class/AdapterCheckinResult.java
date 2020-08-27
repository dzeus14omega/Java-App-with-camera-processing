package entity_class;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.CheckInDetails;
import com.example.myapplication.R;
import com.example.myapplication.UpdateActivity;

import java.util.ArrayList;


public class AdapterCheckinResult extends RecyclerView.Adapter<AdapterCheckinResult.ViewHolder> {
    ArrayList<EmployeeCheckinResult> list;
    Activity context;

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView avatar;
        TextView employeeName;
        TextView datetime;
        ImageButton btn_details;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            employeeName = (TextView) itemView.findViewById(R.id.employee_name);
            datetime = (TextView) itemView.findViewById(R.id.date);

            btn_details = (ImageButton) itemView.findViewById(R.id.btn_details);

        }
    }


    public AdapterCheckinResult(Activity context, ArrayList<EmployeeCheckinResult> list) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.list_result_checkin, parent, false);
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final EmployeeCheckinResult employeeCheckinResult = list.get(position);
        Bitmap bm_ava = BitmapFactory.decodeByteArray(employeeCheckinResult.getAvatar(), 0, employeeCheckinResult.getAvatar().length);

        holder.avatar.setImageBitmap(bm_ava);
        holder.employeeName.setText(list.get(position).getEmployee_name());
        holder.datetime.setText(list.get(position).getDatetime());

        holder.btn_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Activity view details ...
                Intent intent = new Intent(context, CheckInDetails.class);
                intent.putExtra("ID", employeeCheckinResult.getId());
                intent.putExtra("name", employeeCheckinResult.getEmployee_name());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
