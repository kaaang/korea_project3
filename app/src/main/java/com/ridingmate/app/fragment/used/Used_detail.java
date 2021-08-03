package com.ridingmate.app.fragment.used;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ridingmate.app.R;
import com.ridingmate.app.activity.main.MainActivity;
import com.ridingmate.app.util.used.UsedListData;

import org.jetbrains.annotations.NotNull;

public class Used_detail extends Fragment {
    MainActivity main;
    private String used_id;

    private FirebaseFirestore db;


    private TextView used_detail_writer;
    private TextView used_detail_area;
    private TextView used_detail_time;
    private TextView used_detail_content;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String TAG = getClass().getName();

        View view=inflater.inflate(R.layout.fragment_used_detail,container,false);


        db=FirebaseFirestore.getInstance();


        main = (MainActivity) getContext();
        used_id = main.data;

        used_detail_writer=view.findViewById(R.id.used_detail_writer);
        used_detail_area=view.findViewById(R.id.used_detail_area);
        used_detail_time=view.findViewById(R.id.used_detail_time);
        used_detail_content=view.findViewById(R.id.used_detail_content);


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

                                }
                                used_detail_writer.setText(writer);
                                used_detail_area.setText("test");
                                used_detail_time.setText("test");
                                used_detail_content.setText(document.getData().get("content").toString());

                            }
                        }
                    });
                } else {
                    Log.e(TAG, "게시글 가져오기 실패");
                }
            }
        });



        return view;
    }
}
