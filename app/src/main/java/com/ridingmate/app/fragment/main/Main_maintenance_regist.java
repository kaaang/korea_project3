package com.ridingmate.app.fragment.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ridingmate.app.R;
import com.ridingmate.app.activity.main.MainActivity;
import com.ridingmate.app.util.main.MaintenanceAdpter;
import com.ridingmate.app.util.main.MaintenanceDAO;

public class Main_maintenance_regist extends Fragment {
    // 정보
    public static TextView maintenance_date;
    public static TextView  maintenance_list;
    public static TextView maintenance_location;

    // 등록 수정 xml 공통 버튼
    public static TextView btn_regist;
    private TextView btn_list;

    // 프레그먼트 업데이트(등록, 수정 넘어갈때)
    public  static  boolean check_actiive = false;
    public  static  boolean check_edit;
    public  static  int check_id;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_maintenance_regist, container, false);

        maintenance_date = (TextView) view.findViewById(R.id.regist_maintenance_date);
        maintenance_list = (TextView) view.findViewById(R.id.regist_maintenance_list);
        maintenance_location = (TextView) view.findViewById(R.id.regist_maintenance_location);
        btn_regist = (TextView) view.findViewById(R.id.btn_maintenance_regist);
        ChangeTextView(check_id, check_edit);
        btn_list = (TextView) view.findViewById(R.id.btn_maintenance_list);
        btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.showPage(0);
            }
        });
        check_actiive = true;
        return view;
    }
    public  static  void ChangeTextView(int id, boolean check){

        if(check){
            MaintenanceDAO dao = MaintenanceAdpter.arrayList.get(id);
            //현제 데이터 가져오기
            maintenance_date.setText(dao.getItem_maintenance_date());
            maintenance_list.setText(dao.getItem_maintenance());
            maintenance_location.setText(dao.getItem_maintenance_location());

            btn_regist.setText("수정");
        }
        else {

            maintenance_date.setText(Main_maintenance_list.m_date);
            maintenance_list.setText("");
            maintenance_location.setText("");
            btn_regist.setText("등록");
        }
    }
}
