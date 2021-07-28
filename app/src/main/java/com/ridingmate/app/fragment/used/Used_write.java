package com.ridingmate.app.fragment.used;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ridingmate.app.R;
import com.ridingmate.app.util.used.UsedListData;
import com.ridingmate.app.util.used.UsedWriteAdapter;
import com.ridingmate.app.util.used.UsedWriteData;

import java.util.ArrayList;

public class Used_write extends Fragment {
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private ArrayList<UsedWriteData> arrayList;
    private UsedWriteAdapter usedWriteAdapter;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_used_write,container,false);

        arrayList=new ArrayList<>();
        recyclerView=view.findViewById(R.id.used_write_RV);
        linearLayoutManager=new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,true);
        usedWriteAdapter=new UsedWriteAdapter(arrayList);

        recyclerView.setAdapter(usedWriteAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);


        for (int i=0;i<=10;i++){
            UsedWriteData usedWriteData = new UsedWriteData(R.mipmap.ic_launcher);
            arrayList.add(usedWriteData);
        }
        recyclerView.scrollToPosition(arrayList.size() - 1);
        usedWriteAdapter.notifyDataSetChanged();

        return view;
    }
}
