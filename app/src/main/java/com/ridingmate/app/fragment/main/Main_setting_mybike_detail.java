package com.ridingmate.app.fragment.main;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.ridingmate.app.R;
import com.ridingmate.app.activity.main.MainActivity;
import com.ridingmate.app.util.pageAdapter.PageAdapter;
import com.ridingmate.app.util.used.UsedListData;

import java.util.HashMap;
import java.util.Map;

public class Main_setting_mybike_detail extends Fragment {
    String TAG="Main_setting_mybike_detail";

    //파이어베이스 객체
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    MainActivity mainActivity;


    public EditText bike_regist_companySpinner, bike_regist_modelSpinner, bike_regist_yearSpinner;
    EditText bike_regist_distance, bike_regist_bikeNickname;
    Button bike_edit_button, bike_regist_cancel;
    ImageView imageView;

    Map<String,Object> selected;
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main_mybike_detail,container,false);

        mainActivity=(MainActivity)MainActivity._main;

        //파이어 베이스 객체 생성
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        db=FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();


        bike_regist_companySpinner=(EditText) view.findViewById(R.id.bike_regist_companySpinner);
        bike_regist_modelSpinner=(EditText)view.findViewById(R.id.bike_regist_modelSpinner);
        bike_regist_yearSpinner=(EditText)view.findViewById(R.id.bike_regist_yearSpinner);
        bike_regist_distance=(EditText)view.findViewById(R.id.bike_regist_distance);
        bike_regist_bikeNickname=(EditText)view.findViewById(R.id.bike_regist_bikeNickname);
        bike_edit_button=(Button)view.findViewById(R.id.bike_edit_button);
        bike_regist_cancel=(Button)view.findViewById(R.id.bike_regist_cancel);
        imageView= (ImageView) view.findViewById(R.id.imageView);


        bike_edit_button.setOnClickListener(v -> {
            editMyBike();
        });

        bike_regist_cancel.setOnClickListener(v -> {
            mainActivity.showPage(10);
        });

        selectedBike();


        return view;
    }

    private void editMyBike() {


        Map<String, String> data = new HashMap<>();
        data.put("driven",bike_regist_distance.getText().toString());
        data.put("nickname",bike_regist_bikeNickname.getText().toString());



        db.collection("mybike").document(mainActivity.myBikeId)
                .update(
                        "driven", bike_regist_distance.getText().toString(),
                        "nickname",bike_regist_bikeNickname.getText().toString()
                )
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity._main, "수정 성공",Toast.LENGTH_SHORT).show();



                        Main_setting setPage=(Main_setting) PageAdapter.getInstance(mainActivity).pages[10];
                        setPage.reload();



                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });


    }


    public void selectedBike(){
        imageView.setImageResource(android.R.color.transparent);

        for (int i=0 ;i<mainActivity.mybike.size();i++){
            QueryDocumentSnapshot qds=(QueryDocumentSnapshot) mainActivity.mybike.get(i);
            Log.e("abcd",qds.getId()+"aaaa");
            Log.e("abcd",mainActivity.myBikeId+"bbbb");


            if (qds.getId().equals(mainActivity.myBikeId)){
                selected=qds.getData();
                Log.e("abcd","cccc"+selected);
                bike_regist_companySpinner.setText((String) selected.get("company"));
                bike_regist_modelSpinner.setText((String) selected.get("model"));
                bike_regist_yearSpinner.setText((String) selected.get("year"));
                bike_regist_distance.setText((String) selected.get("driven"));
                bike_regist_bikeNickname.setText((String) selected.get("nickname"));

                showImage(selected);
            }

        }


    }



    public void showImage(Map<String, Object> selected){
        String folder= (String) selected.get("uid");
        String image= (String) selected.get("image");
        Log.e("agag",folder);
        Log.e("agag",image);



        storage.getReference().child("Bike_img/"+folder+"/"+image)
                .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.e(TAG,"Uri : >>>>>>>>>>>>"+uri);

                Glide
                        .with(getContext())
                        .load(uri) // the uri you got from Firebase
                        .centerCrop()
                        .into(imageView); //Your imageView variable
            }

        });
    }




}
