package com.ridingmate.app.fragment.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.ridingmate.app.R;
import com.ridingmate.app.activity.main.MainActivity;
import com.ridingmate.app.activity.member.LoginActivity;
import com.ridingmate.app.activity.member.PhoneNumActivity;
import com.ridingmate.app.util.main.MyBikeData;
import com.ridingmate.app.util.main.SettingAdapter;
import com.ridingmate.app.util.used.UsedListData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Main_setting extends Fragment {

    private String TAG = getClass().getName();
    public MainActivity main;

    //파이어베이스 객체
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    TextView myName, myEmail, resign;
    EditText myPhoneNum, myAddr;
    Button logout;
    ImageView editName;


    PopupWindow popupWindow;

    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private ArrayList<MyBikeData> arrayList;
    public SettingAdapter bikeListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main_setting,container,false);
        main=(MainActivity) getActivity();


        //파이어 베이스 객체 생성
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        db=FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        //텍스트 객체생성
        myName=view.findViewById(R.id.myName);
        myEmail=view.findViewById(R.id.myEmail);
        myPhoneNum=view.findViewById(R.id.myPhoneNum);
        myAddr=view.findViewById(R.id.myAddr);
        resign=view.findViewById(R.id.resign);
        logout=view.findViewById(R.id.logout);
        editName=view.findViewById(R.id.editName);


        recyclerView=(RecyclerView)view.findViewById(R.id.bike_list);
        linearLayoutManager= new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), 1));
        arrayList=new ArrayList<>();

        reload();



        return view;
    }

    //내정보 가져오기
    private void getMyInfo() {

        DocumentReference docRef = db.collection("user").document(firebaseUser.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        myName.setText((String) document.get("name"));
                        myEmail.setText( (String) document.get("emailId"));
                        myPhoneNum.setText((String) document.get("phoneNum"));
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });





    }
    //내 바이크 모두 가져오기
    public void getBikeList(){
        arrayList.clear();

        db.collection("mybike")
                .whereEqualTo("uid", firebaseUser.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.e(TAG, document.getId() + " => " + document.getData());

                                MyBikeData myBikeData=new MyBikeData();
                                myBikeData.setBikeId((String) document.getId());
                                myBikeData.setNickname((String) document.getData().get("nickname"));
                                myBikeData.setCompany((String) document.getData().get("company"));
                                myBikeData.setModel((String) document.getData().get("model"));
                                myBikeData.setYear((String) document.getData().get("year"));
                                arrayList.add(myBikeData);
                                bikeListAdapter.notifyDataSetChanged();

                                MainActivity.showPage(10);

                            }
                        } else {
                            Log.e(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }




    //이름 변경 팝업
    public void popupView(View v, int res) {
        popupWindow=new PopupWindow(v);
        LayoutInflater layoutInflater = (LayoutInflater)getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View popup= layoutInflater.inflate(res, null);
        popupWindow.setContentView(popup);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setTouchable(true); // PopupWindow 위에서 Button의 Click이 가능하도록 setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setContentView(popup);

        //이름변경창 객체
        Button change=popupWindow.getContentView().findViewById(R.id.change);
        EditText et_name=popupWindow.getContentView().findViewById(R.id.et_name);
        TextView txt=popupWindow.getContentView().findViewById(R.id.txt);

        TextView cancel=popupWindow.getContentView().findViewById(R.id.cancel);

        change.setOnClickListener(v1 -> {
            changeName(et_name, txt);
        });



        cancel.setOnClickListener(v1 -> popupWindow.dismiss());

        popupWindow.showAsDropDown(v);

    }


    private void changeName(EditText et_name, TextView txt) {
        String name=et_name.getText().toString();
        Log.e("asd", myName+"내이름");
        Log.e("asd", name+"수정후 이름");


        if(myName.getText().toString().equals(name)){
            Toast.makeText(MainActivity._main, "다른 아이디를 입력하세요", Toast.LENGTH_SHORT).show();
            txt.setText("현재 닉네임과 같습니다.");
        }else{
            DocumentReference userRef = db.collection("user").document(firebaseAuth.getUid());
            userRef
                    .update("name", name)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            myName.setText(name);
                            popupWindow.dismiss();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "이름 업데이트 못함", e);
                        }
                    });

        }




    }

    public void reload(){
        //내기본정보 불러오기
        getMyInfo();
        getBikeList();



        bikeListAdapter=new SettingAdapter(arrayList,getActivity(), main);
        recyclerView.setAdapter(bikeListAdapter);
        bikeListAdapter.notifyDataSetChanged();

        resign.setOnClickListener(v -> {
            firebaseUser.delete();
        });

        logout.setOnClickListener(v -> {
            firebaseAuth.signOut();
            startActivity(new Intent(MainActivity._main, LoginActivity.class));

        });

        editName.setOnClickListener(v -> {
            popupView(v, R.layout.change_name);
        });


    }


}
