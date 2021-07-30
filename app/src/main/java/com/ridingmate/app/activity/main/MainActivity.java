package com.ridingmate.app.activity.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ridingmate.app.R;
import com.ridingmate.app.util.navi.MainBottomNaviListener;
import com.ridingmate.app.util.pageAdapter.PageAdapter;


public class MainActivity extends AppCompatActivity {
    // 네비게이션 불러올 객체
    BottomNavigationView bottomNavigationView;
    // 프래그먼트 관리 어댑터
    PageAdapter pageAdapter;
    // 뷰페이저
   private  static   ViewPager2 viewPager;
    //상단 툴바
    Toolbar toolbar;
    String[] category=null;
    // 주유 등록시 context접근 필요하여 선언
    public  static Context  _main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // context담기
        _main =  this;

        //상단 툴바 인스턴스 얻기
        toolbar = findViewById(R.id.toolbar);

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



        //상단 툴바 적용
        setSupportActionBar(toolbar);

    }
    public static void showPage(int index) {
        viewPager.setCurrentItem(index,false);
    }

    //앱바 영역에 툴바를 이용한 메뉴 구성

    public boolean onCreateOptionsMenu(Menu menu) {
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        MenuInflater menuInflater=this.getMenuInflater();
        menuInflater.inflate(R.menu.main_navi,menu);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        MenuItem item=menu.findItem(R.id.bike_spinner);
        Spinner spinner=(Spinner) item.getActionView();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.my_bike, R.layout.bike_spinner);
        adapter.setDropDownViewResource(R.layout.bike_spinner);
        spinner.setAdapter(adapter);


        return super.onCreateOptionsMenu(menu);
    }


    //툴바 이벤트 구현
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.bike_regist:showPage(8);break;
            case R.id.bike_detail:showPage(9);break;
        }
        return super.onOptionsItemSelected(item);
    }




}