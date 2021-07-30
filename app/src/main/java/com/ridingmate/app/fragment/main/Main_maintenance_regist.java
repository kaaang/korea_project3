package com.ridingmate.app.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ridingmate.app.R;

public class Main_maintenance_regist extends Fragment {
    private TextView maintenance_date;
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_main_maintenance_regist,container,false);

        maintenance_date= (TextView)view.findViewById(R.id.regist_maintenance_date);
        maintenance_date.setText(Main_maintenance_list.m_date);
        return view;
    }
}
