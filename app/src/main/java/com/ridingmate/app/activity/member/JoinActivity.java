package com.ridingmate.app.activity.member;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ridingmate.app.R;

public class JoinActivity extends AppCompatActivity {

    Button bt_regist;
    TextView bt_login;
    ImageView bt_agreement, bt_overForteen, bt_personalAgree, bt_LocationAgree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        //버튼 가져오기
        bt_login=findViewById(R.id.bt_login);

        //이용약관 상세보기 버튼
        bt_agreement=findViewById(R.id.bt_agreement);
        bt_overForteen=findViewById(R.id.bt_overForteen);
        bt_personalAgree=findViewById(R.id.bt_personalAgree);
        bt_LocationAgree=findViewById(R.id.bt_LocationAgree);


        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(JoinActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        bt_agreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupWindow popupWindow=new PopupWindow(v);
                LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);

                View popup= layoutInflater.inflate(R.layout.tos_1, null);
                popupWindow.setContentView(popup);
                popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.setTouchable(true); // PopupWindow 위에서 Button의 Click이 가능하도록 setTouchable(true);

                TextView bt_yes=popupWindow.getContentView().findViewById(R.id.bt_yes);
                bt_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });


                popupWindow.showAsDropDown(v);


            }
        });






    }


}