package com.ridingmate.app.activity.member;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ridingmate.app.R;

import org.jetbrains.annotations.NotNull;

public class JoinActivity extends AppCompatActivity {


    private FirebaseAuth mfireFirebaseAuth;
    private FirebaseFirestore db;
    EditText et_email, et_name, et_password, et_passwordCheck;
    Button bt_regist, bt_checkDupli;

    TextView bt_login;
    ImageView bt_agreement, bt_overForteen, bt_personalAgree, bt_LocationAgree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        //객체 생성하기==========================================================
        //버튼 가져오기
        bt_login=findViewById(R.id.bt_login);

        //이용약관 상세보기 버튼
        bt_agreement=findViewById(R.id.bt_agreement);
        bt_overForteen=findViewById(R.id.bt_overForteen);
        bt_personalAgree=findViewById(R.id.bt_personalAgree);
        bt_LocationAgree=findViewById(R.id.bt_LocationAgree);

        //회원정보
        et_email=findViewById(R.id.et_email);
        et_name=findViewById(R.id.et_name);
        et_password=findViewById(R.id.et_password);
        et_passwordCheck=findViewById(R.id.et_passwordCheck);
        bt_checkDupli=findViewById(R.id.bt_checkDupli);
        bt_regist=findViewById(R.id.bt_regist);


        //파이어베이스 객체 ========================================================

        mfireFirebaseAuth=FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        bt_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strEmail=et_email.getText().toString();
                String strName=et_name.getText().toString();
                String strPwd=et_password.getText().toString();

                mfireFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(JoinActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if(task.isSuccessful()){


                            FirebaseUser firebaseUser=mfireFirebaseAuth.getCurrentUser();
                            UserAccount account=new UserAccount();
                            account.setIdToken(firebaseUser.getUid());
                            account.setEmailId(firebaseUser.getEmail());
                            account.setPassword(strPwd);
                            account.setName(strName);

                            db.collection("user").document("user").set(account);


                            Toast.makeText(JoinActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(JoinActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });







        //이벤트 리스너==========================================================
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