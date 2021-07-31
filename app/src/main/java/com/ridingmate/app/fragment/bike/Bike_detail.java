package com.ridingmate.app.fragment.bike;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.ridingmate.app.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Map;

public class Bike_detail extends Fragment {
    String TAG = getClass().getName();

    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = null;



    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_bike_detail,container,false);

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
//                        // 데이터를 가져오는 작업이 에러났을 때
//                        else {
//                            Log.e(TAG, "Error => ", task.getException());
//                        }
//                    }
//                });

//        db.collection("bike_doc").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful()){
//                    int i=0;
//                    for(QueryDocumentSnapshot document:task.getResult()) {
//                        Log.e(TAG, "쿼리 가져오기 성공"+document.getId()+"\n"+document.getData()+document);
//                        jsonObject = new JSONObject((Map) document);
//                        i++;
//                        if(i==10) break;
//                    }
//                }else{
//                    Log.e(TAG,"쿼리 가져오기 실패");
//                }
//            }
//        });

//        DocumentReference docRef = db.collection("bike_doc").document("01AWqTWMk97QORn1iYyI");
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
//                if(task.isSuccessful()){
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        Log.d(TAG, "DocumentSnapshot data: " + document.getData()+"\n");
//                        Log.d(TAG,(String) document.get("Category"));
//                    } else {
//                        Log.d(TAG, "No such document");
//                    }
//                }else{
//                    Log.d(TAG, "get failed with ", task.getException());
//                }
//            }
//        });

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=mAuth.getCurrentUser();
        String token = firebaseUser.getUid();
        Log.e(TAG, "token : "+token);

        Bike_regist_DAO data = new Bike_regist_DAO();
//        data.setTest("test");

        db.collection("user").document(token).set(data, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "DocumentSnapshot successfully written!");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
        });










        return view;
    }
}
