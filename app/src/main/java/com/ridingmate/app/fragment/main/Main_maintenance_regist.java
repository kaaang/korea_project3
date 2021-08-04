package com.ridingmate.app.fragment.main;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ridingmate.app.R;
import com.ridingmate.app.activity.main.MainActivity;
import com.ridingmate.app.util.main.FireBaseInterface;
import com.ridingmate.app.util.main.maintenance.MaintenanceConstants;

import java.util.Calendar;

public class Main_maintenance_regist extends Fragment {
    // 등록 수정 xml 공통 버튼
    private TextView btn_list, btn_regist;

    // EditText
    EditText regist_item, regist_date, regist_ServiceCenter, regist_detail;

    // 프레그먼트 업데이트(등록, 수정 넘어갈때)
    public  static  boolean check_actiive = false;
    public  static  boolean check_edit;
    public  static  int check_id;

    // 날짜 선택
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private int mYear = 0, mMonth = 0, mDay = 0;
    public  static String selected_date;

    // Main에  접근
    MainActivity mainActivity= (MainActivity) MainActivity._main;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_maintenance_regist, container, false);


        MaintenanceConstants.m_interface.Tv_maintenance_list((TextView) view.findViewById(R.id.regist_maintenance_list));
        MaintenanceConstants.m_interface.Tv_maintenance_date((TextView) view.findViewById(R.id.regist_maintenance_date));
        MaintenanceConstants.m_interface.Tv_maintenance_location((TextView) view.findViewById(R.id.regist_maintenance_location));

        MaintenanceConstants.m_interface.Tv_maintenance_regist((TextView) view.findViewById(R.id.btn_maintenance_regist));
        MaintenanceConstants.m_interface.Tv_maintenance_detail((TextView) view.findViewById(R.id.regist_maintenance_detail));

        MaintenanceConstants.m_interface.Maintenace_ChangeTextView(check_id, check_edit);
        
        // FireBase 등록 연결
        FireBaseInterface.m_interface.InitFirebase();
        // EditText
        regist_item= (EditText)view.findViewById(R.id.regist_maintenance_list);
        regist_ServiceCenter= (EditText)view.findViewById(R.id.regist_maintenance_location);
        regist_detail= (EditText)view.findViewById(R.id.regist_maintenance_detail) ;

        // 정비 등록폼 안에 있는 등록 버튼
        btn_regist=(TextView)view.findViewById(R.id.btn_maintenance_regist);
        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FireBaseInterface.m_interface.uploadMaintenanceData(regist_item.getText().toString(), selected_date, regist_ServiceCenter.getText().toString(), regist_detail.getText().toString(), mainActivity.selectedBikeUid);
                Main_maintenance_list.arrayList.clear();
                FireBaseInterface.m_interface.downloadMaintenanceData(selected_date,mainActivity.selectedBikeUid);
                MainActivity.showPage(0);
            }
        });
        btn_list = (TextView) view.findViewById(R.id.btn_maintenance_list);
        btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.showPage(0);
            }
        });
        // ---------------------날짜 선택
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
                makeDateString(day, month, year);
                dateButton.setText(selected_date);
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
        selected_date = year + "년 " +month + "월 "  + day + "일";
        return year + "년 " +month + "월 "  + day + "일";
    }

}
