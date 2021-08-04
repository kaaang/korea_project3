package com.ridingmate.app.fragment.main;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.ridingmate.app.R;
import com.ridingmate.app.activity.main.MainActivity;
import com.ridingmate.app.util.main.FireBaseInterface;
import com.ridingmate.app.util.main.MileageAdapter;
import com.ridingmate.app.util.main.MileageData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class Main_main extends Fragment {

    //카드뷰
    TextView tv_distance,tv_average,tv_bikename;
    ImageView cardViewBackground;

    //리사이클러뷰
    RecyclerView rv;
    MileageAdapter mileageAdapter;
    ArrayList<MileageData> mileageDataArrayList;

    // 팝업
    private EditText tx_litter, tx_price, tx_station, tx_distance;
    private Button btn_showGasStation;
    private TextView bt_ok, bt_list;
    // 날짜 선택
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private int mYear = 0, mMonth = 0, mDay = 0;
    private  String selected_date;

    MainActivity mainActivity= (MainActivity) MainActivity._main;
    LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_main, container, false);

        //리싸이클러뷰
        mileageDataArrayList=new ArrayList<>();

        rv=(RecyclerView)view.findViewById(R.id.main_mileage_RV);
        linearLayoutManager= new LinearLayoutManager(view.getContext());
        rv.setLayoutManager(linearLayoutManager);
        rv.addItemDecoration(new DividerItemDecoration(view.getContext(), 1));

        mileageAdapter=new MileageAdapter(mileageDataArrayList,getActivity(),mainActivity);
        rv.setAdapter(mileageAdapter);

        getMileage();

        //카드뷰
        tv_bikename=(TextView)view.findViewById(R.id.text_bikename);
        tv_distance=(TextView) view.findViewById(R.id.distance);
        tv_average=(TextView) view.findViewById(R.id.text_average);
        cardViewBackground=(ImageView)view.findViewById(R.id.card_background);
        showImage();

        ImageView imageView = view.findViewById(R.id.card_background);
        imageView.setColorFilter(R.color.black);
        FireBaseInterface.m_interface.Tv_litter((TextView)view.findViewById(R.id.milegae_litter));
        FireBaseInterface.m_interface.Tv_date((TextView)view.findViewById(R.id.milegae_date));
        FireBaseInterface.m_interface.InitFirebase();
        FireBaseInterface.m_interface.downloadMileageData();

        // -------------------------------------------------------------------주유 기록 등록
        btn_showGasStation = (Button) view.findViewById(R.id.btn_showGasStation);
        btn_showGasStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupWindow popupWindow = new PopupWindow(v);
                /* ----------------------------------------팝업---------------------------------------------------------------*/
                View popup = inflater.inflate(R.layout.fragment_main_mileage_regist, null);
                popupWindow.setContentView(popup);
                popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

                popupWindow.setFocusable(true);
                popupWindow.setTouchable(true);
                popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


                // 주유 기록 등록
                tx_litter= (EditText)popup.findViewById(R.id.milegae_text_litter);
                tx_price= (EditText)popup.findViewById(R.id.milegae_text_price);
                tx_station= (EditText)popup.findViewById(R.id.milegae_text_station);
                tx_distance= (EditText)popup.findViewById(R.id.milegae_text_distance);



                // ---------------------날짜 선택
                dateButton = (Button) popup.findViewById(R.id.date_picker);
                dateButton.setText(getTodaysDate());
                initDatePicker();

                dateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datePickerDialog.show();
                    }
                });

                // ---------------------팝업 버튼
                // 확인
                bt_ok = popupWindow.getContentView().findViewById(R.id.bt_ok);
                bt_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Firebase에 DB 삽입
                        FireBaseInterface.m_interface.uploadMileageData(dateButton.getText().toString(), tx_station.getText().toString(), tx_litter.getText().toString() , tx_price.getText().toString() ,tx_distance.getText().toString(),mainActivity.selectedBikeUid);
                        FireBaseInterface.m_interface.downloadMileageData();
                        popupWindow.dismiss();
                    }
                });
                // 목록으로
                bt_list = popupWindow.getContentView().findViewById(R.id.bt_list);
                bt_list.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                popupWindow.showAsDropDown(v);
            }
        });
        return view;
    }

    /* ----------------------------------------DatePicker 함수---------------------------------------------------------------*/
    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                makeDateString(day, month, year);
                dateButton.setText(selected_date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(MainActivity._main, style, dateSetListener, year, month, day);
        // datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()); 현재 날짜 기준이 max

    }

    private String makeDateString(int day, int month, int year) {
        selected_date = year + "년 " +month + "월 "  + day + "일";
        return year + "년 " +month + "월 "  + day + "일";
    }









    public void setCard(QueryDocumentSnapshot list){

        Map<String,Object> temp = list.getData();

        tv_bikename.setText((String) temp.get("nickname"));
        tv_distance.setText((String) temp.get("driven"));
        tv_average.setText("지원예정");

    }

    public void getMileage(){
        mileageDataArrayList.clear();
        MainActivity main= (MainActivity) MainActivity._main;
        Log.e("asd","문서커먼"+mainActivity.selectedBikeUid);
        FirebaseFirestore.getInstance().collection("mileage")
                .whereEqualTo("uid",mainActivity.selectedBikeUid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Log.e("asd","문서커먼");
                                Map<String,Object>map= document.getData();
                                MileageData data=new MileageData((String) map.get("litter"),(String)map.get("distance"),(String)map.get("date"));
                                mileageDataArrayList.add(data);
                                mileageAdapter.notifyDataSetChanged();
                            }
                        }




                    }
                });
    }
    public void showImage(){
        String folder;
        String image;

        for (int i=0 ;i<mainActivity.mybike.size();i++){
            QueryDocumentSnapshot selected= (QueryDocumentSnapshot) mainActivity.mybike.get(i);
            String str= selected.getId();

            if (str.equals(mainActivity.selectedBikeUid)){
                folder= (String) selected.get("uid");
                image= (String) selected.get("image");
                Log.e("asd","Uri : >>>>>>>>>>>>"+mainActivity.mybike.size()+"       "+str+"    "+mainActivity.selectedBikeUid);
                Log.e("asd","image : >>>>>>>>>>>>"+image+"    "+folder);
                FirebaseStorage storage=FirebaseStorage.getInstance();
                storage.getReference().child("Bike_img/"+folder+"/"+image+".jpg")
                        .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.e("asd","Uri : >>>>>>>>>>>>"+uri);

                        Glide
                                .with(getContext())
                                .load(uri) // the uri you got from Firebase
                                .centerCrop()
                                .into(cardViewBackground); //Your imageView variable
                    }

                });
            }
        }



    }

}