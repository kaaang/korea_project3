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

    Button bt_login;
    TextView bt_regist;
    EditText et_email, et_password;

    ImageView bt_google;//구글 로그인 버튼

    //구글 로그인 변수
    private static final String TAG = "LoginActivity";
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private FirebaseFirestore db;


    //파이어 베이스 변수
    private FirebaseAuth mAuth = null;

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


        //구글 로그인 초기 세팅
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


//이벤트 리스너==============================================================
        //회원가입 클릭시  창 이동
        bt_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, PhoneNumActivity.class);
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
                            updateUI(user);

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
                signIn();

            }
        });
    }
    //구글 로그인============================================================================
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            Snackbar.make(findViewById(R.id.layout_main), "Authentication Successed.", Snackbar.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
//                            Snackbar.make(findViewById(R.id.layout_main), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }
    private void updateUI(FirebaseUser user) { //update ui code here
        if (user != null) {

            FirebaseUser firebaseUser=mAuth.getCurrentUser();
            UserAccount account=new UserAccount();
            account.setIdToken(firebaseUser.getUid());
            account.setEmailId(firebaseUser.getEmail());
            account.setName(firebaseUser.getDisplayName());
            account.setPhoneNum(firebaseUser.getPhoneNumber());

            db.collection("user").document(firebaseUser.getUid()).set(account);


            Intent intent = new Intent(this, PhoneNumActivity.class);
            startActivity(intent);
            finish();


        }
    }
    //구글 로그인 끝============================================================================

}