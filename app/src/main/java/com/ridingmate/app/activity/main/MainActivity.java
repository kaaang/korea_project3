package com.ridingmate.app.activity.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ridingmate.app.R;
import com.ridingmate.app.activity.member.LoginActivity;
import com.ridingmate.app.util.navi.MainBottomNaviListener;
import com.ridingmate.app.util.pageAdapter.PageAdapter;


public class MainActivity extends AppCompatActivity {
    // 네비게이션 불러올 객체
    BottomNavigationView bottomNavigationView;
    // 프래그먼트 관리 어댑터
    PageAdapter pageAdapter;
    // 뷰페이저
    ViewPager2 viewPager;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //프래그먼트 관리 어댑터 인스턴스 얻어오기
        pageAdapter=PageAdapter.getInstance(this);

        //뷰페이저 인스턴스 얻어오기
        viewPager=findViewById(R.id.viewpager);
        //뷰페이저에 어댑터 붙이기
        viewPager.setAdapter(pageAdapter);
        viewPager.setCurrentItem(4, false);
        viewPager.setUserInputEnabled(false);


        // 하단 네비게이션 불러오기
        bottomNavigationView=findViewById(R.id.bottom_navi);
        // 네비게이션에 리스너 및 액션 넣기
        bottomNavigationView.setOnItemSelectedListener(MainBottomNaviListener.getInstance(this));
        // 처음 선택되있을 하단 네비게이션 아이템 선택
        bottomNavigationView.setSelectedItemId(R.id.main);


        //파이어베이스 권한
        firebaseAuth=FirebaseAuth.getInstance();


    }


    public void showPage(int index) {
        viewPager.setCurrentItem(index,false);
    }
}