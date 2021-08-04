package com.ridingmate.app.fragment.used;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
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
import com.ridingmate.app.util.used.UsedListAdapter;
import com.ridingmate.app.util.used.UsedListData;

import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Used_list extends Fragment {
    private String TAG = getClass().getName();

    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;



    private ArrayList<UsedListData> arrayList;
    private HashMap<String, String> dataList;


    private UsedListAdapter usedListAdapter;
    private ImageButton write_button;

    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private String folder;

    public MainActivity main;



    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_used_list,container,false);

        main=(MainActivity) getContext();

        db=FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
//        folder = FirebaseAuth.getInstance().getCurrentUser().getUid();


        recyclerView=(RecyclerView)view.findViewById(R.id.used_RV);
        linearLayoutManager= new LinearLayoutManager(view.getContext());


        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), 1));

        arrayList=new ArrayList<>();
        dataList = new HashMap<>();

        usedListAdapter=new UsedListAdapter(arrayList,getActivity(),main);
        recyclerView.setAdapter(usedListAdapter);










    reload();





        
        
        
        
        //중고거래 글작성 페이지로 이동
        write_button=view.findViewById(R.id.write_button);
        write_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity main =  (MainActivity) getContext();
                main.showPage(6);
            }
        });






        return view;
    }

    public void reload(){
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.detach(this).attach(this).commit();
//        Log.e(TAG,"@@@@@@@@@@@재시작");
        //모든 문서 가져오기
        db.collection("used")
                .orderBy("used_id")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            arrayList.clear();
                            for(QueryDocumentSnapshot document : task.getResult()){
//                                Log.e(TAG,"가져온 게시글 데이터 : "+(String) document.getData().get("welth"));
                                if(document.getData().get("number") == null) {
                                    folder = document.getData().get("UID").toString();


                                    storage.getReference().child("Used_img/"+folder+"/"+document.getId()+"/"+(String) document.getData().get("0img"))
                                            .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {



                                            Log.e(TAG,"Uri : >>>>>>>>>>>>"+uri);
                                            UsedListData usedListData = new UsedListData();
                                            usedListData.setUsed_thumb(uri);
                                            usedListData.setUsed_title((String) document.getData().get("title"));
                                            usedListData.setArea((String) document.getData().get("area"));
                                            usedListData.setTime((String) document.getData().get("day"));
                                            usedListData.setUsed_welth((String) document.getData().get("welth"));

                                            usedListData.setUsed_id((String)document.getData().get("used_id"));

                                            db.collection("mybike").document(document.getData().get("bike_id").toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                    usedListData.setModel_type(documentSnapshot.getData().get("model").toString());
                                                    usedListData.setUsed_comment(documentSnapshot.getData().get("year").toString());
                                                    arrayList.add(usedListData);
                                                    usedListAdapter.notifyDataSetChanged();
                                                }
                                            });


                                        }
                                    });


                                }
                            }
                            MainActivity.showPage(5);

                        }else{
                            Log.e(TAG,"게시글 가져오기 실패");
                        }
                    }
                });

    }




}
