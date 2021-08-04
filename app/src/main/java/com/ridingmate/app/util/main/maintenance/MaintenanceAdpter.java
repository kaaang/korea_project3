package com.ridingmate.app.util.main.maintenance;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.ridingmate.app.R;
import com.ridingmate.app.activity.main.MainActivity;
import com.ridingmate.app.fragment.main.Main_maintenance_list;
import com.ridingmate.app.fragment.main.Main_maintenance_regist;
import com.ridingmate.app.util.main.FireBaseInterface;;

import java.text.BreakIterator;
import java.util.ArrayList;

import static com.ridingmate.app.fragment.main.Main_maintenance_regist.selected_date;


public class MaintenanceAdpter extends RecyclerView.Adapter<MaintenanceAdpter.CustomViewHolder> {

    // 수정 삭제
    private ArrayList<Maintenance> mArrayList;
    private Context context;
    private CustomViewHolder holder;
    private int view_id;

    // Main에  접근
    MainActivity mainActivity= (MainActivity) MainActivity._main;

    public MaintenanceAdpter() {
        mArrayList = Main_maintenance_list.arrayList;
    }

    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_maintenance_list, parent, false);
        holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MaintenanceAdpter.CustomViewHolder holder, int position) {
        holder.item_maintenance.setText(mArrayList.get(position).getItem_maintenance());
        holder.item_maintenance_date.setText(mArrayList.get(position).getItem_maintenance_date());
        holder.item_maintenance_location.setText(mArrayList.get(position).getItem_maintenance_location());
        holder.item_maintenance_detail.setText(mArrayList.get(position).getItem_maintenance_detail());

        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Main_maintenance_regist.check_actiive) {
                    MaintenanceConstants.m_interface.Maintenace_ChangeTextView(position, true);
                } else {
                    Main_maintenance_regist.check_edit = true;
                }
                MainActivity.showPage(1);
            }
        });
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity._main);
                builder.setTitle("정비 기록 삭제");
                builder.setMessage("선택 기록을 삭제하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String str=mArrayList.get(position).getDocument_ID();
                        FireBaseInterface.m_interface.deleteMaintenanceData(str);
                        Main_maintenance_list.arrayList.clear();
                        FireBaseInterface.m_interface.downloadMaintenanceData(selected_date,mainActivity.selectedBikeUid);
                        MainActivity.showPage(0);
                    }
                });
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.showPage(0);
                    }
                });
                builder.create().show();
            }
        });
    }

    public int getItemCount() {
        return (null != mArrayList ? mArrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView item_maintenance;
        protected TextView item_maintenance_date;
        protected TextView item_maintenance_location;
        protected TextView item_maintenance_detail;
        protected TextView btn_edit;
        protected TextView btn_delete;

        public CustomViewHolder(View view) {
            super(view);
            this.item_maintenance = (TextView) view.findViewById(R.id.item_maintenance);
            this.item_maintenance_date = (TextView) view.findViewById(R.id.item_maintenance_date);
            this.item_maintenance_location = (TextView) view.findViewById(R.id.item_maintenance_location);
            this.item_maintenance_detail = (TextView) view.findViewById(R.id.item_maintenance_detail);
            this.btn_edit= (TextView)view.findViewById(R.id.btn_maintenance_edite);
            this.btn_delete= (TextView)view.findViewById(R.id.btn_maintenance_delete);
        }
    }
}
