package com.example.carrot_market.CONTROLLER;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.MODEL.DTO.DetailMannerItem;
import com.example.carrot_market.MODEL.HttpConnect.MannerEvaluationTask;
import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.DetailMannerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailManner extends AppCompatActivity {

    private RecyclerView recyclerView,recyclerView_bad;
    private DetailMannerAdapter adapter,adapter_bad;
    private ArrayList<DetailMannerItem> arrayList=new ArrayList<>();
    private ArrayList<DetailMannerItem> arrayList_bad=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_manner);

    recyclerView=findViewById(R.id.detail_manner_list_recycler);
    recyclerView_bad=findViewById(R.id.detail_manner_list_recycler_bad);


    adapter=new DetailMannerAdapter(arrayList);
    adapter_bad=new DetailMannerAdapter(arrayList_bad);


    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    recyclerView_bad.setAdapter(adapter_bad);
    recyclerView_bad.setLayoutManager(new LinearLayoutManager(this));




    if (getIntent().getStringExtra("id")!=null) {
        MannerEvaluationTask task = new MannerEvaluationTask(handler, getIntent().getStringExtra("id"), 10);
        Thread thread = new Thread(task);
        thread.run();
    }



    }

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

       switch(msg.what){
           case 1:
               Log.e("manner",msg.getData().getString("manner_evaluation_list"));
               JSONArray review_evaluation_list_json_array= null;
               try {
                   review_evaluation_list_json_array = new JSONArray(msg.getData().getString("manner_evaluation_list"));
                   DetailMannerItem item;
               for (int i=0;i<review_evaluation_list_json_array.length();i++){
                   JSONObject review_evaluation_obj=review_evaluation_list_json_array.getJSONObject(i);
                   item=new DetailMannerItem();

                   if (review_evaluation_obj.getInt("evaluation_type")==1){
                   item.setTitle(review_evaluation_obj.getString("review_title"));
                   item.setCount(""+review_evaluation_obj.getInt("review_count"));
                   arrayList.add(item);
                   adapter.notifyItemInserted(arrayList.size());

                   }else if (review_evaluation_obj.getInt("evaluation_type")==0){

                       item.setTitle(review_evaluation_obj.getString("review_title"));
                       item.setCount(""+review_evaluation_obj.getInt("review_count"));
                       arrayList_bad.add(item);
                       adapter_bad.notifyItemInserted(arrayList.size());
                   }

                   Log.e("manner_detail",""+i);
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
