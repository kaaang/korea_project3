package com.ridingmate.app.util.used;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentFactory;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ridingmate.app.R;
import com.ridingmate.app.activity.main.MainActivity;
import com.ridingmate.app.fragment.used.Used_detail;
import com.ridingmate.app.fragment.used.Used_list;
import com.ridingmate.app.util.pageAdapter.PageAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class UsedListAdapter extends RecyclerView.Adapter<UsedListAdapter.CustomViewHolder> {
    private ArrayList<UsedListData> arrayList;
    private FragmentActivity activity;
    Context context;
    MainActivity main;

    public UsedListAdapter(ArrayList<UsedListData> arrayList, FragmentActivity activity, MainActivity main) {
        this.arrayList = arrayList;
        this.activity= activity;
        this.main = main;

    }

    @NonNull
    @NotNull
    @Override
    public CustomViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_used_list,parent,false);

        context= view.getContext();
        CustomViewHolder holder=new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull UsedListAdapter.CustomViewHolder holder, int position) {
//        holder.used_thumb.setImageBitmap(arrayList.get(position).getUsed_thumb());
//        Glide.with(context).load(arrayList.get(position).getUsed_thumb()).into(holder.used_thumb);

        Glide.with(holder.itemView.getContext())
                .load(arrayList.get(position).getUsed_thumb())
                .into(holder.used_thumb);

        Log.e("asd",arrayList.get(position).getUsed_thumb().toString());
        Log.e("asd",Integer.toString(arrayList.size()));

        holder.used_title.setText(arrayList.get(position).getUsed_title());
        holder.model_type.setText(arrayList.get(position).getModel_type());
        holder.used_comment.setText(arrayList.get(position).getUsed_comment());
        holder.used_welth.setText(arrayList.get(position).getUsed_welth());
        holder.area.setText(arrayList.get(position).getArea());
        holder.time.setText(arrayList.get(position).getTime());
        holder.used_id.setText(arrayList.get(position).getUsed_id());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(v -> {
            Used_detail used_detail=(Used_detail) PageAdapter.getInstance((FragmentActivity) MainActivity._main) .pages[7];
            main.data =holder.used_id.getText().toString();
            if(used_detail.TAG != null){
                used_detail.reload(holder.used_id.getText().toString());
            }
            MainActivity.showPage(7);
        });
    }



    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size():0);
    }



    public class CustomViewHolder extends RecyclerView.ViewHolder{
        protected ImageView used_thumb;
        protected TextView used_title;
        protected TextView model_type;
        protected TextView used_comment;
        protected TextView used_welth;
        protected TextView used_id;
        protected TextView area;
        protected TextView time;

        public CustomViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.used_thumb = (ImageView)itemView.findViewById(R.id.used_thumb);
            this.used_title = (TextView)itemView.findViewById(R.id.used_title);
            this.model_type = (TextView)itemView.findViewById(R.id.model_type);
            this.used_comment = (TextView)itemView.findViewById(R.id.used_comment);
            this.used_welth = (TextView)itemView.findViewById(R.id.used_welth);
            this.area = (TextView)itemView.findViewById(R.id.area);
            this.time = (TextView)itemView.findViewById(R.id.time);
            this.used_id = (TextView)itemView.findViewById(R.id.used_id);



        }
    }





}
