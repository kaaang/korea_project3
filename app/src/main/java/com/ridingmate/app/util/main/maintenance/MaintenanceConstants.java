package com.ridingmate.app.util.main.maintenance;

import android.util.Log;
import android.widget.TextView;

import com.ridingmate.app.fragment.main.Main_maintenance_list;

import java.util.ArrayList;

public class MaintenanceConstants {


    public  static  Maintenaceinterface m_interface =  new MaintenanceController();
    public  static String mtc_Date;
    public  static boolean check_Update;
    public  static  int docu_id;
    // Maintenance UI 인터페이스 수정 파트
    private static class MaintenanceController implements Maintenaceinterface{

        private TextView date;
        private TextView  list;
        private TextView location;
        private TextView regist;
        private TextView detail;


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
            mtc_Date = str;
            return str;
        }

        // Textview 수정
        public void Maintenace_ChangeTextView(int id, boolean check) {
            if (check) {
                docu_id = id;
                check_Update = true;
 /*               Log.e("test",  id + "+++++++++++++++++" + docu_id);*/
                Maintenance dao =  Main_maintenance_list.arrayList.get(id);
                // 현제 데이터 가져오기
                list.setText(dao.getItem_maintenance());
                date.setText(dao.getItem_maintenance_date());
                location.setText(dao.getItem_maintenance_location());
                detail.setText(dao.getItem_maintenance_detail());
                regist.setText("수정");

            }
            else {
                list.setText("");
                date.setText(mtc_Date);
                location.setText("");
                detail.setText("");
                regist.setText("등록");
                check_Update = false;
            }
        }
    }


}


