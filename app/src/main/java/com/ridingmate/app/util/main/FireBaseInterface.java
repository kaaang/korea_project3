package com.ridingmate.app.util.main;

import android.net.UrlQuerySanitizer;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
// 파이어베이스 코드 모음
public class FireBaseInterface {

    public  static  mil m_interface = new mil_interface();

    public static  class mil_interface implements mil{
        private FirebaseDatabase firebaseDatabase;
        private DatabaseReference databaseReference;
        private FirebaseAuth  mfireFirebaseAuth;
        private FirebaseUser firebaseUser;
        private FirebaseFirestore  db;
        //주유 UI
        private  TextView tv_letter;
        private TextView tv_date;
        @Override
        public void InitFirebase() {
            firebaseDatabase = FirebaseDatabase.getInstance();
            mfireFirebaseAuth=FirebaseAuth.getInstance();
            firebaseUser=mfireFirebaseAuth.getCurrentUser();
            db = FirebaseFirestore.getInstance();
        }
        // 주유 파트
        public void uploadMileageData(String date, String gasStation, String litter, String price){
            if( date == null||gasStation == null||price == null||litter == null){
                Log.e("upload", "+++++++++++++++nodata");
                return;
            }
            else {
                Map<String,Object> input= new HashMap<>();
/*                input.put("uid", firebaseUser.getUid().toString());*/
                input.put("date",date);
                input.put("gasStation",gasStation);
                input.put("litter",litter);
                input.put("price",price);
                SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddhhmmss");
                String now = sd.format(new Date(System.currentTimeMillis()));
                db.collection("mileage").document(now).set(input);
            }
        }
        public TextView Tv_litter(TextView tv){
            tv_letter = tv;
            return tv;
        }
        public TextView Tv_date(TextView tv){
            tv_date = tv;
            return tv;
        }
        @Override
        public void downloadMileageData() {
            CollectionReference collectionReference = db.collection("mileage");
            QueryDocumentSnapshot documentSnapshot;
            collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot documentSnapshots) {
                    // Get the last visible document
                    DocumentSnapshot lastVisible = documentSnapshots.getDocuments().get(documentSnapshots.size() - 1);

                    tv_letter.setText(lastVisible.getData().get("litter").toString());
                    tv_date.setText(lastVisible.getData().get("date").toString());
                }
            });
        }
/*        @Override
        public void downloadMileageData() {
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            Query lastQuery = rootRef.child("mileage").limitToLast(1);
            Query date =  lastQuery.orderByKey();

*//*
           Log.e("down", "" + );*//*
        }*/

        // 정비 파트
        public void uploadMaintenanceData(String item, String date, String serviceCenter, String detail){
            if(date == null||item == null||serviceCenter == null||detail == null){
                Log.e("upload", "+++++++++++++++nodata");
                return;
            }
            else {
                Map<String,Object> input= new HashMap<>();
 /*               input.put("uid", firebaseUser.getUid().toString());*/
                input.put("item",item);
                input.put("date",date);
                input.put("serviceCenter",serviceCenter);
                input.put("detail",detail);
                db.collection("maintenance").add(input);
            }
        }
    }
    public interface mil{
        void InitFirebase();
        void uploadMileageData(String date, String gasStation,String litter, String price);
        TextView Tv_litter(TextView tv);
        TextView Tv_date(TextView tv);
        void downloadMileageData();
        void uploadMaintenanceData(String item, String date, String serviceCenter, String detail);
    }
}
