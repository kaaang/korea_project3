package com.ridingmate.app.fragment.used;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.ridingmate.app.R;

public class Used_write extends Fragment {
    Spinner writeSpinner;
    String[] items={"회사를 선택하세요","강신혁컴퍼니","쉉미딘해적단","진아와아이들","융합아이돌"};
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_used_write,container,false);

        return view;
    }
}
