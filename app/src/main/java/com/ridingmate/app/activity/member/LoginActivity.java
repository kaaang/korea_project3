package com.ridingmate.app.activity.member;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ridingmate.app.R;

public class LoginActivity extends AppCompatActivity{

    Button bt_login;
    TextView bt_regist;

    ImageView bt_google;//구글 로그인
   /* private FirebaseAuth auth; //파이어베이스 인증객체
    private GoogleApiClient googleApiClient;//구글 API 클라이언트 객체
    private static final int REQ_SIGN_GOOGLE=100; //구글 로그인 결과*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        bt_login=findViewById(R.id.bt_login);
        bt_regist=findViewById(R.id.bt_regist);
        bt_google=findViewById(R.id.bt_google);

        //텍스트 클릭시 회원가입 창 이동
        bt_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);

            }
        });
/*
        //구글 로그인 초기작업
        GoogleSignInOptions googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,googleSignInOptions)
                .build();

        auth=FirebaseAuth.getInstance(); //파이어베이스 인증객체 초기화

       //버튼 클릭시 구글 로그인
        bt_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, REQ_SIGN_GOOGLE);
            }
        });
*/
    }
/*
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==REQ_SIGN_GOOGLE){
            GoogleSignInResult result=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){
                GoogleSignInAccount account=result.getSignInAccount();//구글로그인 정보를 담고있음
                resultLogin(account);//로그인 결과값 출력
            }
        }
    }

    private void resultLogin(GoogleSignInAccount account) {
        AuthCredential credential= GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if(task.isSuccessful()){//로그인 성공시
                            AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);
                            builder.setTitle("로그인 결과");
                            builder.setMessage("로그인 성공");
                            builder.show();
                            Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("nickName", account.getDisplayName());
                            startActivity(intent);

                        }else{//로그인 성공시
                            AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);
                            builder.setTitle("로그인 결과");
                            builder.setMessage("로그인 실패");
                            builder.show();

                        }
                    }
                });

    }
     */

}