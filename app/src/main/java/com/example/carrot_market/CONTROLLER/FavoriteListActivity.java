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

import com.example.carrot_market.MODEL.DTO.HomeFragmentItem;
import com.example.carrot_market.MODEL.HttpConnect.FavoriteProductListTask;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.HomeFragmentAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FavoriteListActivity extends AppCompatActivity {

    private ImageView back;
    private RecyclerView recyclerView;
    private HomeFragmentAdapter productListAdapter;
    private ArrayList<HomeFragmentItem> arrayList;
    private UserInfoSave userInfoSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);

        userInfoSave=new UserInfoSave(FavoriteListActivity.this);

        arrayList=new ArrayList<>();
        back=findViewById(R.id.favorite_list_back);
        recyclerView=findViewById(R.id.favorite_list_recyclerview);

        productListAdapter=new HomeFragmentAdapter(FavoriteListActivity.this,arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(FavoriteListActivity.this));
        recyclerView.setAdapter(productListAdapter);


        FavoriteProductListTask favoriteProductListTask=new FavoriteProductListTask(userInfoSave.return_account().getId(),0,handler);
        Thread thread=new Thread(favoriteProductListTask);
        thread.run();



        RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int itemTotalCount = recyclerView.getAdapter().getItemCount()-1;

                Log.e("scroll", "lastVisibled"+arrayList.size());
                if (lastVisibleItemPosition == itemTotalCount) {
                    FavoriteProductListTask favoriteProductListTask=new FavoriteProductListTask(userInfoSave.return_account().getId(),arrayList.size(),handler);
                    Thread thread=new Thread(favoriteProductListTask);
                    thread.run();

                }

            }
        };
        recyclerView.addOnScrollListener(onScrollListener);




    }

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

       switch (msg.what){
           case 0:

               try {
                   JSONArray favorite_product_list=new JSONArray(msg.getData().getString("favorite_product_list"));

                   Log.e("favorite_list",""+favorite_product_list);

               for (int i=0;i<favorite_product_list.length();i++){
                   JSONObject favorite_product_obj=favorite_product_list.getJSONObject(i);
                   HomeFragmentItem item=new HomeFragmentItem();
                   item.setProduct_key(favorite_product_obj.getInt("product_key"));
                   item.setTitle(favorite_product_obj.getString("title"));
                   item.setPrice(favorite_product_obj.getInt("price"));
                   item.setChatting_count(favorite_product_obj.getInt("chat_room_count"));
                   item.setComnet_count(favorite_product_obj.getInt("product_coment_count"));
                   item.setFavorite_count(favorite_product_obj.getInt("favorite_count"));
                   item.setSales_completed(favorite_product_obj.getString("sales_completed"));
                   item.setLocation(favorite_product_obj.getString("location"));
                   item.setImage_path(favorite_product_obj.getString("main_image"));
                   item.setId(favorite_product_obj.getString("id"));
                   arrayList.add(item);
                   productListAdapter.notifyItemInserted(arrayList.size()-1);
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
