package com.example.carrot_market.CONTROLLER;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.MODEL.DTO.SellerListItem;
import com.example.carrot_market.MODEL.HttpConnect.FindSellersInChatTask;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.SellerListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FindSellersInChat extends AppCompatActivity {


    private RecyclerView recyclerView;
    private SellerListAdapter adapter;
    private ArrayList<SellerListItem> arrayList;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_sellers_in_chat);

        arrayList=new ArrayList<>();
        recyclerView=findViewById(R.id.find_sellers_in_chat_recyclerview);


        String product_title=getIntent().getStringExtra("title");
        String product_key=getIntent().getStringExtra("product_key");

        adapter=new SellerListAdapter(arrayList,product_title,product_key);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        FindSellersInChatTask findSellersInChatTask=new FindSellersInChatTask(new UserInfoSave(FindSellersInChat.this).return_account().getId(),handler);
        Thread thread=new Thread(findSellersInChatTask);
        thread.start();

    }

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
        switch (msg.what){
            case 1:

                try {
                    JSONArray jsonArray=new JSONArray(msg.getData().getString("find_seller_in_chat"));
                    Log.e("find_seller_list",""+jsonArray);

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject seller_obj=jsonArray.getJSONObject(i);
                        SellerListItem item=new SellerListItem();
                        item.setId(seller_obj.getString("id"));
                        item.setProfile_image(seller_obj.getString("profile_image"));
                        item.setLocation(seller_obj.getString("location"));
                        item.setAddress(seller_obj.getString("address"));
                        item.setReview_commit_check(false);
                        arrayList.add(item);
                        adapter.notifyItemInserted(arrayList.size()-1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                break;
        }



            return false;
        }
    });
}
