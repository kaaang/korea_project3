package com.ridingmate.app.util.main;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ridingmate.app.R;
import com.ridingmate.app.activity.main.MainActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MileageAdapter extends RecyclerView.Adapter<MileageAdapter.CustomViewHolder> {
    private ArrayList<MileageData> arrayList;
    private FragmentActivity activity;
    Context context;
    MainActivity main;

    public MileageAdapter(ArrayList<MileageData> arrayList, FragmentActivity activity, MainActivity main) {
        this.arrayList = arrayList;
        this.activity= activity;
        this.main = main;
        Log.e("asd","  어댑터 뉴  ");
    }

    @NonNull
    @NotNull
    @Override
    public CustomViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_main_mileage,parent,false);
        Log.e("asd","    크리에이트뷰홀더");
        context= view.getContext();
        CustomViewHolder holder=new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MileageAdapter.CustomViewHolder holder, int position) {


        Log.e("asd",Integer.toString(arrayList.size()));

        holder.main_mileage_oil.setText(arrayList.get(position).getMain_mileage_oil());
        holder.main_mileage_driven.setText(arrayList.get(position).getMain_mileage_driven());
        holder.main_mileage_date.setText(arrayList.get(position).getMain_mileage_date());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(v -> {
            Log.e("asd","    "+position);
        });
    }



    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size():0);
    }



    public class CustomViewHolder extends RecyclerView.ViewHolder{

        protected TextView main_mileage_oil;
        protected TextView main_mileage_driven;
        protected TextView main_mileage_date;

        public CustomViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.main_mileage_oil = (TextView)itemView.findViewById(R.id.main_mileage_oil);
            this.main_mileage_driven = (TextView)itemView.findViewById(R.id.main_mileage_driven);
            this.main_mileage_date = (TextView)itemView.findViewById(R.id.main_mileage_date);


        }
    }





}
