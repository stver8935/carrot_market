package com.example.carrot_market.CONTROLLER;


import android.content.Intent;
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

import com.example.carrot_market.MODEL.DTO.HomeFragmentItem;
import com.example.carrot_market.MODEL.HttpConnect.ProductActivityInfoTask;
import com.example.carrot_market.MODEL.HttpConnect.SalesProductTask;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.ProductHistoryAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductHistoryFragment extends Fragment {



    private final int PRODUCT_HISTORY_ITEM_UPDATE_REQUEST_CODE=2;
    private RecyclerView recyclerView;
     ArrayList<HomeFragmentItem> arrayList;
     ProductHistoryAdapter adapter;


     private UserInfoSave userInfoSave;

    private String sales_completed_check,hidden_check;

    public ProductHistoryFragment(String sales_completed_check,String hidden_check) {
        this.sales_completed_check = sales_completed_check;
        this.hidden_check=hidden_check;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userInfoSave=new UserInfoSave(getContext());
        arrayList=new ArrayList<>();


        adapter=new ProductHistoryAdapter(arrayList,getContext());
        SalesProductTask salesProductTask=new SalesProductTask(userInfoSave.return_account().getId(),""+arrayList.size(),hidden_check,sales_completed_check,handler);
        Thread thread = new Thread(salesProductTask);




        thread.run();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=LayoutInflater.from(getContext()).inflate(R.layout.fragment_product_histoty,container,false);


        recyclerView=v.findViewById(R.id.fragment_product_history_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);




        RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int itemTotalCount = recyclerView.getAdapter().getItemCount()-1;

                Log.e("scroll", "lastVisibled"+arrayList.size());
                if (lastVisibleItemPosition==itemTotalCount&&9<lastVisibleItemPosition) {
                    SalesProductTask salesProductTask=new SalesProductTask(userInfoSave.return_account().getId(),""+arrayList.size(),hidden_check,sales_completed_check,handler);
                    Thread thread = new Thread(salesProductTask);
                    thread.run();

                }

            }
        };

        recyclerView.addOnScrollListener(onScrollListener);

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("ProductHistoryFragment","result_call");

    switch (requestCode){
        case PRODUCT_HISTORY_ITEM_UPDATE_REQUEST_CODE:

            if (resultCode==1){
                ProductActivityInfoTask productActivityInfoTask=new ProductActivityInfoTask(data.getStringExtra("product_key"),handler);
                Thread thread=new Thread(productActivityInfoTask);
                thread.start();
            }
            else {

            }

            break;
    }





    }

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            switch (msg.what){
                case 0:

                    Log.e("product_error_chek",""+msg.getData().getString("sales_product"));
                    JSONArray jsonArray= null;

                    try {
                        jsonArray = new JSONArray(msg.getData().getString("sales_product"));

                    for (int i=0;i<jsonArray.length();i++){
                        HomeFragmentItem item=new HomeFragmentItem();
                        item.setHidden(jsonArray.getJSONObject(i).getString("hidden"));
                        item.setSales_completed(jsonArray.getJSONObject(i).getString("sales_completed"));
                        item.setId(jsonArray.getJSONObject(i).getString("id"));
                        item.setProduct_key(Integer.parseInt(jsonArray.getJSONObject(i).getString("product_key")));
                        Log.e("itme_key",""+jsonArray.getJSONObject(i).getString("product_key"));
                        item.setTitle(jsonArray.getJSONObject(i).getString("title"));
                        item.setPrice(jsonArray.getJSONObject(i).getInt("price"));
                        item.setChatting_count(jsonArray.getJSONObject(i).getInt("chat_room_count"));
                        item.setFavorite_count(jsonArray.getJSONObject(i).getInt("favorite_count"));
                        item.setComnet_count(jsonArray.getJSONObject(i).getInt("product_coment_count"));
                        item.setDate(jsonArray.getJSONObject(i).getString("date"));
                        item.setLocation(jsonArray.getJSONObject(i).getString("location"));
                        item.setImage_path(jsonArray.getJSONObject(i).getString("main_image"));
                        arrayList.add(item);
                        adapter.notifyItemInserted(arrayList.size()-1);
                    }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;


                    //채팅룸 갯수 댓글수 좋아요 수 업데이트
                case 1:
                    if (msg.getData()!=null){
                        try {
                            JSONObject product_activity_info_json=new JSONObject(msg.getData().getString("product_activity_info"));

                            if (arrayList!=null) {
                                for (int i = 0; i < arrayList.size(); i++) {
                                    if (arrayList.get(i).getProduct_key() == product_activity_info_json.getInt("product_key")) {
                                        arrayList.get(i).setChatting_count(product_activity_info_json.getInt("chatting_room_count"));
                                        arrayList.get(i).setComnet_count(product_activity_info_json.getInt("coment_count"));
                                        arrayList.get(i).setFavorite_count(product_activity_info_json.getInt("favorite_count"));
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                            }


                        } catch (JSONException e) {


                        }
                    }
                    break;
                }



        return false;

        }
    });

}
