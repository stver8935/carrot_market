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

import com.example.carrot_market.MODEL.DTO.FollowListItem;
import com.example.carrot_market.MODEL.HttpConnect.FollowListTask;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.FollowListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FollowList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageView back;

    private FollowListAdapter followListAdapter;
    private ArrayList<FollowListItem> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_list);



        arrayList=new ArrayList<>();
        followListAdapter=new FollowListAdapter(arrayList);


        back=findViewById(R.id.follow_list_back);
        recyclerView=findViewById(R.id.follow_list_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(FollowList.this));
        recyclerView.setAdapter(followListAdapter);



        FollowListTask followListTask=new FollowListTask(handler,new UserInfoSave(this).return_account().getId());
        Thread thread=new Thread(followListTask);
        thread.start();
    }

Handler handler=new Handler(new Handler.Callback() {
    @Override
    public boolean handleMessage(@NonNull Message msg) {
   switch (msg.what){
       case 0:
           try {
               JSONArray jsonArray=new JSONArray(msg.getData().getString("follow_list"));
               Log.e("follow_list",""+jsonArray);

            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                FollowListItem item=new FollowListItem();

                item.setId(jsonObject.getString("id"));
                item.setProfile_image_path(jsonObject.getString("profile_image"));
                item.setAddress(jsonObject.getString("address"));
                item.setLocation(jsonObject.getString("location"));
                arrayList.add(item);
                followListAdapter.notifyItemInserted(arrayList.size()-1);
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
