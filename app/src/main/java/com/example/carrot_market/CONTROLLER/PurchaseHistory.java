package com.example.carrot_market.CONTROLLER;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.CONTROLLER.Dialog.PurchaseHistoryDialog;
import com.example.carrot_market.MODEL.DTO.HomeFragmentItem;
import com.example.carrot_market.MODEL.HttpConnect.PurchaseHistoryDeleteTask;
import com.example.carrot_market.MODEL.HttpConnect.PurchaseHistoryTask;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.PurchaseHistoryAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class PurchaseHistory extends AppCompatActivity implements PurchaseHistoryDialog.purchase_history_work {




    private RecyclerView recyclerView;
    private PurchaseHistoryAdapter adapter;
    private ArrayList<HomeFragmentItem> arrayList;
    private ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_history);

        arrayList=new ArrayList<>();
        adapter=new PurchaseHistoryAdapter(arrayList);


        back=findViewById(R.id.purchase_history_back);
        recyclerView=findViewById(R.id.purchase_history_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(PurchaseHistory.this));
        recyclerView.setAdapter(adapter);


        PurchaseHistoryTask purchaseHistoryTask=new PurchaseHistoryTask(new UserInfoSave(PurchaseHistory.this).return_account().getId(),"0",handler);
        Thread thread=new Thread(purchaseHistoryTask);
        thread.start();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            finish();
            }
        });

    }

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            switch (msg.what){
                case 0:

                    Log.e("product_error_chek",""+msg.getData().getString("purchase_history"));
                    JSONArray jsonArray= null;

                    try {
                        jsonArray = new JSONArray(msg.getData().getString("purchase_history"));

                        for (int i=0;i<jsonArray.length();i++){
                            HomeFragmentItem item=new HomeFragmentItem();
                            item.setSales_completed(jsonArray.getJSONObject(i).getString("sales_completed"));
                            item.setId(jsonArray.getJSONObject(i).getString("id"));
                            item.setHidden(jsonArray.getJSONObject(i).getString("hidden"));
                            item.setProduct_key(Integer.parseInt(jsonArray.getJSONObject(i).getString("product_key")));
                            Log.e("itme_key",""+jsonArray.getJSONObject(i).getString("product_key"));
                            item.setTitle(jsonArray.getJSONObject(i).getString("title"));
                            item.setPrice(jsonArray.getJSONObject(i).getInt("price"));
                            item.setChatting_count(jsonArray.getJSONObject(i).getInt("chatting_room_count"));
                            item.setFavorite_count(jsonArray.getJSONObject(i).getInt("favorite_count"));
                            item.setComnet_count(jsonArray.getJSONObject(i).getInt("coment_count"));
                            item.setDate(jsonArray.getJSONObject(i).getString("date"));
                            item.setLocation(jsonArray.getJSONObject(i).getString("location"));
                            item.setImage_path(jsonArray.getJSONObject(i).getString("image_path"));
                            arrayList.add(item);
                            adapter.notifyItemInserted(arrayList.size()-1);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;

                case 1:
                    Log.e("delete","delete");

                    for (int i=0;i<arrayList.size();i++){
                        String product_key=""+arrayList.get(i).getProduct_key();

                        Log.e("pk",product_key);
                        Log.e("pk2",""+msg.getData().getString("product_key"));

                        if (product_key.equals(msg.getData().getString("product_key"))){
                            arrayList.remove(i);
                            adapter.notifyItemRemoved(i);
                        }
                    }


                   break;
            }



            return false;

        }
    });

    @Override
    public void purchase_history_works(String product_key, String work_type) {


        switch (work_type){
            case "deal_review_leave":

                break;
            case "delete":
                PurchaseHistoryDeleteTask purchaseHistoryDeleteTask=new PurchaseHistoryDeleteTask(handler,product_key);
                Thread thread=new Thread(purchaseHistoryDeleteTask);
                thread.run();
                break;
        }
    }

}
