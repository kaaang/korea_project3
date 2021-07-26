package com.ridingmate.app.util.used;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ridingmate.app.R;

import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.util.ArrayList;

public class UsedListAdapter extends RecyclerView.Adapter<UsedListAdapter.CustomViewHolder> {
    private ArrayList<UsedListData> arrayList;

    public UsedListAdapter(ArrayList<UsedListData> arrayList){
        this.arrayList=arrayList;
    }

    @NonNull
    @NotNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_used_list,parent,false);

        CustomViewHolder holder=new CustomViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull UsedListAdapter.CustomViewHolder holder, int position) {
        holder.used_thumb.setImageResource(arrayList.get(position).getUsed_thumb());
        holder.used_title.setText(arrayList.get(position).getUsed_title());
        holder.model_type.setText(arrayList.get(position).getModel_type());
        holder.used_comment_cont.setText(arrayList.get(position).getUsed_comment_cont());
        holder.used_welth.setText(arrayList.get(position).getUsed_welth());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(v -> {
            String title= holder.used_title.getText().toString();
            Toast.makeText(v.getContext(),title,Toast.LENGTH_SHORT).show();
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
        protected TextView used_comment_cont;
        protected TextView used_welth;

        public CustomViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.used_thumb = (ImageView)itemView.findViewById(R.id.used_thumb);
            this.used_title = (TextView)itemView.findViewById(R.id.used_title);
            this.model_type = (TextView)itemView.findViewById(R.id.model_type);
            this.used_comment_cont = (TextView)itemView.findViewById(R.id.used_comment_cont);
            this.used_welth = (TextView)itemView.findViewById(R.id.used_welth);


        }
    }
}
