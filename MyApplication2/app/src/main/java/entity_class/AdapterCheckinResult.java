package entity_class;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.myapplication.R;
import java.util.ArrayList;


public class AdapterCheckinResult extends BaseAdapter {
    ArrayList<EmployeeCheckinResult> list;
    Activity context;

    public AdapterCheckinResult(ArrayList<EmployeeCheckinResult> list, Activity context) {
        this.list = list;
        this.context = context;
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
        View row = inflater.inflate(R.layout.list_result_checkin, null);

        ImageView avatar = row.findViewById(R.id.avatar);
        TextView txtName = (TextView) row.findViewById(R.id.employee_name);
        TextView datetime = (TextView) row.findViewById(R.id.datetime);

        ImageButton btn_details = (ImageButton) row.findViewById(R.id.btn_details);

        EmployeeCheckinResult employeeCheckinResult = list.get(i);
        txtName.setText(employeeCheckinResult.getEmployee_name() + " ");
        datetime.setText(employeeCheckinResult.getDatetime() + " ");

        Bitmap bm_ava = BitmapFactory.decodeByteArray(employeeCheckinResult.getAvatar(), 0, employeeCheckinResult.getAvatar().length);
        avatar.setImageBitmap(bm_ava);

        btn_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return row;
    }
}
