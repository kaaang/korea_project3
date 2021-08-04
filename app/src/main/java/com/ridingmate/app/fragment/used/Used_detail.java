package com.ridingmate.app.fragment.used;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.ridingmate.app.R;
import com.ridingmate.app.activity.main.MainActivity;
import com.ridingmate.app.util.used.UsedDetailAdapter;
import com.ridingmate.app.util.used.UsedDetailData;
import com.ridingmate.app.util.used.UsedListAdapter;
import com.ridingmate.app.util.used.UsedListData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Used_detail extends Fragment {
    MainActivity main;
    private String used_id;

    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private String folder;
    private FirebaseAuth mfireFirebaseAuth;
    private FirebaseUser firebaseUser;


    private TextView used_detail_writer;
    private TextView used_detail_area;
    private TextView used_detail_time;
    private TextView used_detail_content;

    ViewPager2 used_detail_photo;
    private ArrayList<UsedDetailData> arrayList;
    private UsedDetailAdapter usedDetailAdapter;


    public String TAG;



    //버튼관련
    Button show, update, delete;



    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mfireFirebaseAuth= FirebaseAuth.getInstance();
        firebaseUser=mfireFirebaseAuth.getCurrentUser();

        TAG = getClass().getName();
        arrayList=new ArrayList<>();
        main = (MainActivity) getContext();
        usedDetailAdapter = new UsedDetailAdapter(arrayList,getActivity(),main);

        View view=inflater.inflate(R.layout.fragment_used_detail,container,false);


        db=FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();



        used_id = main.data;

        used_detail_writer=view.findViewById(R.id.used_detail_writer);
        used_detail_area=view.findViewById(R.id.used_detail_area);
        used_detail_time=view.findViewById(R.id.used_detail_time);
        used_detail_content=view.findViewById(R.id.used_detail_content);

        used_detail_photo = view.findViewById(R.id.used_detail_photo);

        used_detail_photo.setAdapter(usedDetailAdapter);

        show = view.findViewById(R.id.show);
        update = view.findViewById(R.id.update);
        delete = view.findViewById(R.id.delete);

        update.onVisibilityAggregated(false);
        delete.onVisibilityAggregated(false);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });





        reload(used_id);

        return view;
    }



    public void reload(String used_id){
        arrayList.clear();
        db.collection("used").document(used_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    db.collection("user").whereEqualTo("idToken",document.get("UID")).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                            String writer=null;
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot user : task.getResult()) {
//                                    Log.d(TAG, user.getId() + " =>>>>>>> " + user.getData().get("name"));
                                    writer=user.getData().get("name").toString();
                                    Log.e(TAG,"@@@@@@반복문");

                                }
                                Log.e(TAG,"@@@@@@도큐먼트"+document.getData().toString());
                                used_detail_writer.setText(writer);
                                used_detail_area.setText(document.getData().get("area").toString());
                                used_detail_time.setText(document.getData().get("day").toString());
                                used_detail_content.setText(document.getData().get("content").toString());



                                if(document.getData().get("UID").toString().equals(firebaseUser.getUid())){
                                    Log.e("접근함","ㅋㅋㅋㅋㅋ");
                                    myUsed();
                                    update.onVisibilityAggregated(true);
                                    delete.onVisibilityAggregated(true);

                                }else {
                                    update.onVisibilityAggregated(false);
                                    delete.onVisibilityAggregated(false);
                                }


                                storage.getReference().child("Used_img/"+
                                        document.getData().get("UID").toString()+"/"+
                                        document.getData().get("used_id")).listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                                    @Override
                                    public void onSuccess(ListResult listResult) {
                                        for (StorageReference item : listResult.getItems()) {
                                            Log.e(TAG,"@@@@@@"+item.toString());
                                            Log.e(TAG,"@@@@@@"+item.getName());
                                            storage.getReference().child("Used_img/"+
                                                    document.getData().get("UID").toString()+"/"+
                                                    document.getData().get("used_id")+"/"+
                                                    item.getName()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    UsedDetailData usedDetailData = new UsedDetailData();
                                                    usedDetailData.setUsed_thumb(uri);
                                                    arrayList.add(usedDetailData);
                                                    Log.e("asdf","sksms rowhswkftlsurl");
                                                    usedDetailAdapter.notifyDataSetChanged();



                                                }

                                            });
                                        }
                                    }

                                })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull @NotNull Exception e) {
                                                Log.e(TAG,"@@@@@@실패함");
                                            }
                                        });






                            }
                        }
                    });
                } else {
                    Log.e(TAG, "게시글 가져오기 실패");
                }
            }
        });
    }




    public void myUsed(){
        Log.e(TAG,"들어옴");

    }

    public void show(){

    }

    public void update(){

    }
    public void delete(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("삭제 확인");
        builder.setMessage("게시글을 삭제하시겠습니까?");

        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }





}
