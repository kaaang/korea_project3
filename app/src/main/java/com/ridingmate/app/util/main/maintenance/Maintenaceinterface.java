package com.ridingmate.app.util.main.maintenance;

import android.widget.TextView;

import java.util.ArrayList;

// 정비의 등록, 수정 레이아웃 통일로 인해 버튼명 변경 인터페이스, MaintenanceDAO의 날짜와 정보 받아오기
public interface Maintenaceinterface {
    TextView Tv_maintenance_date(TextView view);
    TextView Tv_maintenance_list(TextView view);
    TextView Tv_maintenance_location(TextView view);
    TextView Tv_maintenance_regist(TextView view);
    TextView Tv_maintenance_detail(TextView view);
    String Cel_Data(String str);
    void Maintenace_ChangeTextView(int id, boolean check);
}
