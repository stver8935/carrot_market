package com.example.carrot_market.CONTROLLER;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.MODEL.DTO.HomeFragmentItem;
import com.example.carrot_market.MODEL.HttpConnect.productDownLoadAllTask;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.HomeFragmentAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class SaleItemsFragment extends Fragment {

     RecyclerView product_recyclerview;
    private LinearLayoutManager linearLayoutManager;
     private Context context;
    private String tab_count;

    HomeFragmentAdapter adapter;
    ArrayList<HomeFragmentItem> arrayList=new ArrayList<>();
    private UserInfoSave userInfoSave;
    private String profile_id;

    public SaleItemsFragment(String count,String profile_id) {
        this.tab_count=count;
        this.profile_id=profile_id;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_sale_items,container,false);
        context=getContext();




        product_recyclerview=v.findViewById(R.id.fragment_sale_items_recycler);
        adapter=new HomeFragmentAdapter(context,arrayList);
        linearLayoutManager=new LinearLayoutManager(context);

        product_recyclerview.setAdapter(adapter);
        product_recyclerview.setLayoutManager(linearLayoutManager);



        /*
        * 0-전체 물품 판매 데이터
        * 1-판매중인 물품 데이터
        * 2-판매가 완료된 물품 데이터
        * 아이템-ProductListItem
        * 리스트-arraylist
        *어댑터-adapter
        */


        RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int itemTotalCount = recyclerView.getAdapter().getItemCount()-1;

                Toast.makeText(context, "sale_position", Toast.LENGTH_SHORT).show();
                Log.e("scroll", "lastVisibled"+arrayList.size());
                if (lastVisibleItemPosition == itemTotalCount) {
                    productDownLoadAllTask productDownLoadAllTask=new productDownLoadAllTask(arrayList.size(),profile_id,tab_count,handler);
                    Thread thread=new Thread(productDownLoadAllTask);
                    thread.run();

                }

            }
        };
        product_recyclerview.addOnScrollListener(onScrollListener);




        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userInfoSave=new UserInfoSave(getContext());

        productDownLoadAllTask productDownLoadAllTask=new productDownLoadAllTask(arrayList.size(),profile_id,tab_count,handler);
        Thread thread=new Thread(productDownLoadAllTask);
        thread.run();




        }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            switch (msg.what) {
                case 4:

                    Log.e("sales_items", "" + msg.getData().getString("product_list"));


                    Log.e("product_error_chek", "" + msg.getData().getString("product_list"));
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = new JSONArray(msg.getData().getString("product_list"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    for (int i = 1; i < jsonArray.length(); i++) {
                        HomeFragmentItem item = new HomeFragmentItem();
                        try {
                            item.setSales_completed(jsonArray.getJSONObject(i).getString("sales_completed"));
                            item.setId(jsonArray.getJSONObject(i).getString("id"));
                            item.setProduct_key(Integer.parseInt(jsonArray.getJSONObject(i).getString("product_key")));
                            Log.e("itme_key", "" + jsonArray.getJSONObject(i).getString("product_key")+"//"+jsonArray.length());
                            item.setTitle(jsonArray.getJSONObject(i).getString("title"));
                            item.setPrice(jsonArray.getJSONObject(i).getInt("price"));
                            item.setChatting_count(jsonArray.getJSONObject(i).getInt("chat_room_count"));
                            item.setFavorite_count(jsonArray.getJSONObject(i).getInt("favorite_count"));
                            item.setComnet_count(jsonArray.getJSONObject(i).getInt("product_coment_count"));
                            item.setDate(jsonArray.getJSONObject(i).getString("date"));
                            item.setLocation(jsonArray.getJSONObject(i).getString("location"));
                            item.setImage_path(jsonArray.getJSONObject(i).getString("main_image"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        arrayList.add(item);
                        adapter.notifyItemInserted(arrayList.size()-1);




                    }
                    break;



            }


            return false;
        }
    });

}




