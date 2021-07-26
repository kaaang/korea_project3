package com.ridingmate.app.activity.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.ridingmate.app.R;

/* 정비 등록 상단바에 일정 추가하는 버튼 추가하고 싶음.(list에 추가해야 regist로 이동)*/
public class MaintenanceActivity extends Fragment {
    private View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_main_maintenance_list, container, false);

        return view;
    }
}
