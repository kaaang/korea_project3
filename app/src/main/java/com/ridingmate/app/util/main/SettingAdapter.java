package com.ridingmate.app.util.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ridingmate.app.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.CustomViewHolder> {
    private ArrayList<MyBikeData> arrayList;
    private FragmentActivity activity;
    Context context;

    public SettingAdapter(ArrayList<MyBikeData> arrayList, FragmentActivity activity) {
        this.arrayList = arrayList;
        this.activity = activity;
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

        holder.nickname.setText(arrayList.get(position).getNickname());
        holder.company.setText(arrayList.get(position).getCompany());
        holder.model.setText(arrayList.get(position).getModel());
        holder.year.setText(arrayList.get(position).getYear());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(v -> {
            String curName=holder.nickname.getText().toString();
            Toast.makeText(v.getContext(),curName,Toast.LENGTH_SHORT).show();
        });


    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size():0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        protected TextView nickname;
        protected TextView company;
        protected TextView model;
        protected TextView year;

        public CustomViewHolder(View view) {
            super(view);
            this.nickname = (TextView)view.findViewById(R.id.nickname);
            this.company = (TextView)view.findViewById(R.id.company);
            this.model = (TextView)view.findViewById(R.id.model);
            this.year = (TextView)view.findViewById(R.id.year);
        }
    }
}
