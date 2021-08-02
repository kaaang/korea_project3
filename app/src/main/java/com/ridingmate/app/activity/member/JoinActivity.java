package com.ridingmate.app.activity.member;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ridingmate.app.R;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class JoinActivity extends AppCompatActivity {

    private static final String TAG = "JoinActivity";

    //파이어베이스 객체
    private FirebaseAuth mfireFirebaseAuth;
    private FirebaseFirestore db;


    EditText et_email, et_name, et_password, et_phone;
    Button bt_regist, bt_checkDupli;

    TextView bt_login, emailCheckText;
    ImageView bt_agreement, bt_personalAgree, bt_LocationAgree;
    CheckBox ch_agreeAll, ch_agreement, ch_overForteen, ch_personalAgree, ch_LocationAgree;


    boolean emailFlag=true;

    Bundle bundle=null;
    String phoneNum;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);


        //객체 생성하기==========================================================
        //버튼 가져오기
        bt_login=findViewById(R.id.bt_login);

        //이용약관 상세보기 버튼
        bt_agreement=findViewById(R.id.bt_agreement);
        bt_personalAgree=findViewById(R.id.bt_personalAgree);
        bt_LocationAgree=findViewById(R.id.bt_LocationAgree);

        //이용약관 체크박스 버튼
        ch_agreement=findViewById(R.id.ch_agreement);
        ch_overForteen=findViewById(R.id.ch_overForteen);
        ch_personalAgree=findViewById(R.id.ch_personalAgree);
        ch_LocationAgree=findViewById(R.id.ch_LocationAgree);
        ch_agreeAll=findViewById(R.id.ch_agreeAll);


        //회원정보
        et_email=findViewById(R.id.et_email);
        et_name=findViewById(R.id.et_name);
        et_password=findViewById(R.id.et_password);
        et_phone=findViewById(R.id.et_phone);
        emailCheckText=findViewById(R.id.emailCheckText);

        bt_checkDupli=findViewById(R.id.bt_checkDupli);
        bt_regist=findViewById(R.id.bt_regist);

        bundle=getIntent().getExtras();
        phoneNum=bundle.getString("phoneNum");
        et_phone.setText(phoneNum);
        Log.e(TAG, "번들로 전달된 phoneNum"+phoneNum);






        //파이어베이스 객체 ========================================================

        mfireFirebaseAuth=FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();



        //이벤트 리스너==========================================================
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(JoinActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        bt_agreement.setOnClickListener(v -> popupView(v, R.layout.tos_1));
        bt_personalAgree.setOnClickListener(v -> popupView(v, R.layout.tos_2));
        bt_LocationAgree.setOnClickListener(v -> popupView(v, R.layout.tos_3));



        ch_agreeAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    ch_overForteen.setChecked(true);
                    ch_agreement.setChecked(true);
                    ch_personalAgree.setChecked(true);
                    ch_LocationAgree.setChecked(true);
                }else{
                    ch_overForteen.setChecked(false);
                    ch_agreement.setChecked(false);
                    ch_personalAgree.setChecked(false);
                    ch_LocationAgree.setChecked(false);
                }
            }
        });

        et_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                emailCheckText.setText("");
                emailFlag=true;
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) { }
        });


        bt_regist.setOnClickListener(v -> {

            String strEmail=et_email.getText().toString();
            String strPwd=et_password.getText().toString();
            String strName=et_name.getText().toString();


            if(strEmail.equals("")){
                Toast.makeText(JoinActivity.this, "이메일을 입력하세요", Toast.LENGTH_SHORT).show();
                return;
            }else if(strName.equals("")) {
                Toast.makeText(JoinActivity.this, "이름을 입력하세요", Toast.LENGTH_SHORT).show();
                return;
            }else if(strPwd.equals("")) {
                Toast.makeText(JoinActivity.this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                return;
            }

            if(emailFlag){
                Toast.makeText(JoinActivity.this, "중복검사를 실행하여 주세요", Toast.LENGTH_SHORT).show();
                return;
            }


            if(ch_agreement.isChecked()==false || ch_overForteen.isChecked()==false || ch_personalAgree.isChecked()==false){
                Toast.makeText(JoinActivity.this, "필수 이용약관을 모두 동의해 주세요", Toast.LENGTH_SHORT).show();
                return;
            }

           successJoin(strEmail, strPwd, strName);

        });

        bt_checkDupli.setOnClickListener(v -> {
            String strEmail=et_email.getText().toString();
            Pattern pattern= Patterns.EMAIL_ADDRESS;
            if(pattern.matcher(strEmail).matches()){
                checkEmailDupli(strEmail);
            }else{
                Toast.makeText(JoinActivity.this, "올바른 이메일 형식을 입력하세요", Toast.LENGTH_SHORT).show();
            }


        });


    }

    private boolean checkEmailDupli(String strEmail) {

        db.collection("user")
                .whereEqualTo("emailId", strEmail)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isComplete()){
                            if(task.getResult().isEmpty()){

                                Log.e(TAG, "사용 가능한 이메일");
                                emailCheckText.setText("사용 가능한 이메일 입니다.");
                                Toast.makeText(JoinActivity.this, "사용 가능한 이메일 입니다", Toast.LENGTH_SHORT).show();
                                emailFlag=false;
                            }else{
                                Log.e(TAG, "이미 사용중인 이메일");
                                emailCheckText.setText("이미 사용중인 이메일 입니다.");
                                Toast.makeText(JoinActivity.this, "다른 이메일을 입력하세요", Toast.LENGTH_SHORT).show();
                                emailFlag=true;
                            }

                        }
                    }

                });
        return emailFlag;
    }

    private void successJoin(String strEmail, String strPwd, String strName) {
        Log.e(TAG, "가입시작");

        mfireFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(JoinActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if(task.isSuccessful()){


                    FirebaseUser firebaseUser=mfireFirebaseAuth.getCurrentUser();
                    UserAccount account=new UserAccount();
                    account.setIdToken(firebaseUser.getUid());
                    account.setEmailId(firebaseUser.getEmail());
                    account.setName(strName);
                    account.setPhoneNum(phoneNum);

                    db.collection("user").document(firebaseUser.getUid()).set(account);

                    Toast.makeText(JoinActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();

                    Intent intent=new Intent(JoinActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();




                }else{
                    Toast.makeText(JoinActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void popupView(View v, int tos) {
        PopupWindow popupWindow=new PopupWindow(v);
        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        View popup= layoutInflater.inflate(tos, null);
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


}