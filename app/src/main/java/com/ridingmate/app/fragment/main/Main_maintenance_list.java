package com.ridingmate.app.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ridingmate.app.R;
import com.ridingmate.app.util.maintenance.MaintenanceAdpter;
import com.ridingmate.app.util.maintenance.MaintenanceDAO;

import java.util.ArrayList;

public class Main_maintenance_list extends Fragment {
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private ArrayList<MaintenanceDAO> arrayList;
    private MaintenanceAdpter adpter;

    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main_maintenance_list,container,false);

        linearLayoutManager= new LinearLayoutManager((view.getContext()));
        recyclerView= (RecyclerView)view.findViewById(R.id.maintentance_list);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), 1));

        arrayList= new ArrayList<>();
        adpter= new MaintenanceAdpter(arrayList);
        recyclerView.setAdapter(adpter);

        /* 주유 기록까지 넘겨 받아서 캘린더와 정비기록란에 같이 띄우고 싶음.*/

        for(int i=0; i<10; i++){
            MaintenanceDAO dao= new MaintenanceDAO("엔진오일","2021.07.28", "S오일 송파점");
            arrayList.add(dao);
        }
        adpter.notifyDataSetChanged();
        return view;


    }
}
