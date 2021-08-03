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

import java.util.ArrayList;
import java.util.Map;

public class Bike_detail extends Fragment {
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    Map<String,Object> map;
    TextView Overall_height_mm,Dry_weight_kg,Overall_width_mm,Fuel_capacity_liters,Seat_height_mm,Front_tyre,Wheelbase_mm,Rear_tyre,Overall_length_mm,HP_rpm,Bore_Stroke,Torque,Compression_Ratio,Gearbox,Front_suspension,Rear_suspension,Front_brakes,Rear_brakes;
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_bike_detail,container,false);

        Overall_height_mm =(TextView) view.findViewById(R.id.Overall_height_mm);
        Dry_weight_kg =(TextView)view.findViewById(R.id.Dry_weight_kg);
        Overall_width_mm =(TextView)view.findViewById(R.id.Overall_width_mm);
        Fuel_capacity_liters =(TextView)view.findViewById(R.id.Fuel_capacity_liters);
        Seat_height_mm =(TextView)view.findViewById(R.id.Seat_height_mm);
        Front_tyre =(TextView)view.findViewById(R.id.Front_tyre);
        Wheelbase_mm =(TextView)view.findViewById(R.id.Wheelbase_mm);
        Rear_tyre =(TextView)view.findViewById(R.id.Rear_tyre);
        Overall_length_mm =(TextView)view.findViewById(R.id.Overall_length_mm);
        HP_rpm =(TextView)view.findViewById(R.id.HP_rpm);
        Bore_Stroke =(TextView)view.findViewById(R.id.Bore_Stroke);
        Torque =(TextView)view.findViewById(R.id.Torque);
        Compression_Ratio =(TextView)view.findViewById(R.id.Compression_Ratio);
        Gearbox =(TextView)view.findViewById(R.id.Gearbox);
        Front_suspension =(TextView)view.findViewById(R.id.Front_suspension);
        Rear_suspension =(TextView)view.findViewById(R.id.Rear_suspension);
        Front_brakes =(TextView)view.findViewById(R.id.Front_brakes);
        Rear_brakes =(TextView)view.findViewById(R.id.Rear_brakes);


        setDetail();

        return view;
    }

    public void getDetail(QueryDocumentSnapshot list){

        String company= (String) list.getData().get("company");
        String model= (String) list.getData().get("model");
        String year= (String) list.getData().get("year");

        db.collection("bike_doc").document("bike_doc").collection(company).document(company).collection(model).document(year)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            Log.e("asd", "(String)"+ task.getResult().getData().get("Power_Benchmark_RPM"));
                            map=task.getResult().getData();
                            if (Overall_height_mm!=null){
                                setDetail();
                            }
                        }else {

                        }
                    }
                });
    }
    public void setDetail(){

        Overall_height_mm.setText(map.get("Overall_height_mm").toString());
        Dry_weight_kg.setText(map.get("Dry_weight_kg").toString());
        Overall_width_mm.setText(map.get("Overall_width_mm").toString());
        Fuel_capacity_liters.setText(map.get("Fuel_capacity_liters").toString());
        Seat_height_mm.setText(map.get("Seat_height_mm").toString());
        Front_tyre.setText(map.get("Front_tyre").toString());
        Wheelbase_mm.setText(map.get("Wheelbase_mm").toString());
        Rear_tyre.setText(map.get("Rear_tyre").toString());
        Overall_length_mm.setText(map.get("Overall_length_mm").toString());

        HP_rpm.setText(map.get("Power_HP").toString()+"/"+map.get("Power_Benchmark_RPM").toString());
        Bore_Stroke.setText(map.get("Bore_mm").toString()+"x"+map.get("Stroke_mm").toString());

        // 토크 밴치마크 없음..
        Torque.setText(map.get("Torque_Nm").toString());

        Compression_Ratio.setText(map.get("Compression_Ratio").toString());
        Gearbox.setText(map.get("Gearbox").toString());
        Front_suspension.setText(map.get("Front_suspension").toString());
        Rear_suspension.setText(map.get("Rear_suspension").toString());
        Front_brakes.setText(map.get("Front_brakes").toString());
        Rear_brakes.setText(map.get("Rear_brakes").toString());
    }
}
