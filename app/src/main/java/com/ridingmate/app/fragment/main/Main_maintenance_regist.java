package com.ridingmate.app.fragment.main;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ridingmate.app.R;
import com.ridingmate.app.activity.main.MainActivity;
import com.ridingmate.app.util.main.Maintenaceinterface;
import com.ridingmate.app.util.main.MaintenanceConstants;

import java.util.Calendar;

public class Main_maintenance_regist extends Fragment {
    // 등록 수정 xml 공통 버튼
    private TextView btn_list;
    // 프레그먼트 업데이트(등록, 수정 넘어갈때)
    public  static  boolean check_actiive = false;
    public  static  boolean check_edit;
    public  static  int check_id;

    // 날짜 선택
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private int mYear = 0, mMonth = 0, mDay = 0;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_maintenance_regist, container, false);

        MaintenanceConstants.m_interface.Tv_maintenance_date((TextView) view.findViewById(R.id.regist_maintenance_date));
        MaintenanceConstants.m_interface.Tv_maintenance_list((TextView) view.findViewById(R.id.regist_maintenance_list));
        MaintenanceConstants.m_interface.Tv_maintenance_location((TextView) view.findViewById(R.id.regist_maintenance_location));
        MaintenanceConstants.m_interface.Tv_maintenance_regist((TextView) view.findViewById(R.id.btn_maintenance_regist));
        MaintenanceConstants.m_interface.Maintenace_ChangeTextView(check_id, check_edit);
        btn_list = (TextView) view.findViewById(R.id.btn_maintenance_list);
        btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.showPage(0);
            }
        });
        // ---------------------날짜 선택

        // 문제: 수정 누를 시에는 DB에 등록된 날짜가 와야하나, 오늘 날짜로 다이얼이 셋팅됨.(데이터피커 class별도 분리 후 constants에서 맞게 처리 필요)
        dateButton = (Button) view.findViewById(R.id.regist_maintenance_date);
        dateButton.setText(getTodaysDate());
        initDatePicker();

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        check_actiive = true;
        return view;
    }
    /* ----------------------------------------DatePicker 함수---------------------------------------------------------------*/
    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(MainActivity._main, style, dateSetListener, year, month, day);
        // datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()); 현재 날짜 기준이 max

    }

    private String makeDateString(int day, int month, int year) {
        return year + "년 " +getMonthFormat(month)  + day + "일";
    }

    private String getMonthFormat(int month) {
        if (month == 1)
            return "01월 ";
        if (month == 2)
            return "02월 ";
        if (month == 3)
            return "03월 ";
        if (month == 4)
            return "04월 ";
        if (month == 5)
            return "05월 ";
        if (month == 6)
            return "06월 ";
        if (month == 7)
            return "07월 ";
        if (month == 8)
            return "08월 ";
        if (month == 9)
            return "09월 ";
        if (month == 10)
            return "10월 ";
        if (month == 11)
            return "11월 ";
        if (month == 12)
            return "12월 ";

        return "01월";
    }

}
