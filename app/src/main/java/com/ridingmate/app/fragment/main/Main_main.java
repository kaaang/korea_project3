package com.ridingmate.app.fragment.main;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.ridingmate.app.R;
import com.ridingmate.app.activity.main.MainActivity;
import com.ridingmate.app.util.main.FireBaseInterface;

import java.util.Calendar;

public class Main_main extends Fragment {
    private EditText tx_litter, tx_price, tx_station;
    // 팝업
    private Button btn_showGasStation;
    private TextView bt_ok, bt_list;
    // 날짜 선택
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private int mYear = 0, mMonth = 0, mDay = 0;
    private  String selected_date;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_main, container, false);
        ImageView imageView = view.findViewById(R.id.card_background);
        imageView.setColorFilter(R.color.black);
        FireBaseInterface.m_interface.Tv_litter((TextView)view.findViewById(R.id.milegae_litter));
        FireBaseInterface.m_interface.Tv_date((TextView)view.findViewById(R.id.milegae_date));
        FireBaseInterface.m_interface.InitFirebase();
        FireBaseInterface.m_interface.downloadMileageData();

        // -------------------------------------------------------------------주유 기록 등록
        btn_showGasStation = (Button) view.findViewById(R.id.btn_showGasStation);
        btn_showGasStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupWindow popupWindow = new PopupWindow(v);
                /* ----------------------------------------팝업---------------------------------------------------------------*/
                View popup = inflater.inflate(R.layout.fragment_main_mileage_regist, null);
                popupWindow.setContentView(popup);
                popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

                popupWindow.setFocusable(true);
                popupWindow.setTouchable(true);
                popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


                // 주유 기록 등록
                tx_litter= (EditText)popup.findViewById(R.id.milegae_text_litter);
                tx_price= (EditText)popup.findViewById(R.id.milegae_text_price);
                tx_station= (EditText)popup.findViewById(R.id.milegae_text_station);



                // ---------------------날짜 선택
                dateButton = (Button) popup.findViewById(R.id.date_picker);
                dateButton.setText(getTodaysDate());
                initDatePicker();

                dateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datePickerDialog.show();
                    }
                });

                // ---------------------팝업 버튼
                // 확인
                bt_ok = popupWindow.getContentView().findViewById(R.id.bt_ok);
                bt_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Firebase에 DB 삽입
                  /*     uid는 아직 어떻게 처리 할지 좀 감이 안 오니까 빼고 테스트 진행
                        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();*/
                        popupWindow.dismiss();
                        FireBaseInterface.m_interface.uploadMileageData(selected_date, tx_station.getText().toString(), tx_litter.getText().toString() + "L", tx_price.getText().toString() + "원");
                        FireBaseInterface.m_interface.downloadMileageData();
                    }
                });
                // 목록으로
                bt_list = popupWindow.getContentView().findViewById(R.id.bt_list);
                bt_list.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
               popupWindow.showAsDropDown(v);


            }
        });
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
                selected_date = makeDateString(day, month, year);
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
