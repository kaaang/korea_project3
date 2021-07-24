package com.ridingmate.app.util.pageAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ridingmate.app.fragment.main.*;
import com.ridingmate.app.fragment.used.*;

import org.jetbrains.annotations.NotNull;

public class PageAdapter extends FragmentStateAdapter {
    Fragment pages[]=new Fragment[8];
    static private PageAdapter pageAdapter=null;

    private PageAdapter(@NonNull @NotNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        pages[0]=new Main_maintenance_list();
        pages[1]=new Main_maintenance_regist();
        pages[2]=new Main_mileage_list();
        pages[3]=new Main_mileage_regist();
        pages[4]=new Main_main();
        pages[5]=new Used_list();
        pages[6]=new Used_write();
        pages[7]=new Used_detail();
    }
    public static PageAdapter getInstance(FragmentActivity fragmentActivity){
        if(pageAdapter==null){
            pageAdapter=new PageAdapter(fragmentActivity);
        }
        return pageAdapter;
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        return pages[position];
    }

    @Override
    public int getItemCount() {
        return pages.length;
    }
}
