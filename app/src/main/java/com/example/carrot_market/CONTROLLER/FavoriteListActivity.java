package com.example.carrot_market.CONTROLLER;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.MODEL.DTO.HomeFragmentItem;
import com.example.carrot_market.MODEL.HttpConnect.FavoriteProductListTask;
import com.example.carrot_market.MODEL.HttpConnect.ProductActivityInfoTask;
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
    private final int PRODUCT_DETAILE_ACTIVITY_RESULT_CODE=2;
    private boolean data_load=true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);

        userInfoSave=new UserInfoSave(FavoriteListActivity.this);

        arrayList=new ArrayList<>();
        back=findViewById(R.id.favorite_list_back);
        recyclerView=findViewById(R.id.favorite_list_recyclerview);

        productListAdapter=new HomeFragmentAdapter(arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(FavoriteListActivity.this));
        recyclerView.setAdapter(productListAdapter);


        FavoriteProductListTask favoriteProductListTask=new FavoriteProductListTask(userInfoSave.return_account().getId(),0,handler);
        Thread thread=new Thread(favoriteProductListTask);
        thread.run();



        RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);



                int totalItemCount = productListAdapter.getItemCount();
                int lastVisibleItemPosition =((LinearLayoutManager)recyclerView.getLayoutManager()).findLastVisibleItemPosition();

                if((totalItemCount-1)==lastVisibleItemPosition) {
                    if (data_load) {
                        FavoriteProductListTask favoriteProductListTask = new FavoriteProductListTask(userInfoSave.return_account().getId(), arrayList.size(), handler);
                        Thread thread = new Thread(favoriteProductListTask);
                        thread.run();
                        data_load = false;
                    }
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
                   data_load=true;
               }

               } catch (JSONException e) {
                   e.printStackTrace();
               }
               break;

           case 1:
               try {
                   JSONObject product_activity_info_json=new JSONObject(msg.getData().getString("product_activity_info"));
                   for (int i=0;i<arrayList.size();i++){
                       if (arrayList.get(i).getProduct_key()==product_activity_info_json.getInt("product_key")){
                           Log.e("update_product_key",""+arrayList.get(i).getProduct_key()+product_activity_info_json.getInt("product_key"));
                           arrayList.remove(i);
                           productListAdapter.notifyItemRemoved(i);
                           break;
                       }
                   }

                   Log.e("product_update","update"+product_activity_info_json.getInt("product_key"));

               } catch (JSONException e) {
                   e.printStackTrace();
               }
               break;


       }

            return false;
        }
    });

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    switch (requestCode){

        case PRODUCT_DETAILE_ACTIVITY_RESULT_CODE:
            if (resultCode==1){

                if (!data.getBooleanExtra("favorite_check",true)) {
                    ProductActivityInfoTask productActivityInfoTask = new ProductActivityInfoTask(data.getStringExtra("product_key"), handler);
                    Thread thread = new Thread(productActivityInfoTask);
                    thread.start();
                }
                }
            break;
    }


    }
}
