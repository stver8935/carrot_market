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

import com.example.carrot_market.CONTROLLER.InterFace.Search;
import com.example.carrot_market.MODEL.DTO.UserListItem;
import com.example.carrot_market.MODEL.HttpConnect.UserSearchTask;
import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.SearchUserListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeSearchFragmentPerson extends Fragment implements Search {


    private SearchUserListAdapter adapter;
    private ArrayList<UserListItem> arraylist=new ArrayList<>();
    private RecyclerView recyclerview;
    private String serch_text;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.activity_home_search_fragment_person,container,false);


        recyclerview=v.findViewById(R.id.home_search_person_recyclerview);
        adapter=new SearchUserListAdapter(arraylist);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setAdapter(adapter);


        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = adapter.getItemCount();
                int lastVisibleItemPosition =((LinearLayoutManager)recyclerView.getLayoutManager()).findLastVisibleItemPosition();

                if(10<=totalItemCount&&(totalItemCount-1)<=lastVisibleItemPosition){

                    //밑으로 스크롤시 검색 단어를 포함하는 다음 유저 리스트 요청청
                    UserSearchTask UserSearchTask=new UserSearchTask(serch_text,""+arraylist.size(),handler);
                    Thread thread=new Thread(UserSearchTask);
                    thread.start();
                }else{

                }
            }
        });

        return v;
    }


    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
       switch (msg.what){
           case 0:

               try {
                   JSONArray user_list_jsonarray=new JSONArray(msg.getData().getString("user_list"));
                   Log.e("user_list",""+user_list_jsonarray);

                   for (int i=0;i<user_list_jsonarray.length();i++){
                   JSONObject user_json_obj=user_list_jsonarray.getJSONObject(i);
                   UserListItem item=new UserListItem();

                   item.setName(user_json_obj.getString("name"));
                   item.setId(user_json_obj.getString("id"));
                   item.setProfile_image_path(user_json_obj.getString("profile_image"));
                   item.setAddress(user_json_obj.getString("address"));
                   arraylist.add(item);
                   adapter.notifyItemInserted(arraylist.size()-1);
               }



               } catch (JSONException e) {
                   e.printStackTrace();
               }



               break;
       }

            return false;
        }
    });


    @Override
    public void search_deal(String content) {
        adapter.notifyItemRangeRemoved(0,arraylist.size());
        arraylist.clear();
        this.serch_text=content;

        UserSearchTask userSearchTask=new UserSearchTask(content,"0",handler);
        Thread thread=new Thread(userSearchTask);
        thread.start();
    }
}
