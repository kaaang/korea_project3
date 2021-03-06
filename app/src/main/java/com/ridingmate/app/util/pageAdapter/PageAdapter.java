package com.ridingmate.app.util.pageAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ridingmate.app.fragment.bike.Bike_detail;
import com.ridingmate.app.fragment.bike.Bike_regist;
import com.ridingmate.app.fragment.main.*;
import com.ridingmate.app.fragment.used.*;


public class PageAdapter extends FragmentStateAdapter {
    public Fragment pages[]=new Fragment[12];
    static private PageAdapter pageAdapter=null;

    private PageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        pages[0]=new Main_maintenance_list();
        pages[1]=new Main_maintenance_regist();
        pages[2]=new Main_mileage_list();
        pages[3]=new Main_mileage_regist();
        pages[4]=new Main_main();
        pages[5]=new Used_list();
        pages[6]=new Used_write();
        pages[7]=new Used_detail();
        pages[8]=new Bike_regist();
        pages[9]=new Bike_detail();
        pages[10]=new Main_setting();
        pages[11]=new Main_setting_mybike_detail();
    }
    public static PageAdapter getInstance(FragmentActivity fragmentActivity){
        if(pageAdapter==null){
            pageAdapter=new PageAdapter(fragmentActivity);
        }
        return pageAdapter;
    }

    @NonNull

    @Override
    public Fragment createFragment(int position) {
        return pages[position];
    }

    @Override
    public int getItemCount() {
        return pages.length;
    }
}
