package com.ridingmate.app.util.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.ridingmate.app.R;;

import java.util.ArrayList;


public class MaintenanceAdpter extends RecyclerView.Adapter<MaintenanceAdpter.CustomViewHolder> {
    private ArrayList<MaintenanceDAO> arrayList;

    public MaintenanceAdpter(ArrayList<MaintenanceDAO> arrayList){
        this.arrayList=arrayList;
    }
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_maintenance_list,parent,false);

        CustomViewHolder holder=new CustomViewHolder(view);


        return holder;
    }

    public void onBindViewHolder(MaintenanceAdpter.CustomViewHolder holder, int position) {

        holder.item_maintenance.setText(arrayList.get(position).getItem_maintenance());
        holder.item_maintenance_date.setText(arrayList.get(position).getItem_maintenance_date());
        holder.item_maintenance_location.setText(arrayList.get(position).getItem_maintenance_location());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(v -> {
            String title= holder.item_maintenance.getText().toString();
            Toast.makeText(v.getContext(),title,Toast.LENGTH_SHORT).show();
        });
    }

    public int getItemCount() {
        return (null != arrayList ? arrayList.size():0);
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
