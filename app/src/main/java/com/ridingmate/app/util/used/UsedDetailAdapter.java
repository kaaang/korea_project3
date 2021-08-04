package com.ridingmate.app.util.used;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.bumptech.glide.Glide;
import com.ridingmate.app.R;
import com.ridingmate.app.activity.main.MainActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class UsedDetailAdapter extends RecyclerView.Adapter<UsedDetailAdapter.CustomViewHolder> {

    private ArrayList<UsedDetailData> arrayList;
    private FragmentActivity activity;
    Context context;

    public UsedDetailAdapter(ArrayList<UsedDetailData> arrayList, FragmentActivity activity, MainActivity main) {
        this.arrayList = arrayList;
        this.activity= activity;

    }

    @NonNull
    @NotNull
    @Override
    public UsedDetailAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_used_detail,parent,false);

        context= view.getContext();
        UsedDetailAdapter.CustomViewHolder holder=new UsedDetailAdapter.CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull UsedDetailAdapter.CustomViewHolder holder, int position) {
        Log.e("asdf","나는존잘신혁");

        Glide.with(holder.itemView.getContext())
                .load(arrayList.get(position).getUsed_thumb())
                .into(holder.used_thumb);

        holder.itemView.setTag(position);
    }



    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size():0);
    }



    public class CustomViewHolder extends RecyclerView.ViewHolder{
        protected ImageView used_thumb;


        public CustomViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.used_thumb = (ImageView)itemView.findViewById(R.id.imageSlider);
        }
    }



}
