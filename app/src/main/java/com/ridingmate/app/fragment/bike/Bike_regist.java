package com.ridingmate.app.fragment.bike;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ridingmate.app.R;

import org.jetbrains.annotations.NotNull;

public class Bike_regist extends Fragment {
    String TAG = getClass().getName();
    private FirebaseFirestore db=FirebaseFirestore.getInstance();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_bike_regist,container,false);






//        db.collection("brand")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        // 데이터를 가져오는 작업이 잘 동작했을 떄
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.e(TAG, document.getId() + " => " + document.getData());
//                            }
//                        }
//                            // 데이터를 가져오는 작업이 에러났을 때
//                        else {
//                            Log.w(TAG, "Error => ", task.getException());
//                        }
//                    }
//                });




//        DocumentReference docRef = db.collection("brand").document("brand").collection("");
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
//                if(task.isSuccessful()){
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        Log.e(TAG, "DocumentSnapshot data: " + document.getData()+"\n");
////                        Log.d(TAG,(String) document.get("Category"));
//                    } else {
//                        Log.d(TAG, "No such document");
//                    }
//                }else{
//                    Log.d(TAG, "get failed with ", task.getException());
//                }
//            }
//        });



//
//            db.collection("company")
//                    .whereEqualTo("mymy", 1212)
//                    .get()
//                    .addOnCompleteListener(task -> {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
//                                Log.e(TAG, "DocumentSnapshot data: " + documentSnapshot.getData()+"\n");
//                                String username = (String) documentSnapshot.getData().get("model");
//                                if(username == null){
////                                    Log.e("username", "설정된 닉네임이 없습니다.");
//                                } else {
////                                    Log.e("username", (String)documentSnapshot.getData().get("model"));
//                                }
////                                hashMap.put("code", documentSnapshot.getData().get("code"));
//                            }
//                        }
////                        DlogUtil.d(TAG, hashMap);
//                    })
//                    .addOnFailureListener(e -> e.printStackTrace());







        return view;
    }
}