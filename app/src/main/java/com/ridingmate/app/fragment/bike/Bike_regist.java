package com.ridingmate.app.fragment.bike;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.StorageReference;
import com.ridingmate.app.R;
import com.ridingmate.app.activity.main.MainActivity;
import com.ridingmate.app.fragment.used.UploadFile;
import com.ridingmate.app.util.used.UsedWriteData;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


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

    //????????? ?????? ??????
    ImageButton imageButton4;
    private static final int REQUEST_CODE=0;
    //storage??????
    private StorageReference mStorageRef;
    private Uri filePath;
    private List filePathArray;
    private FirebaseUser firebaseUser;
    private FirebaseAuth mfireFirebaseAuth;

    //????????? ??????
    Spinner companySpinner;
    Spinner modelSpinner;
    Spinner yearSpinner;

    //???????????? ?????????
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
        mfireFirebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=mfireFirebaseAuth.getCurrentUser();

        //?????????
        companySpinner=view.findViewById(R.id.bike_regist_companySpinner);
        modelSpinner=view.findViewById(R.id.bike_regist_modelSpinner);
        yearSpinner=view.findViewById(R.id.bike_regist_yearSpinner);
        et_distance=view.findViewById(R.id.bike_regist_distance);
        et_nickname=view.findViewById(R.id.bike_regist_bikeNickname);
        imageView=view.findViewById(R.id.imageView);
        imageButton4=view.findViewById(R.id.imageButton4);

        regist =view.findViewById(R.id.bike_regist_button);
        cancel =view.findViewById(R.id.bike_regist_cancel);
        getCompanyDB();




        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addImg();
            }
        });



        regist.setOnClickListener(v -> {
            driven=et_distance.getText().toString();
            nickname=et_nickname.getText().toString();


            if (isSelected==false){
                Toast.makeText(getContext(),"????????? ????????? ?????? ??? ?????????.",Toast.LENGTH_SHORT).show();
            }else if (driven.equals("")){
                Toast.makeText(getContext(),"??????????????? ?????? ??? ?????????.",Toast.LENGTH_SHORT).show();
            }else if (nickname.equals("")){
                Toast.makeText(getContext(),"???????????? ?????? ??? ?????????.",Toast.LENGTH_SHORT).show();
            }else{
                insertBike();
            }

        });
        cancel.setOnClickListener(v -> {
            //?????? ????????????!!
        });

        return view;
    }



    private void getCompanyDB(){

        //?????? ??????
        db.collection("bike_doc")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        // ???????????? ???????????? ????????? ??? ???????????? ???
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.e(TAG, document.getId() + " => " + document.getData());
                                map=document.getData();
                            }

                            // ???????????? ?????? ?????? ???????????? ??????
                            //??????????????? ??? ????????? ???????????? ??????
                            setAdapter(map,companyArray,companyAdapter,companySpinner,"???????????? ???????????????.");

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

                                    if(companyArray.get(position).equals("???????????? ???????????????.")){

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
                        // ???????????? ???????????? ????????? ???????????? ???
                        else {
                            Log.e(TAG, "Error => ", task.getException());
                        }
                    }
                });
    }
    private void getModelDB(){
        //?????? ??????
        db.collection("bike_doc").document("bike_doc").collection(company)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        // ???????????? ???????????? ????????? ??? ???????????? ???
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.e(TAG, document.getId() + " => " + document.getData());
                                modelMap=document.getData();
                            }

                            // ???????????? ?????? ?????? ???????????? ??????
                            //??????????????? ??? ????????? ???????????? ??????
                            setAdapter(modelMap,modelArray,modelAdapter,modelSpinner,"?????? ????????? ???????????????.");

                            modelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (yearMap!=null){
                                        yearMap=null;
                                    }
                                    if (yearArray.size()>0){
                                        yearArray.clear();
                                    }
                                    if(modelArray.get(position).equals("?????? ????????? ???????????????.")){

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
                        // ???????????? ???????????? ????????? ???????????? ???
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

                            yearArray.add("????????? ???????????????.");
                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.e("asd", document.getId() + " => " + document.getData());
                                yearArray.add(document.getId());
                            }
                            setAdapter(yearMap,yearArray,yearAdapter,yearSpinner,"????????? ???????????????.");

                            yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if(yearArray.get(position).equals("????????? ???????????????.")){

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
        //???????????? ??? ?????? ????????? ??? ??????...

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
        getBikeImgName();
        Bike_regist_DAO data=new Bike_regist_DAO(company,model,year,driven,nickname,image,userUid);
        db.collection("mybike").add(data)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            DocumentReference document = task.getResult();
                            String bikeId = document.getId();
                            insertBikeInUser(bikeId);
                            MainActivity main =  (MainActivity) getContext();
                            main.showPage(4);

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



    
    
    
    
    
    //????????? ????????? ??????
    public void addImg(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        if(requestCode == 0 && resultCode == RESULT_OK){
            filePath = data.getData();
            Log.e(TAG, "uri:" + filePath.toString());
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                imageView.onVisibilityAggregated(true);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void getBikeImgName(){
        BikeImgUpload  bikeImgUpload= new BikeImgUpload(getActivity(),firebaseUser);
        image=bikeImgUpload.uploadFile(filePath);
    }



}