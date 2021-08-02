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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
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

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_used_list,container,false);

        db=FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        folder = FirebaseAuth.getInstance().getCurrentUser().getUid();


        recyclerView=(RecyclerView)view.findViewById(R.id.used_RV);
        linearLayoutManager= new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), 1));

        arrayList=new ArrayList<>();
        dataList = new HashMap<>();

        usedListAdapter=new UsedListAdapter(arrayList,getActivity());
        recyclerView.setAdapter(usedListAdapter);





        //모든 문서 가져오기
        db.collection("used")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
//                                Log.e(TAG,"가져온 게시글 데이터 : "+(String) document.getData().get("welth"));
                                if(document.getData().get("number") == null) {


                                    storage.getReference().child("Used_img/"+folder+"/"+(String) document.getData().get("0img"))
                                            .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {



                                            Log.e(TAG,"Uri : >>>>>>>>>>>>"+uri);
                                            UsedListData usedListData = new UsedListData();
                                            usedListData.setUsed_thumb(uri);
                                            usedListData.setUsed_title((String) document.getData().get("title"));
                                            usedListData.setUsed_welth((String) document.getData().get("welth"));
                                            usedListData.setModel_type("test");
                                            usedListData.setUsed_comment_cont("test");
                                            arrayList.add(usedListData);
                                            usedListAdapter.notifyDataSetChanged();
                                        }
                                    });


                                }
                            }

                        }else{
                            Log.e(TAG,"게시글 가져오기 실패");
                        }
                    }
                });










        
        
        
        
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
}
