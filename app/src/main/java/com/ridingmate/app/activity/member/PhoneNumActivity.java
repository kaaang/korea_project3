package com.ridingmate.app.activity.member;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ridingmate.app.R;
import com.ridingmate.app.activity.main.MainActivity;

import java.util.concurrent.TimeUnit;

public class PhoneNumActivity extends AppCompatActivity {

    private static final String TAG = "PhoneAuthActivity";

    // 파이어 베이스 객체
    private FirebaseAuth mAuth=null;
    private FirebaseFirestore db;

    //구글 로그인 객체
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;

    //전화번호 인증 객체
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;



    TextView bt_Auth_request, bt_retry_auth;
    Button bt_submit;
    EditText et_phone, et_code;

    String phoneNum=null;
    String uid=null;
    String clicked=null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_num);

        //객체 생성
        bt_Auth_request=findViewById(R.id.bt_Auth_request);
        bt_retry_auth=findViewById(R.id.bt_retry_auth);
        bt_submit=findViewById(R.id.bt_submit);

        et_phone=findViewById(R.id.et_phone);
        et_code=findViewById(R.id.et_code);


        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        Bundle bundle=getIntent().getExtras();
        clicked=bundle.getString("clicked");
        Log.e(TAG, "현재 클릭된 버튼은"+clicked);



        //구글 로그인 초기 세팅
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //전화번호 인증 초기 셋팅
        // Initialize phone auth callbacks
        // [START phone_auth_callbacks]
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.e(TAG, "onVerificationCompleted:" + credential);

                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.e(TAG, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }

                // Show a message and update the UI
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.e(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
            }
        };
        // [END phone_auth_callbacks]



        //클릭 리스너==========================================================
        bt_Auth_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNum=et_phone.getText().toString();
                if(TextUtils.isEmpty(phoneNum)){
                    Toast.makeText(PhoneNumActivity.this, "번호를 입력해 주세요", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(PhoneNumActivity.this, "인증번호를 전송하였습니다. 60초안에 인증하여 주십시오", Toast.LENGTH_SHORT).show();
                    startPhoneNumberVerification("+1"+phoneNum);
                    et_code.setEnabled(true);
                    Resources res = getResources();
                    Drawable res_white = res.getDrawable(R.drawable.white_edittext);
                    et_code.setBackground(res_white);
                }
            }
        });
        bt_retry_auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNum=et_phone.getText().toString();
                if(TextUtils.isEmpty(phoneNum)){
                    Toast.makeText(PhoneNumActivity.this, "번호를 입력해 주세요", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(PhoneNumActivity.this, "인증번호를 다시 전송하였습니다. 60초안에 인증하여 주십시오", Toast.LENGTH_SHORT).show();
                    resendVerificationCode(phoneNum, mResendToken);
                }
            }
        });

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code=et_code.getText().toString();
                if(TextUtils.isEmpty(code)){
                    Toast.makeText(PhoneNumActivity.this, "인증코드를 입력해 주세요", Toast.LENGTH_SHORT).show();
                }else{

                    verifyPhoneNumberWithCode(mVerificationId,code);
                }
            }
        });
        //클릭 리스너 끝==========================================================


    }

//전화전호 인증=================================================================
    // [START on_start_check_user]
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
//    }
    // [END on_start_check_user]


    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        // [END start_phone_auth]
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
        // [END verify_with_code]

    }

    // [START resend_verification]
    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .setForceResendingToken(token)     // ForceResendingToken from callbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    // [END resend_verification]

    // [START sign_in_with_phone]
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e(TAG, "signInWithCredential:success");
                            Toast.makeText(PhoneNumActivity.this, "인증 성공", Toast.LENGTH_SHORT).show();
                            FirebaseUser user=mAuth.getCurrentUser();
                            user.delete();

                            if(clicked.equals("normal")){
                                Log.e(TAG, "일반 로그인 시작");
                                Intent intent=new Intent(PhoneNumActivity.this, JoinActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("phoneNum", phoneNum);
                                intent.putExtras(bundle);
                                startActivity(intent);

                            }else if(clicked.equals("google")){
                                Log.e(TAG, "구글 로그인 시작");
                                signIn();
                            }
                            //updateUI(user);
                            updatePhoneNum();
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.e(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(PhoneNumActivity.this, "인증 실패! 재인증하여 주십시오", Toast.LENGTH_SHORT).show();
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }
    //[END sign_in_with_phone]

//    private void updateUI(FirebaseUser user) {
//        if (user != null) {
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
//
//    }

    public void updatePhoneNum(){
        Log.e(TAG, "인증제출");
        Log.e(TAG, "현재 phoneNum은"+phoneNum);
        DocumentReference userRef = db.collection("user").document(mAuth.getUid());

        userRef
                .update("phoneNum", phoneNum)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e(TAG, "핸드폰 db넣기 성공");



                        Intent intent = new Intent(PhoneNumActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "핸드폰번호 업데이트 못함", e);
                    }
                });

    }
    //전화전호 인증 END=================================================================

    //구글 로그인============================================================================
    private void signIn() {
        Log.e(TAG, "로그인창 띄웠음");
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e(TAG, "구글 계정 가져오기 ");

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

        Log.e(TAG, "파베에 구글 계정 넣기 ");

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            Snackbar.make(findViewById(R.id.layout_main), "Authentication Successed.", Snackbar.LENGTH_SHORT).show();
                            Log.e(TAG, "구글로 로그인 성공 ");

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
            account.setPhoneNum(phoneNum);

            db.collection("user").document(firebaseUser.getUid()).set(account);


            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();


        }
    }
    //구글 로그인 END============================================================================



}