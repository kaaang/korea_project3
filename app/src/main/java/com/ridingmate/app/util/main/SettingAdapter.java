package com.ridingmate.app.util.main;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ridingmate.app.R;
import com.ridingmate.app.activity.main.MainActivity;
import com.ridingmate.app.fragment.main.Main_setting_mybike_detail;
import com.ridingmate.app.util.pageAdapter.PageAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.CustomViewHolder> {
    private ArrayList<MyBikeData> arrayList;
    private FragmentActivity activity;
    Context context;
    MainActivity main;

    public SettingAdapter(ArrayList<MyBikeData> arrayList, FragmentActivity activity, MainActivity main) {
        this.arrayList = arrayList;
        this.activity = activity;
        this.main=main;
    }

    @NonNull
    @NotNull
    @Override
    public SettingAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_setting_bikelist,parent,false);

        context= view.getContext();
        CustomViewHolder holder=new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SettingAdapter.CustomViewHolder holder, int position) {


        holder.bikeId.setText(arrayList.get(position).getBikeId());
        holder.nickname.setText(arrayList.get(position).getNickname());
        holder.company.setText(arrayList.get(position).getCompany());
        holder.model.setText(arrayList.get(position).getModel());
        holder.year.setText(arrayList.get(position).getYear());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(v -> {
            main.myBikeId =holder.bikeId.getText().toString();
            Log.e("@@@@@@@@@@@@@@@@@", holder.bikeId.getText().toString());

            main.showPage(11);


            Main_setting_mybike_detail setJava=(Main_setting_mybike_detail)PageAdapter.getInstance(main).pages[11];
            Log.e("@@@@@@@@@@@@@@@@@","    "+setJava.bike_regist_companySpinner);
            if(setJava.bike_regist_companySpinner!=null){
                setJava.selectedBike();
            }



        });


    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size():0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        protected  TextView bikeId;
        protected TextView nickname;
        protected TextView company;
        protected TextView model;
        protected TextView year;

        public CustomViewHolder(View view) {
            super(view);
            this.bikeId= (TextView)view.findViewById(R.id.bikeId);
            this.nickname = (TextView)view.findViewById(R.id.nickname);
            this.company = (TextView)view.findViewById(R.id.company);
            this.model = (TextView)view.findViewById(R.id.model);
            this.year = (TextView)view.findViewById(R.id.year);
        }
    }
}
