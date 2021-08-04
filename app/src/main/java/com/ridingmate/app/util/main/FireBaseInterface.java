package com.ridingmate.app.util.main;

import android.app.AlertDialog;
import android.content.Context;
import android.net.UrlQuerySanitizer;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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
import com.ridingmate.app.activity.main.MainActivity;
import com.ridingmate.app.fragment.main.Main_maintenance_list;
import com.ridingmate.app.util.main.maintenance.Maintenance;
import com.ridingmate.app.util.main.maintenance.MaintenanceConstants;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// 파이어베이스 코드 모음
public class FireBaseInterface {

    public static mil m_interface = new mil_interface();

    public static class mil_interface implements mil {
        private FirebaseDatabase firebaseDatabase;
        private DatabaseReference databaseReference;
        private FirebaseAuth mfireFirebaseAuth;
        private FirebaseUser firebaseUser;
        private FirebaseFirestore db;
        //주유 UI
        private TextView tv_litter;
        private TextView tv_date;
        // 정비 리스트 UI
        private TextView item_maintenance;
        private TextView item_maintenance_date;
        private TextView item_maintenance_location;
        private TextView item_maintenance_detail;

        String TAG = this.getClass().getName();

        public void InitFirebase() {
            firebaseDatabase = FirebaseDatabase.getInstance();
            mfireFirebaseAuth = FirebaseAuth.getInstance();
            firebaseUser = mfireFirebaseAuth.getCurrentUser();
            db = FirebaseFirestore.getInstance();
        }

        // ---------------------------------------------------------------주유 파트

        public TextView Tv_litter(TextView tv) {
            tv_litter = tv;
            return tv;
        }

        public TextView Tv_date(TextView tv) {
            tv_date = tv;
            return tv;
        }

        // 게시글 등록
        // 주유 파트
        public void uploadMileageData(String date, String gasStation, String litter, String price, String distance, String uid){
            if( date == null||gasStation == null||price == null||litter == null){
                //  Log.e("upload", "+++++++++++++++nodata");
                return;
            }
            else {
                Map<String,Object> input= new HashMap<>();
                input.put("uid", uid);
                input.put("date",date);
                input.put("gasStation",gasStation);
                input.put("litter",litter);
                input.put("price",price);
                input.put("distance",distance);
                SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddhhmmss");
                String now = sd.format(new Date(System.currentTimeMillis()));
                db.collection("mileage").document(now).set(input);
            }
        }

        // 게시글 가져오기
        public void downloadMileageData(String str) {
            CollectionReference collectionReference = db.collection("mileage");
            QueryDocumentSnapshot documentSnapshot;
            collectionReference.whereEqualTo("uid", str)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot documentSnapshots) {
                    // Get the last visible document
                    DocumentSnapshot lastVisible = documentSnapshots.getDocuments().get(documentSnapshots.size() - 1);

                    tv_litter.setText(lastVisible.getData().get("litter").toString());
                    tv_date.setText(lastVisible.getData().get("date").toString());
                }
            });
        }

        // ---------------------------------------------------------------정비 파트
        public TextView item_maintenance(TextView view) {
            item_maintenance_date = view;
            return view;
        }

        public TextView item_maintenance_date(TextView view) {
            item_maintenance = view;
            return view;
        }

        public TextView item_maintenance_location(TextView view) {
            item_maintenance_location = view;
            return view;
        }
        public TextView item_maintenance_detail(TextView view){
            item_maintenance_detail= view;
            return  view;
        }

        // 게시글 등록
        public void uploadMaintenanceData(String item, String date, String serviceCenter, String detail, String uid) {
            if (date == null || item == null || serviceCenter == null || detail == null) {
                /*                Log.e("upload", "+++++++++++++++nodata");*/
                return;
            } else {
                Map<String, Object> input = new HashMap<>();
                input.put("uid", uid);
                input.put("item", item);
                input.put("date", date);
                input.put("serviceCenter", serviceCenter);
                input.put("detail", detail);
                if(MaintenanceConstants.check_Update){
                    //document 찾아서 덮어 쓰기
                    db.collection("maintenance").document(Main_maintenance_list.arrayList.get(MaintenanceConstants.docu_id).document_ID).set(input);
                }
                else {
                    db.collection("maintenance").add(input);
                }

            }
        }
        // 게시글 삭제
        public void deleteMaintenanceData(String id){
            db.collection("maintenance").document(id)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {

                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "삭제 완료");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "삭제 실패", e);
                        }
                    });
        }

        // 게시글 가져오기
        public void downloadMaintenanceData(String cel_date) {
            db.collection("maintenance").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String str =  document.getData().get("date").toString();
                            if(cel_date.compareTo(str) == 0){
                                Main_maintenance_list.arrayList.add (new Maintenance(document.getId(),document.getData().get("item").toString(),  document.getData().get("date").toString(), document.getData().get("serviceCenter").toString(), document.getData().get("detail").toString()));
                            }
                        }
                    }
                    Main_maintenance_list.UpdateRecyclerView();
                }
            });
        }

    }


    // ***********************************인터페이스
    public interface mil {
        void InitFirebase();

        void uploadMileageData(String s, String date, String gasStation, String litter, String price, String uid);

        TextView Tv_litter(TextView tv);

        TextView Tv_date(TextView tv);

        void downloadMileageData(String str);

        void uploadMaintenanceData(String item, String date, String serviceCenter, String detail, String uid);

        TextView item_maintenance(TextView tv);

        TextView item_maintenance_date(TextView tv);

        TextView item_maintenance_location(TextView tv);

        TextView item_maintenance_detail(TextView tv);

        void downloadMaintenanceData(String date);
        void deleteMaintenanceData(String id);
    }
}