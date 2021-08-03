package com.ridingmate.app.activity.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ridingmate.app.R;
import com.ridingmate.app.fragment.bike.Bike_detail;
import com.ridingmate.app.util.navi.MainBottomNaviListener;
import com.ridingmate.app.util.pageAdapter.PageAdapter;

import java.util.ArrayList;

<<<<<<< HEAD
public class MainActivity extends AppCompatActivity {
    public String data;
=======

public class MainActivity extends AppCompatActivity{
>>>>>>> sukho2
    // 네비게이션 불러올 객체
    BottomNavigationView bottomNavigationView;
    // 프래그먼트 관리 어댑터
    PageAdapter pageAdapter;
    // 뷰페이저
   public   static   ViewPager2 viewPager;
    //상단 툴바
    Toolbar toolbar;
    String[] category=null;
    // 주유 등록시 context접근 필요하여 선언
    public  static Context  _main;

    //파이어베이스 관련
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    FirebaseFirestore db=FirebaseFirestore.getInstance();

    //스피너관련
    ArrayAdapter adapter;
    ArrayList mybikeUid=new ArrayList();
    ArrayList mybike=new ArrayList();
    ArrayList spinnerList=new ArrayList();
    String selectedBikeUid;
    Spinner spinner;

    Bike_detail bike_detail;

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
        //툴바 타이틀 로고로 변경
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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
        spinner=(Spinner) item.getActionView();
        getMybike();
        adapter = new ArrayAdapter(this,R.layout.bike_spinner,spinnerList);
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

    public void getMybike(){
        //어레이 초기화
        mybike.clear();
        spinnerList.clear();
        mybikeUid.clear();

        db.collection("mybike")
                .whereEqualTo("uid", firebaseAuth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().isEmpty()){
                                spinnerList.add("바이크를 등록 하세요");
                                adapter.notifyDataSetChanged();
                            }else {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.e("asd", document.getId() + " => " + document.getData());
                                    mybike.add(document);
                                    spinnerList.add(document.getData().get("nickname"));
                                    mybikeUid.add(document.getId());
                                    adapter.notifyDataSetChanged();
                                    setSpinner();
                                }
                            }
                        } else {
                            Log.e("asd", "Error getting documents: ", task.getException());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("asd", "Error getting documents: "+e);
                    }
                });
    }

    public void setSpinner(){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedBikeUid= (String) mybikeUid.get(position);
                Log.e("asd", selectedBikeUid);
                bike_detail= (Bike_detail) pageAdapter.pages[9];
                bike_detail.getDetail((QueryDocumentSnapshot) mybike.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}