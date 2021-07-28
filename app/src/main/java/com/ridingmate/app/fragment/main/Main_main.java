package com.ridingmate.app.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.ridingmate.app.R;

public class Main_main extends Fragment {
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main_main,container,false);
        ImageView imageView= view.findViewById(R.id.card_background);
        imageView.setColorFilter(R.color.black);
        return view;
    }
}
