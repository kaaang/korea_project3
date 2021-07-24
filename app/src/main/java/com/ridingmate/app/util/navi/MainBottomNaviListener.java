package com.ridingmate.app.util.navi;

import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.navigation.NavigationBarView;
import com.ridingmate.app.R;
import com.ridingmate.app.activity.main.MainActivity;

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
                Toast.makeText(mainActivity.getApplicationContext(),"정비",Toast.LENGTH_SHORT).show();
                break;
            case R.id.milegae:
                mainActivity.showPage(2);
                Toast.makeText(mainActivity.getApplicationContext(),"주유",Toast.LENGTH_SHORT).show();
                break;
            case R.id.main:
                mainActivity.showPage(4);
                Toast.makeText(mainActivity.getApplicationContext(),"홈",Toast.LENGTH_SHORT).show();
                break;
            case R.id.used:
                mainActivity.showPage(5);
                Toast.makeText(mainActivity.getApplicationContext(),"중고",Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                Toast.makeText(mainActivity.getApplicationContext(),"우주 대존잘 강신혁",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
