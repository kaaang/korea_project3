package com.ridingmate.app.fragment.used;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ridingmate.app.R;
import com.ridingmate.app.activity.main.MainActivity;
import com.ridingmate.app.util.used.UsedListAdapter;
import com.ridingmate.app.util.used.UsedListData;

import java.util.ArrayList;

public class Used_list extends Fragment {

    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private ArrayList<UsedListData> arrayList;
    private UsedListAdapter usedListAdapter;
    private ImageButton write_button;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_used_list,container,false);



        recyclerView=(RecyclerView)view.findViewById(R.id.used_RV);
        linearLayoutManager= new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), 1));

        arrayList=new ArrayList<>();

        usedListAdapter=new UsedListAdapter(arrayList);
        recyclerView.setAdapter(usedListAdapter);

        for (int i=0;i<=30;i++){

        UsedListData usedListData = new UsedListData(R.mipmap.ic_launcher,"","신혀기 람브동생 커브팔아여","커브","9","975");
        arrayList.add(usedListData);
        }

        usedListAdapter.notifyDataSetChanged();

        
        
        
        
        //중고거래 글작성 페이지로 이동
        write_button=view.findViewById(R.id.write_button);
        write_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity main =  (MainActivity) getContext();
                main.showPage(6);
            }
        });






        return view;
    }
}
