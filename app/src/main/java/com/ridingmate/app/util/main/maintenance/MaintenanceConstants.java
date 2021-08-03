package com.ridingmate.app.util.main.maintenance;

import android.widget.TextView;

import com.ridingmate.app.util.main.FireBaseInterface;

import java.util.ArrayList;

public class MaintenanceConstants {


    public  static  Maintenaceinterface m_interface =  new MaintenanceController();

    // Maintenance UI 인터페이스 수정 파트
    private static class MaintenanceController implements Maintenaceinterface{
        private ArrayList<MaintenanceDAO> arrayList;
        private TextView date;
        private TextView  list;
        private TextView location;
        private TextView regist;
        private TextView detail;
        private String celDate;
        public  ArrayList<MaintenanceDAO> DAO_arrayList(ArrayList<MaintenanceDAO> list){
            arrayList = list;
            return list;
        }
        public TextView Tv_maintenance_date(TextView view){
            date = view;
            return view;
        }
        public TextView Tv_maintenance_list(TextView view){
            list = view;
            return view;
        }
        public TextView Tv_maintenance_location(TextView view){
            location = view;
            return view;
        }
        public TextView Tv_maintenance_regist(TextView view){
            regist = view;
            return view;
        }
        public TextView Tv_maintenance_detail(TextView view){
            detail = view;
            return view;
        }
        public  String Cel_Data(String str){
            celDate = str;
            return str;
        }
        // Textview 수정
        public void Maintenace_ChangeTextView(int id, boolean check) {
            if (check) {
                MaintenanceDAO dao = arrayList.get(id);
                //현제 데이터 가져오기
                list.setText(dao.getItem_maintenance());
                date.setText(dao.getItem_maintenance_date());
                location.setText(dao.getItem_maintenance_location());
                detail.setText(dao.getMaintenace_detail());
                regist.setText("수정");
            }
            else {
                list.setText("");
                date.setText(celDate);
                location.setText("");
                detail.setText("");
                regist.setText("등록");
            }
        }
    }


}


