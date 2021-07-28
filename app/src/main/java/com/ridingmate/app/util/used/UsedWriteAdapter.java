package com.ridingmate.app.util.used;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ridingmate.app.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class UsedWriteAdapter extends RecyclerView.Adapter<UsedWriteAdapter.CustomViewHolder>{
    private ArrayList<UsedWriteData> arrayList;

    public UsedWriteAdapter(ArrayList<UsedWriteData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @NotNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_used_write_image,parent,false);
        CustomViewHolder holder=new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull UsedWriteAdapter.CustomViewHolder holder, int position) {
        holder.used_write_thumb.setImageResource(arrayList.get(position).getUsed_write_thumb());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(v -> {
            String title= "이미지 왜 눌러 임마";
            Toast.makeText(v.getContext(),title,Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size():0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public ImageView used_write_thumb;

        public CustomViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            used_write_thumb= itemView.findViewById(R.id.used_write_thumb);
        }
    }
}
