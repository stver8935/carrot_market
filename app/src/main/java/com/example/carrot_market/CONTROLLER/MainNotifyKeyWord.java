package com.example.carrot_market.CONTROLLER;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.MODEL.DTO.NotifyKeywordItem;
import com.example.carrot_market.MODEL.HttpConnect.KeyWordNotificationListDeleteTask;
import com.example.carrot_market.MODEL.HttpConnect.ProductNotificationListLoadTask;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.NotifyKeywordAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainNotifyKeyWord extends Fragment {

    NotifyKeywordAdapter adapter;
     ArrayList<NotifyKeywordItem> arrayList=new ArrayList<>();
    ConstraintLayout all_del_bar;
    UserInfoSave userInfoSave;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userInfoSave=new UserInfoSave(getContext());


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_main_notify_key_word,container,false);
        RecyclerView recyclerView = v.findViewById(R.id.main_notify_key_word_recycler);
        adapter=new NotifyKeywordAdapter(arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(v.getContext());

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        all_del_bar=v.findViewById(R.id.main_notify_key_word_del_all_bar);
        TextView del_all = v.findViewById(R.id.main_notify_key_word_del_all);


        ProductNotificationListLoadTask productNotificationListLoadTas=new ProductNotificationListLoadTask(new UserInfoSave(v.getContext()).return_account().getId(),"0",handler);
        Thread thread=new Thread(productNotificationListLoadTas);
        thread.start();

        del_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //로컬디비에 있는 알림 리스트 삭제 시키기
                KeyWordNotificationListDeleteTask keyWordNotificationListDeleteTask=new KeyWordNotificationListDeleteTask(null,userInfoSave.return_account().getId(),handler);
                Thread key_word_notificaiton_list_delete_thread=new Thread(keyWordNotificationListDeleteTask);
                key_word_notificaiton_list_delete_thread.start();
            }
        });
        return v;
    }

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Log.e("cal_key_word","call");

            switch (msg.what) {

            case 0:
                try {
                    JSONArray key_word_list_json_array=new JSONArray(msg.getData().getString("product_notification_list"));


                    for (int i=0;i<key_word_list_json_array.length();i++){
                        JSONObject keyword_json_obj=key_word_list_json_array.getJSONObject(i);

                        String title="["+keyword_json_obj.getString("key_word")+" 키워드 알림] "+keyword_json_obj.getString("location")+"-"
                                +keyword_json_obj.getString("title");

                        NotifyKeywordItem item=new NotifyKeywordItem();
                        item.setText(title);
                        item.setKey_word_key(keyword_json_obj.getString("key_word_key"));
                        item.setProduct_key(keyword_json_obj.getString("product_key"));
                        item.setImage_path(keyword_json_obj.getString("image_path"));
                        item.setDate(keyword_json_obj.getString("insert_time"));
                        arrayList.add(item);
                        adapter.notifyItemInserted(i);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                break;

                //키워드 삭제 반환코드 받는곳 클라이언트 아이템 삭제 처리
                case 1:
                    try {
                        JSONObject delete_response_code=new JSONObject(msg.getData().getString("response_code"));
                        Log.e("delete_response_code",delete_response_code.toString());

                        if (delete_response_code.getBoolean("success")){

                            //전체 삭제이라면
                            if (delete_response_code.getString("action").equals("all")){
                                adapter.notifyItemRangeRemoved(0,arrayList.size());
                                arrayList.clear();
                            }else {
                            //부분 삭제이라면

                                for (int i=0;i<arrayList.size();i++){
                                    if (arrayList.get(i).getKey_word_key().equals(delete_response_code.getString("action"))){
                                     arrayList.remove(i);
                                     adapter.notifyItemRemoved(i);
                                    }
                                }
                            }


                        }else {
                            Toast.makeText(getView().getContext(), "키워드 삭제에 실패 했습니다.", Toast.LENGTH_SHORT).show();
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
