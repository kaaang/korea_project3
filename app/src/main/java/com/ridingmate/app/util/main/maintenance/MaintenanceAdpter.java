package com.ridingmate.app.util.main.maintenance;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.ridingmate.app.R;
import com.ridingmate.app.activity.main.MainActivity;
import com.ridingmate.app.fragment.main.Main_maintenance_regist;;

import java.util.ArrayList;


public class MaintenanceAdpter extends RecyclerView.Adapter<MaintenanceAdpter.CustomViewHolder> {

    // 수정 삭제
    private TextView btn_edit;
    private  ArrayList<MaintenanceDAO> mArrayList;
    private  CustomViewHolder holder;
    public MaintenanceAdpter(ArrayList<MaintenanceDAO> arrayList){
        mArrayList = arrayList;
        MaintenanceConstants.m_interface.DAO_arrayList(arrayList);
    }
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_maintenance_list,parent,false);
        holder=new CustomViewHolder(view);
        btn_edit= (TextView)view.findViewById(R.id.btn_maintenance_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.showPage(1);
               int id = holder.getBindingAdapterPosition(); // 각 테이블의 id얻기
               Log.e("Test", "" + id );
                if(Main_maintenance_regist.check_actiive){
                    MaintenanceConstants.m_interface.Maintenace_ChangeTextView(id, true);
                }
                else {
                    Main_maintenance_regist.check_edit = true;
                }
            }
        });




        return holder;
    }

    public void onBindViewHolder(MaintenanceAdpter.CustomViewHolder holder, int position) {

        holder.item_maintenance.setText(mArrayList.get(position).getItem_maintenance());
        holder.item_maintenance_date.setText(mArrayList.get(position).getItem_maintenance_date());
        holder.item_maintenance_location.setText(mArrayList.get(position).getItem_maintenance_location());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(v -> {
            String title= holder.item_maintenance.getText().toString();
            Toast.makeText(v.getContext(),title,Toast.LENGTH_SHORT).show();
        });
    }

    public int getItemCount() {
        return (null != mArrayList ? mArrayList.size():0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        public TextView item_maintenance;
        public TextView item_maintenance_date;
        public TextView item_maintenance_location;

        public CustomViewHolder(View view) {
            super(view);

            this.item_maintenance = (TextView)view.findViewById(R.id.item_maintenance);
            this.item_maintenance_date = (TextView)view.findViewById(R.id.item_maintenance_date);
            this.item_maintenance_location = (TextView)view.findViewById(R.id.item_maintenance_location);
        }
    }
}
