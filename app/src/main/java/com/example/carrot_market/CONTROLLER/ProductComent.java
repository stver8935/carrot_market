package com.example.carrot_market.CONTROLLER;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.MODEL.DTO.ProductComentItem;
import com.example.carrot_market.MODEL.HttpConnect.ProductComentInsertTask;
import com.example.carrot_market.MODEL.HttpConnect.ProductComentListTask;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.ProductComentAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductComent extends AppCompatActivity {
    private RecyclerView recyclerView_coment;
    private LinearLayoutManager linearLayoutManager;
    private ProductComentAdapter adapter;
    private ArrayList<ProductComentItem> arrayList=new ArrayList<>();
    private ImageButton back;
    private EditText write_coment;
    public Button write_commit;
    private TextView title;
    private UserInfoSave userInfoSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_coment);

        userInfoSave=new UserInfoSave(ProductComent.this);


        //뒤로 가기 버튼
        back=findViewById(R.id.product_coment_back);

        //댓글 입력창
        write_coment=findViewById(R.id.product_coment_insert_text);

        //댓글 입력 완료
        write_commit=findViewById(R.id.product_coment_commit);

        title=findViewById(R.id.product_coment_title);


        recyclerView_coment=findViewById(R.id.product_coment_list_recycler);
        linearLayoutManager=new LinearLayoutManager(this);
        adapter=new ProductComentAdapter(arrayList,this,write_coment,handler);
        recyclerView_coment.setAdapter(adapter);
        recyclerView_coment.setLayoutManager(linearLayoutManager);



        write_coment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()<=0){
                    write_commit.setTextColor(R.color.colorwhite);

                }else {
                    write_commit.setTextColor(R.color.colorblack);
                }
            }
        });


        write_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProductComentInsertTask comentInsertTask=new ProductComentInsertTask(userInfoSave.return_account().getId(),write_coment.getText().toString(),
                        Integer.parseInt(getIntent().getStringExtra("product_key")),handler);
                Thread thread=new Thread(comentInsertTask);

                thread.run();


                if (write_coment.length()<=0){
                    Toast.makeText(ProductComent.this, "작성할 댓글을 입력해 주세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    
                    recyclerView_coment.scrollToPosition(arrayList.size()-1);
                    //입력했던 댓글 초기화
                    write_coment.setText(null);

                }

            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        ProductComentListTask task=new ProductComentListTask(getIntent().getStringExtra("product_key"),""+arrayList.size(),handler);
        Thread thread=new Thread(task);
        thread.run();

        recyclerView_coment.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                int last=((LinearLayoutManager)recyclerView_coment.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int itemcount=recyclerView_coment.getAdapter().getItemCount();

                if (last>(itemcount-3)){
                    ProductComentListTask task=new ProductComentListTask(getIntent().getStringExtra("product_key"),""+arrayList.size(),handler);
                    Thread thread=new Thread(task);
                    thread.run();

                }

            }
        });



    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            switch (msg.what){

                //코멘트 리스트 로드
                case 0:
                    try {
                        JSONArray jsonArray=new JSONArray(msg.getData().getString("coment_list"));
                   for (int i=0; i<jsonArray.length();i++){
                       JSONObject coment=jsonArray.getJSONObject(i);
                       ProductComentItem comentItem=new ProductComentItem();
                       comentItem.setKey(coment.getString("coment_key"));
                       comentItem.setId(coment.getString("coment_id"));
                       comentItem.setProfile_image_path(coment.getString("profile_image"));
                       comentItem.setComent(coment.getString("coment"));
                       comentItem.setLocation(coment.getString("location"));
                       comentItem.setDate(coment.getString("time_stamp"));
                       arrayList.add(comentItem);
                       adapter.notifyItemInserted(arrayList.size()-1);
                   }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;

                    //코멘트 입력
                case 1:

                    JSONObject jsonObject= null;
                    try {
                        jsonObject = new JSONObject(msg.getData().getString("coment"));
                    ProductComentItem item=new ProductComentItem();
                        item.setKey(jsonObject.getString("coment_key"));
                        item.setId(jsonObject.getString("id"));
                        item.setDate(jsonObject.getString("date"));

                        item.setComent(jsonObject.getString("coment"));
                        item.setProfile_image_path(jsonObject.getString("profile_image"));
                        arrayList.add(0,item);
                        adapter.notifyItemInserted(0);
                        recyclerView_coment.scrollToPosition(0);

                    } catch (JSONException  e) {
                        e.printStackTrace();
                    }

                    break;

                    //코멘트 수정
                case 2:

                    Log.e("coemnt","update?");
                    try {
                        JSONObject product_coment_update_info=new JSONObject(msg.getData().getString("product_coment_update"));

                        Log.e("coment_update",product_coment_update_info.toString());

                        for (int i=0;i<arrayList.size();i++){
                            if (arrayList.get(i).getKey().equals(product_coment_update_info.getString("coment_key"))){
                                Log.e("coemnt","update");
                                arrayList.get(i).setComent(product_coment_update_info.getString("coment"));
                                arrayList.get(i).setText(product_coment_update_info.getString("coment"));
                                adapter.notifyDataSetChanged();

                                write_coment.setText(null);

                                //수정 작업을 했던 댓글입력 버튼을 ->댓글 입력작업을 하는 버튼으로 초기화
                                write_commit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        ProductComentInsertTask comentInsertTask=new ProductComentInsertTask(userInfoSave.return_account().getId(),write_coment.getText().toString(),
                                                Integer.parseInt(getIntent().getStringExtra("product_key")),handler);
                                        Thread thread=new Thread(comentInsertTask);

                                        thread.run();


                                        if (write_coment.length()<=0){
                                            Toast.makeText(ProductComent.this, "작성할 댓글을 입력해 주세요", Toast.LENGTH_SHORT).show();
                                        }
                                        else {

                                            recyclerView_coment.scrollToPosition(arrayList.size()-1);
                                            //입력했던 댓글 초기화
                                            write_coment.setText(null);

                                        }

                                    }
                                });


                            }
                        }





                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    break;

                    //코멘트 삭제제
                case 3:
                    if (msg.getData()!=null){

                        try {
                            JSONObject coment_delete_check=new JSONObject(msg.getData().getString("coment_delete"));
                            for (int i=0;i<arrayList.size();i++){
                                if (arrayList.get(i).getKey().equals(coment_delete_check.getString("coment_key"))
                                        &&coment_delete_check.getBoolean("coment_delete_check")){
                                    adapter.notifyItemRemoved(i);
                                    arrayList.remove(i);
                                }
                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        Toast.makeText(ProductComent.this, "댓글이 삭제 되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }    };
}
