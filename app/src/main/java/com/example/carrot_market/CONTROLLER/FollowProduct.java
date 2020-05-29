package com.example.carrot_market.CONTROLLER;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.MODEL.DTO.HomeFragmentItem;
import com.example.carrot_market.MODEL.HttpConnect.FollowProductTask;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.FollowProductAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class FollowProduct extends AppCompatActivity {


    private RecyclerView recyclerView;
    private ImageView back;
    private TextView setting;
    private FollowProductAdapter adapter;
    private ArrayList<HomeFragmentItem> arrayList;
    private UserInfoSave userInfoSave;

    private boolean restart_check=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_product);


        userInfoSave=new UserInfoSave(this);

        back=findViewById(R.id.follow_product_back);
        setting=findViewById(R.id.follow_product_setting);


            arrayList=new ArrayList<>();

        recyclerView=findViewById(R.id.follow_product_recyclerview);
        adapter=new FollowProductAdapter(arrayList);
        recyclerView.setLayoutManager(new GridLayoutManager(FollowProduct.this,2));
        recyclerView.setAdapter(adapter);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FollowProduct.this,FollowList.class);
                startActivity(intent);
            }
        });

        FollowProductTask task=new FollowProductTask(userInfoSave.return_account().getId(),""+arrayList.size(),handler);
        Thread thread=new Thread(task);
        thread.start();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        restart_check=true;
        FollowProductTask task=new FollowProductTask(userInfoSave.return_account().getId(),"0",handler);
        Thread thread=new Thread(task);
        thread.start();

    }

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Log.e("msg",msg.getData().getString("follow_product_list"));

       switch (msg.what){
           case 0:

               try {
                   JSONArray jsonArray = new JSONArray(msg.getData().getString("follow_product_list"));
                   if (restart_check) {

                       adapter.notifyItemRangeRemoved(0,arrayList.size());
                       arrayList.clear();

                       for (int i = 0; i < jsonArray.length(); i++) {
                           JSONObject item_jobj = jsonArray.getJSONObject(i);
                           HomeFragmentItem item = new HomeFragmentItem();
                           item.setPrice(11111);
                           item.setTitle(item_jobj.getString("title"));
                           item.setId(item_jobj.getString("id"));
                           item.setLocation(item_jobj.getString("location"));
                           item.setImage_path(API_URL + "image/" + item_jobj.getString("main_image"));
                           item.setProduct_key(item_jobj.getInt("product_key"));
                           item.setPrice(item_jobj.getInt("price"));
                           item.setSales_completed(item_jobj.getString("sales_completed"));
                           arrayList.add(item);
                           adapter.notifyItemInserted(arrayList.size() - 1);

                       }
                   }else {
                       for (int i = 0; i < jsonArray.length(); i++) {
                           JSONObject item_jobj = jsonArray.getJSONObject(i);

                           HomeFragmentItem item = new HomeFragmentItem();
                           item.setPrice(11111);
                           item.setTitle(item_jobj.getString("title"));
                           item.setId(item_jobj.getString("id"));
                           item.setLocation(item_jobj.getString("location"));
                           item.setImage_path(API_URL + "image/" + item_jobj.getString("main_image"));
                           item.setProduct_key(item_jobj.getInt("product_key"));
                           item.setPrice(item_jobj.getInt("price"));
                           item.setSales_completed(item_jobj.getString("sales_completed"));
                           arrayList.add(item);
                           adapter.notifyItemInserted(arrayList.size() - 1);

                       }
                   }


                   } catch(JSONException e){
                       e.printStackTrace();
                   }


               break;
       }


            return false;
        }
    });
}
