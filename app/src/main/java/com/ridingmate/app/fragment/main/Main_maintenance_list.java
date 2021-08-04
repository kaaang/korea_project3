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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ridingmate.app.R;
import com.ridingmate.app.activity.main.MainActivity;
import com.ridingmate.app.util.main.FireBaseInterface;
import com.ridingmate.app.util.main.maintenance.MaintenanceAdpter;
import com.ridingmate.app.util.main.maintenance.Maintenance;
import com.ridingmate.app.util.main.maintenance.MaintenanceConstants;

import java.util.ArrayList;
import java.util.Calendar;

public class Main_maintenance_list extends Fragment{
    // 리사이클러뷰 처리
    private LinearLayoutManager linearLayoutManager;
    public static RecyclerView recyclerView;
    public static ArrayList<Maintenance> arrayList = new ArrayList<>();
    public static MaintenanceAdpter adpter;
    // 메인 접근
    MainActivity mainActivity= (MainActivity) MainActivity._main;
    // 캘린더
    private CalendarView mCalendarView;
    // 등록페이지 넘어가는 버튼
    private ImageView imgView;
    private Calendar  _todayCal = Calendar.getInstance();
    // 등록 수정 삭제
    private TextView btn_edit, btn_list;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_maintenance_list, container, false);
        // ---------------------------------------------------------------------------- 리사이클러 뷰 붙이기
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.maintentance_list);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), 1));



        // ---------------------------------------------------------------------------- 날짜 선택시 날짜 따라오게끔(정비내역&등록 수정 시 이용)
        mCalendarView = (CalendarView) view.findViewById(R.id.maintentance_cal);
        MaintenanceConstants.m_interface.Cel_Data( _todayCal.get(Calendar.YEAR) + "년 " +  (_todayCal.get(Calendar.MONTH) +1) + "월 " +  _todayCal.get(Calendar.DATE) + "일");
        FireBaseInterface.m_interface.downloadMaintenanceData(MaintenanceConstants.mtc_Date,mainActivity.selectedBikeUid);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                MaintenanceConstants.m_interface.Cel_Data(year + "년 " + (month + 1) + "월 " + dayOfMonth + "일");
                arrayList.clear();
                FireBaseInterface.m_interface.downloadMaintenanceData(MaintenanceConstants.mtc_Date,mainActivity.selectedBikeUid);
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
    public static void UpdateRecyclerView(){
        adpter = new MaintenanceAdpter();
        recyclerView.setAdapter(adpter);
        adpter.notifyDataSetChanged();
    }
}
