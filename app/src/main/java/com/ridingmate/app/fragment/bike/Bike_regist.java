package com.ridingmate.app.fragment.bike;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.ridingmate.app.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Bike_regist extends Fragment {
    String TAG = getClass().getName();
    private FirebaseFirestore db=FirebaseFirestore.getInstance();

    boolean isSelected=false;

    Map map=null;
    Map modelMap=null;
    Map yearMap=null;

    EditText et_distance;
    EditText et_nickname;

    Button regist;
    Button cancel;

    ImageView imageView;

    //스피너 관련
    Spinner companySpinner;
    Spinner modelSpinner;
    Spinner yearSpinner;

    //리스트와 어댑터
    ArrayList<String> companyArray=new ArrayList<>();
    ArrayAdapter<String> companyAdapter=null;
    ArrayList<String> modelArray=new ArrayList<>();
    ArrayAdapter<String> modelAdapter=null;
    ArrayList<String> yearArray=new ArrayList<>();
    ArrayAdapter<String> yearAdapter=null;

    String company;
    String model;
    String year;
    String driven;
    String nickname;
    String image;
    String userUid;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_bike_regist,container,false);

        userUid= FirebaseAuth.getInstance().getCurrentUser().getUid();

        //바인딩
        companySpinner=view.findViewById(R.id.bike_regist_companySpinner);
        modelSpinner=view.findViewById(R.id.bike_regist_modelSpinner);
        yearSpinner=view.findViewById(R.id.bike_regist_yearSpinner);
        et_distance=view.findViewById(R.id.bike_regist_distance);
        et_nickname=view.findViewById(R.id.bike_regist_bikeNickname);
        imageView=view.findViewById(R.id.imageView);

        regist =view.findViewById(R.id.bike_regist_button);
        cancel =view.findViewById(R.id.bike_regist_cancel);
        getCompanyDB();


        regist.setOnClickListener(v -> {
            driven=et_distance.getText().toString();
            nickname=et_nickname.getText().toString();
            image=Integer.toString(imageView.getId());


            if (isSelected==false){
                Toast.makeText(getContext(),"바이크 선택을 완료 해 주세요.",Toast.LENGTH_SHORT).show();
            }else if (driven.equals("")){
                Toast.makeText(getContext(),"주행거리를 입력 해 주세요.",Toast.LENGTH_SHORT).show();
            }else if (nickname.equals("")){
                Toast.makeText(getContext(),"닉네임을 입력 해 주세요.",Toast.LENGTH_SHORT).show();
            }else{
                insertBike();
            }

        });
        cancel.setOnClickListener(v -> {
            //뒤로 보내주오!!
        });

        return view;
    }



    private void getCompanyDB(){

        //디비 조회
        db.collection("bike_doc")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        // 데이터를 가져오는 작업이 잘 동작했을 떄
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.e(TAG, document.getId() + " => " + document.getData());
                                map=document.getData();
                            }

                            // 어레이에 값을 넣고 어뎁터를 만듬
                            //매개변수로 다 넘겨서 재사용성 강화
                            setAdapter(map,companyArray,companyAdapter,companySpinner,"제조사를 선택하세요.");

                            companySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (yearMap!=null){
                                        yearMap=null;
                                    }
                                    if (yearArray.size()>0){
                                        yearArray.clear();
                                    }
                                    if(modelMap!=null){
                                        modelMap=null;
                                    }
                                    if (modelArray.size()>0){
                                        modelArray.clear();
                                    }

                                    if(companyArray.get(position).equals("제조사를 선택하세요.")){

                                    }else {

                                        Log.e("asd",companyArray.get(position));
                                        company=companyArray.get(position);
                                        getModelDB();
                                        isSelected=false;
                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {}
                            });

                        }
                        // 데이터를 가져오는 작업이 에러났을 때
                        else {
                            Log.e(TAG, "Error => ", task.getException());
                        }
                    }
                });
    }
    private void getModelDB(){
        //디비 조회
        db.collection("bike_doc").document("bike_doc").collection(company)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        // 데이터를 가져오는 작업이 잘 동작했을 떄
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.e(TAG, document.getId() + " => " + document.getData());
                                modelMap=document.getData();
                            }

                            // 어레이에 값을 넣고 어뎁터를 만듬
                            //매개변수로 다 넘겨서 재사용성 강화
                            setAdapter(modelMap,modelArray,modelAdapter,modelSpinner,"차량 이름을 선택하세요.");

                            modelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (yearMap!=null){
                                        yearMap=null;
                                    }
                                    if (yearArray.size()>0){
                                        yearArray.clear();
                                    }
                                    if(modelArray.get(position).equals("차량 이름을 선택하세요.")){

                                    }else {
                                        Log.e("asd",modelArray.get(position));
                                        model=modelArray.get(position);
                                        getYearDB();
                                        isSelected=false;
                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {}
                            });

                        }
                        // 데이터를 가져오는 작업이 에러났을 때
                        else {
                            Log.e(TAG, "Error => ", task.getException());
                        }
                    }
                });
    }
    private void getYearDB(){
        db.collection("bike_doc").document("bike_doc").collection(company).document(company).collection(model)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            yearArray.add("연식을 선택하세요.");
                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.e("asd", document.getId() + " => " + document.getData());
                                yearArray.add(document.getId());
                            }
                            setAdapter(yearMap,yearArray,yearAdapter,yearSpinner,"연식을 선택하세요.");

                            yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if(yearArray.get(position).equals("연식을 선택하세요.")){

                                    }else {

                                        Log.e("asd",yearArray.get(position));
                                        year=yearArray.get(position);

                                        Log.e("asd",company+"  "+model+"  "+year);
                                        isSelected=true;
                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {}
                            });

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }
    private void setAdapter(Map map,ArrayList list,ArrayAdapter adapter,Spinner spinner,String topOne){
        //어레이에 값 넣기 숫자로 재 정렬...

        if (map!=null){
            list.add(topOne);
            for (int i=0;i<map.size();i++){
                String key=Integer.toString(i);
                list.add(map.get(key).toString());
            }
        }
        adapter=new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

    }
    private void insertBike() {
        Bike_regist_DAO data=new Bike_regist_DAO(company,model,year,driven,nickname,image,userUid);
        db.collection("mybike").add(data)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            DocumentReference document = task.getResult();
                            String bikeId = document.getId();
                            insertBikeInUser(bikeId);
                        }


                    }
                });

    }

    private void insertBikeInUser(String bikeId) {
        Map<String, Object> data = new HashMap<>();
        data.put(bikeId,"bike");

        db.collection("user").document(userUid)
                .set(data, SetOptions.merge());
    }
}