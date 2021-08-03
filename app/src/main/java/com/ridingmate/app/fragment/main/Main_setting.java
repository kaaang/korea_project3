package com.ridingmate.app.fragment.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ridingmate.app.R;
import com.ridingmate.app.util.main.MaintenanceAdpter;
import com.ridingmate.app.util.main.MaintenanceDAO;
import com.ridingmate.app.util.main.MyBikeData;
import com.ridingmate.app.util.main.SettingAdapter;
import com.ridingmate.app.util.used.UsedListAdapter;
import com.ridingmate.app.util.used.UsedListData;

import java.util.ArrayList;
import java.util.HashMap;

public class Main_setting extends Fragment {



    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private ArrayList<MyBikeData> arrayList;
    private SettingAdapter bikeListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main_setting,container,false);


        recyclerView=(RecyclerView)view.findViewById(R.id.bike_list);
        linearLayoutManager= new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), 1));
        arrayList=new ArrayList<>();

        // 사용자가 입력한 값을 끌어다가 보여주기 select * from...
        for (int i = 0; i < 3; i++) {
            MyBikeData dao = new MyBikeData("붕붕이", "BMW", "AMC", "2020");
            arrayList.add(dao);
        }

        bikeListAdapter=new SettingAdapter(arrayList,getActivity());
        recyclerView.setAdapter(bikeListAdapter);
        bikeListAdapter.notifyDataSetChanged();



        return view;
    }
}
