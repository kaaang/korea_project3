package com.ridingmate.app.activity.member;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.ridingmate.app.R;
import com.ridingmate.app.activity.main.MainActivity;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    Button bt_login;
    TextView bt_regist; //일반 회원가입 버튼
    EditText et_email, et_password;

    ImageView bt_google;//구글 로그인 버튼

    Bundle bundle = new Bundle();

    //파이어 베이스 변수
    private FirebaseAuth mAuth = null;
    private FirebaseFirestore db;

    SignInButton signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        bt_login = findViewById(R.id.bt_login);
        bt_regist = findViewById(R.id.bt_regist);
        bt_google = findViewById(R.id.bt_google);

        et_email=findViewById(R.id.et_email);
        et_password=findViewById(R.id.et_password);



        //파이어 베이스 객체
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();






//이벤트 리스너==============================================================
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

        public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }



}