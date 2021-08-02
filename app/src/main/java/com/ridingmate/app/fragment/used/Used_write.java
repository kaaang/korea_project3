package com.ridingmate.app.fragment.used;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ridingmate.app.R;
import com.ridingmate.app.activity.main.MainActivity;
import com.ridingmate.app.activity.member.UserAccount;
import com.ridingmate.app.activity.used.UsedActivity;
import com.ridingmate.app.util.used.UsedListData;
import com.ridingmate.app.util.used.UsedWriteAdapter;
import com.ridingmate.app.util.used.UsedWriteData;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class Used_write extends Fragment {
    String TAG = this.getClass().getName();

    private static final int REQUEST_CODE=0;
    private FirebaseAuth mfireFirebaseAuth;

    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private ArrayList<UsedWriteData> arrayList;
    private UsedWriteAdapter usedWriteAdapter;
    private ImageButton add_image;
    private Button used_submit;


    //storage선언
    private StorageReference mStorageRef;
    private Uri filePath;
    private List filePathArray;

    FirebaseUser firebaseUser;

    UploadFile upload;



    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        mfireFirebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=mfireFirebaseAuth.getCurrentUser();

        mStorageRef = FirebaseStorage.getInstance().getReference();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
        }
        View view=inflater.inflate(R.layout.fragment_used_write,container,false);

        arrayList=new ArrayList<>();
        recyclerView=view.findViewById(R.id.used_write_RV);
        linearLayoutManager=new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,true);
        usedWriteAdapter=new UsedWriteAdapter(arrayList);

        recyclerView.setAdapter(usedWriteAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);




        recyclerView.scrollToPosition(arrayList.size() - 1);
        usedWriteAdapter.notifyDataSetChanged();


        filePathArray = new ArrayList();

        add_image=view.findViewById(R.id.add_image);
        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addImg();
            }
        });

        used_submit = view.findViewById(R.id.used_submit);
        used_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regist();
            }
        });


        return view;
    }






    public void addImg(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        if(requestCode == 0 && resultCode == RESULT_OK){
            filePath = data.getData();
            filePathArray.add(filePath);
            Log.d(TAG, "uri:" + String.valueOf(filePath));
            try {
                //Uri 파일을 Bitmap으로 만들어서 ImageView에 집어 넣는다.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);


                //비트맵 회전 방지 해야함


                UsedWriteData usedWriteData=new UsedWriteData(bitmap);
                arrayList.add(usedWriteData);
                usedWriteAdapter.notifyDataSetChanged();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void regist(){

        for(int i=0;i<filePathArray.size();i++){
            upload = new UploadFile((Uri) filePathArray.get(i), getActivity(), firebaseUser, i);
        }



    }
    






}
