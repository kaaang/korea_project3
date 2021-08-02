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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ridingmate.app.R;
import com.ridingmate.app.activity.main.MainActivity;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    Button bt_login;
    TextView bt_regist; //일반 회원가입 버튼
    TextView bt_forgetPW, bt_forgetID;

    EditText et_email, et_password;

    ImageView bt_google;//구글 로그인 버튼

    Bundle bundle = new Bundle();

    //파이어 베이스 변수
    private FirebaseAuth mAuth = null;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        bt_login = findViewById(R.id.bt_login);
        bt_regist = findViewById(R.id.bt_regist);
        bt_google = findViewById(R.id.bt_google);

        bt_forgetID=findViewById(R.id.bt_forgetID);
        bt_forgetPW=findViewById(R.id.bt_forgetPW);

        et_email=findViewById(R.id.et_email);
        et_password=findViewById(R.id.et_password);



        //파이어 베이스 객체
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();






//이벤트 리스너==============================================================

        bt_forgetPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupView1(v, R.layout.change_password);
            }
        });

        bt_forgetID.setOnClickListener(v -> {
            popupView2(v, R.layout.search_email);
        });


        //회원가입 클릭시  창 이동
        bt_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, PhoneNumActivity.class);
                bundle.putString("clicked", "normal");
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });


        //로그인 클릭시 로그인처리
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strEmail=et_email.getText().toString();
                String strPwd=et_password.getText().toString();

                mAuth.signInWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();

                        }else{
                            Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        //구글 로그인 버튼 클릭
        bt_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onclick 작동");
                Intent intent=new Intent(LoginActivity.this, PhoneNumActivity.class);

                bundle.putString("clicked", "google");
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
    }

//팝업생성
    public void popupView1(View v, int res) {
        PopupWindow popupWindow=new PopupWindow(v);
        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        View popup= layoutInflater.inflate(res, null);
        popupWindow.setContentView(popup);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setTouchable(true); // PopupWindow 위에서 Button의 Click이 가능하도록 setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setContentView(popup);

        //비밀번호 찾기 객체
        Button button1=popupWindow.getContentView().findViewById(R.id.button1);
        EditText et_emailForPW=popupWindow.getContentView().findViewById(R.id.et_emailForPW);
        TextView txt=popupWindow.getContentView().findViewById(R.id.txt);


        TextView cancel=popupWindow.getContentView().findViewById(R.id.cancel);

        //비밀번호 찾기 버튼 클릭
        button1.setOnClickListener(v1 -> {
            sendEmailForPW(et_emailForPW);
            popupWindow.dismiss();
        });


        cancel.setOnClickListener(v1 -> popupWindow.dismiss());

        et_emailForPW.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Pattern pattern= Patterns.EMAIL_ADDRESS;
                if(pattern.matcher(et_emailForPW.getText().toString()).matches()){
                    txt.setText("올바른 이메일 형식입니다.");
                }else{
                    txt.setText("올바른 이메일 형식을 입력하세요");
                }

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        popupWindow.showAsDropDown(v);

    }
//아이디 찾기 팝업
    public void popupView2(View v, int res) {
        PopupWindow popupWindow=new PopupWindow(v);
        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        View popup= layoutInflater.inflate(res, null);
        popupWindow.setContentView(popup);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setTouchable(true); // PopupWindow 위에서 Button의 Click이 가능하도록 setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setContentView(popup);

        //아이디 찾기 객체
        Button button2=popupWindow.getContentView().findViewById(R.id.button2);
        EditText et_phonNum=popupWindow.getContentView().findViewById(R.id.et_phonNum);
        EditText et_nameForId=popupWindow.getContentView().findViewById(R.id.et_nameForId);
        TextView foundEmail=popupWindow.getContentView().findViewById(R.id.foundEmail);

        TextView cancel=popupWindow.getContentView().findViewById(R.id.cancel);


        //아이디 찾기 버튼 클릭
        button2.setOnClickListener(v1 -> {
            if(et_phonNum.getText().toString().isEmpty() || et_nameForId.getText().toString().isEmpty()){
                Toast.makeText(LoginActivity.this, "이름과 전화번호를 모두 입력해주세요", Toast.LENGTH_SHORT).show();
            }else{
                searchId(et_phonNum, et_nameForId, foundEmail);
            }
        });


        cancel.setOnClickListener(v1 -> popupWindow.dismiss());

        popupWindow.showAsDropDown(v);

    }

    public void searchId(EditText et_phonNum, EditText et_nameForId, TextView foundEmail) {
        String phoneNum=et_phonNum.getText().toString();
        String name=et_nameForId.getText().toString();
        String result=null;

        db.collection("user")
                .whereEqualTo("phoneNum", phoneNum)
                .whereEqualTo("name", name)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isComplete()){
                            if(task.getResult().isEmpty()){
                                Log.e(TAG, "사용 가능한 이메일");
                                Toast.makeText(LoginActivity.this, "가입되지 않은 회원입니다", Toast.LENGTH_SHORT).show();
                                foundEmail.setText("");
                            }else{
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.e(TAG, document.getString("emailId") );
                                    String result=document.getString("emailId");
                                    foundEmail.setText("당신의 아이디는 "+result);
                                }
                                Toast.makeText(LoginActivity.this, "가입된 회원입니다.", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                });

    }


    public void sendEmailForPW(EditText et_emailForPW) {
        String emailAddress = et_emailForPW.getText().toString();
        Log.e(TAG, "내가이메일을 보낼곳"+emailAddress);



        mAuth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.e(TAG, "왜 테스크가 성공이아닐까"+task);

                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "이메일을 성공적으로 보냈습니다\n이메일을 확인해주세요", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(LoginActivity.this, "이메일을 보내는데 실패하였습니다.", Toast.LENGTH_LONG).show();
                        }
                    }


                });

    }


    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }



}