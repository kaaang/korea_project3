package com.ridingmate.app.util.navi;

import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.navigation.NavigationBarView;
import com.ridingmate.app.R;
import com.ridingmate.app.activity.main.MainActivity;
import com.ridingmate.app.fragment.main.Main_setting;
import com.ridingmate.app.util.pageAdapter.PageAdapter;

import org.jetbrains.annotations.NotNull;

public class MainBottomNaviListener implements NavigationBarView.OnItemSelectedListener {
    static private MainBottomNaviListener mainBottomNaviListener = null;
    public MainActivity mainActivity;

    private MainBottomNaviListener(MainActivity mainActivity) {
        this.mainActivity=mainActivity;
    }

    public static MainBottomNaviListener getInstance(MainActivity mainActivity){
        if (mainBottomNaviListener==null){
            mainBottomNaviListener=new MainBottomNaviListener(mainActivity);
        }
        return mainBottomNaviListener;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.maintentance:
                mainActivity.showPage(0);
                break;
            case R.id.milegae:
                mainActivity.showPage(2);
                break;
            case R.id.main:
                mainActivity.showPage(4);
                break;
            case R.id.used:
                mainActivity.showPage(5);
                break;
            case R.id.setting:
                mainActivity.showPage(10);
                break;
        }
        return true;
    }
}
