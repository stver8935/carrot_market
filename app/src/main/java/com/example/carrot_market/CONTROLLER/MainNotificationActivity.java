package com.example.carrot_market.CONTROLLER;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.MODEL.DTO.NotifyActiveItem;
import com.example.carrot_market.MODEL.HttpConnect.ActivityNotificationListloadTask;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.NotifyActiveAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainNotificationActivity extends Fragment {

    RecyclerView recyclerView;
    NotifyActiveAdapter activeAdapter;
    ArrayList<NotifyActiveItem> arrayList=new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    UserInfoSave userInfoSave;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userInfoSave=new UserInfoSave(getContext());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_main_notification_active,container,false);
        recyclerView=v.findViewById(R.id.main_notification_active_recycler);
        activeAdapter=new NotifyActiveAdapter(arrayList,v.getContext());
        linearLayoutManager=new LinearLayoutManager(v.getContext());

        recyclerView.setAdapter(activeAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        ActivityNotificationListloadTask activityNotificationListloadTask=new ActivityNotificationListloadTask(userInfoSave.return_account().getId(),""+arrayList.size(),handler);
        Thread thread=new Thread(activityNotificationListloadTask);
        thread.start();


        return v;
    }



    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

        switch (msg.what){

            //활동알림 받는 부분
            case 0:
                try {
                    JSONArray jsonArray=new JSONArray(msg.getData().getString("activity_notification_list"));

                    Log.e("main_notification_ac",""+jsonArray);
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        NotifyActiveItem item=new NotifyActiveItem();

                        item.setDivision_type(jsonObject.getString("division_type"));
                        item.setKey(jsonObject.getString("key"));
                        item.setProduct_key(jsonObject.getString("product_key"));
                        item.setId(jsonObject.getString("id"));
                        item.setImage_path(jsonObject.getString("image_path"));
                        item.setDescription(jsonObject.getString("description"));
                        item.setTime_stamp(jsonObject.getString("time_stamp"));
                        arrayList.add(item);
                        activeAdapter.notifyItemInserted(arrayList.size()-1);

                    }





                } catch (JSONException e) {
                    e.printStackTrace();
                }


                break;
            case 1:

                break;
        }





            return false;
        }
    });
}
