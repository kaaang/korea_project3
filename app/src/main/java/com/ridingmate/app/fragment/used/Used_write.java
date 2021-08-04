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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ridingmate.app.R;
import com.ridingmate.app.activity.main.MainActivity;
import com.ridingmate.app.activity.member.JoinActivity;
import com.ridingmate.app.activity.member.UserAccount;
import com.ridingmate.app.activity.used.UsedActivity;
import com.ridingmate.app.util.pageAdapter.PageAdapter;
import com.ridingmate.app.util.used.UsedListData;
import com.ridingmate.app.util.used.UsedWriteAdapter;
import com.ridingmate.app.util.used.UsedWriteData;
import com.ridingmate.app.util.used.Used_regist;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class Used_write extends Fragment {

    EditText used_write_title,used_write_welth,used_content;

    String TAG = this.getClass().getName();

    private static final int REQUEST_CODE=0;
    private FirebaseAuth mfireFirebaseAuth;

    private LinearLayoutManager linearLayoutManager;
    private Spinner spinner;
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
    String save_num;

    //스피너 선택
    String area;

    MainActivity main;




    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        mfireFirebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=mfireFirebaseAuth.getCurrentUser();

        mStorageRef = FirebaseStorage.getInstance().getReference();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
        }
        View view=inflater.inflate(R.layout.fragment_used_write,container,false);
        main=(MainActivity) getActivity();

        //스피너 관련
        spinner = view.findViewById(R.id.used_write_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.korea,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                area = (String)parent.getItemAtPosition(position);
                Log.e(TAG,"@@@###" + area);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        
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
                Log.e("asd","온클릭 순서확인 ");
            }
        });

//        EditText used_write_title,used_write_welth,used_content;
        used_write_title=view.findViewById(R.id.used_write_title);
        used_write_welth=view.findViewById(R.id.used_write_welth);
        used_content=view.findViewById(R.id.used_content);


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

        write_store();


    }


    public void write_store(){

        SimpleDateFormat dayformat = new SimpleDateFormat("yy.MM.dd");
        SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm:ss");
        Date now = new Date();
        String day = dayformat.format(now) ;
        String time = timeformat.format(now);


        Map<String, String> input_data = new HashMap<>();
        input_data.put("UID", firebaseUser.getUid());
        input_data.put("day", day);
        input_data.put("time", time);
        input_data.put("title", used_write_title.getText().toString());
        input_data.put("welth",used_write_welth.getText().toString());
        input_data.put("content",used_content.getText().toString());
        input_data.put("area",area);
        input_data.put("bike_id",main.selectedBikeUid);





        FirebaseFirestore db = FirebaseFirestore.getInstance();


        db.collection("used").document("used_start").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String num=documentSnapshot.getString("number");
                save_num=num;
                input_data.put("used_id",save_num);
                List<String> nameArr = new ArrayList<>();
                upload = new UploadFile(getActivity(), firebaseUser, Integer.parseInt(save_num));
                for(int i=0;i<filePathArray.size();i++){
                    String name=upload.uploadFile((Uri) filePathArray.get(i),i);
                    nameArr.add(name);
                }
                int i=0;
                for(Object obj:nameArr){
                    input_data.put(i+"img",obj.toString());
                    i++;
                }
                nameArr.clear();


                db.collection("used").document(save_num).set(input_data).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        int plus = Integer.parseInt(save_num);

                        plus++;

                        Map<String, String> number = new HashMap<>();
                        number.put("number",""+plus);
                        db.collection("used").document("used_start").set(number)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<Void> task) {


                                    }
                                });

                        Log.e("asd","디비 올라감");














                        used_write_title.setText("");
                        used_write_welth.setText("");
                        used_content.setText("");
                        input_data.clear();
                        filePathArray.clear();
                        arrayList.clear();
                        usedWriteAdapter.notifyDataSetChanged();

                    }
                });
            }
        });











    }






}
