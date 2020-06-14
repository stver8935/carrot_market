package com.example.carrot_market.CONTROLLER;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.carrot_market.MODEL.DTO.HomeFragmentItem;
import com.example.carrot_market.MODEL.HttpConnect.ProductActivityInfoTask;
import com.example.carrot_market.R;
import com.example.carrot_market.ViewPager.Adapter.SaleItemsAdapter;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SaleItemes extends AppCompatActivity {


    private final int PRODUCT_DETAIL_ACTIVITY_RESULT_CODE=2;

    SaleItemsAdapter viewpager_adapter = new SaleItemsAdapter(getSupportFragmentManager());
    ViewPager tab_view_pager;
    TabLayout pager_tab_layout;

    ImageButton back;


    SaleItemsFragment sell_product_fragment;
    SaleItemsFragment commit_product_fragment2;
    SaleItemsFragment hidden_product_fragment3;


    int count = 0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_itemes);

        sell_product_fragment = new SaleItemsFragment(null,getIntent().getStringExtra("id"));
        commit_product_fragment2 = new SaleItemsFragment("0",getIntent().getStringExtra("id"));
        hidden_product_fragment3 = new SaleItemsFragment("1",getIntent().getStringExtra("id"));

        pager_tab_layout = findViewById(R.id.sale_items_tab);

        tab_view_pager = findViewById(R.id.sale_items_view_pager);

        setting_title(tab_view_pager);
        pager_tab_layout.setupWithViewPager(tab_view_pager);


        back = findViewById(R.id.sale_items_back);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }



    public void setting_title(ViewPager viewPager) {

        viewpager_adapter.addFragment(sell_product_fragment, "전체", SaleItemes.this);
        viewpager_adapter.addFragment(commit_product_fragment2, "판매중", SaleItemes.this);
        viewpager_adapter.addFragment(hidden_product_fragment3, "거래완료", SaleItemes.this);
        viewPager.setAdapter(viewpager_adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("sales_items_update","result");
        switch (requestCode){
            case PRODUCT_DETAIL_ACTIVITY_RESULT_CODE:
                if (resultCode==1){
                    ProductActivityInfoTask productActivityInfoTask=new ProductActivityInfoTask(data.getStringExtra("product_key"),handler);
                    Thread thread=new Thread(productActivityInfoTask);
                    thread.start();
                }
                break;
        }
    }

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            switch (msg.what) {
                case 1:

                    if (msg.getData() != null) {
                        try {
                            JSONObject product_activity_info = new JSONObject(msg.getData().getString("product_activity_info"));

                            if (product_activity_update(sell_product_fragment.arrayList,product_activity_info)){
                                sell_product_fragment.adapter.notifyDataSetChanged();
                            }else if (product_activity_update(commit_product_fragment2.arrayList,product_activity_info)){
                                commit_product_fragment2.adapter.notifyDataSetChanged();
                            }else if(product_activity_update(hidden_product_fragment3.arrayList,product_activity_info)){
                                hidden_product_fragment3.adapter.notifyDataSetChanged();
                            }else {
                                Log.e("favorite_product_update" +
                                        "","변경 할 상품이 없습니다");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        break;
                    }

                }
                    return false;
            }
    });


    private boolean product_activity_update(ArrayList<HomeFragmentItem> product_list,JSONObject update_product) throws JSONException {
        boolean update_product_find_check=false;

        for (int i =0;i<product_list.size();i++){
        if (product_list.get(i).getProduct_key()==update_product.getInt("product_key")){
            product_list.get(i).setComnet_count(update_product.getInt("coment_count"));
            product_list.get(i).setFavorite_count((update_product.getInt("favorite_count")));
            product_list.get(i).setChatting_count(update_product.getInt("chatting_room_count"));
            update_product_find_check=true;
            break;
            }
        }
        return update_product_find_check;
    }


}