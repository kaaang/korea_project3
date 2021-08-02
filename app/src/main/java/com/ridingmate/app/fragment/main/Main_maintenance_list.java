package com.ridingmate.app.fragment.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ridingmate.app.R;
import com.ridingmate.app.activity.main.MainActivity;
import com.ridingmate.app.util.main.MaintenanceAdpter;
import com.ridingmate.app.util.main.MaintenanceDAO;
import com.ridingmate.app.util.main.MaintenanceConstants;

import java.util.ArrayList;
import java.util.Calendar;

public class Main_maintenance_list extends Fragment{
    // 리사이클러뷰 처리
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private ArrayList<MaintenanceDAO> arrayList = new ArrayList<>();
    private MaintenanceAdpter adpter;
    // 캘린더
    private CalendarView mCalendarView;
    // 등록페이지 넘어가는 버튼
    private ImageView imgView;

    private Calendar  _todayCal = Calendar.getInstance();
    // 등록 수정 삭제
    private TextView btn_eidt, btn_list;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_maintenance_list, container, false);
        // ---------------------------------------------------------------------------- 리사이클러 뷰 붙이기
        linearLayoutManager = new LinearLayoutManager((view.getContext()));
        recyclerView = (RecyclerView) view.findViewById(R.id.maintentance_list);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), 1));
        // 사용자가 입력한 값을 끌어다가 보여주기 select * from...
        for (int i = 0; i < 5; i++) {
            MaintenanceDAO dao = new MaintenanceDAO("엔진오일 " + i, "2021.07.28", "S오일 송파점");
            arrayList.add(dao);
            Log.e("Test", "" + i);
        }
        adpter = new MaintenanceAdpter(arrayList);
        recyclerView.setAdapter(adpter);
        adpter.notifyDataSetChanged();



        // ---------------------------------------------------------------------------- 날짜 선택시 날짜 따라오게끔(정비내역&등록 수정 시 이용)
        mCalendarView = (CalendarView) view.findViewById(R.id.maintentance_cal);
        MaintenanceConstants.m_interface.Cel_Data( _todayCal.get(Calendar.YEAR) + "-" +  (_todayCal.get(Calendar.MONTH) +1) + "-" +  _todayCal.get(Calendar.DATE));


        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                MaintenanceConstants.m_interface.Cel_Data(year + "-" + (month + 1) + "-" + dayOfMonth);
            }

        });

        // ---------------------------------------------------------------------------- 정비 등록으로 이동
        imgView= view.findViewById(R.id.cal_add);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.showPage(1);
                if(Main_maintenance_regist.check_actiive){
                    MaintenanceConstants.m_interface.Maintenace_ChangeTextView(-1, false);
                }
                else {
                    Main_maintenance_regist.check_edit = false;
                }

            }
        });





        return view;


    }
}
