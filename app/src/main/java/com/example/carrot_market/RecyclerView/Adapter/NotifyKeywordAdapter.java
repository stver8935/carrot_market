package com.example.carrot_market.RecyclerView.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.carrot_market.CONTROLLER.Product;
import com.example.carrot_market.MODEL.DTO.NotifyKeywordItem;
import com.example.carrot_market.MODEL.HttpConnect.KeyWordNotificationListDeleteTask;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class NotifyKeywordAdapter extends RecyclerView.Adapter<NotifyKeywordAdapter.CustomViewHolder> {

    private ArrayList<NotifyKeywordItem> arrayList;
    private Context context;
    private UserInfoSave userInfoSave;


    public NotifyKeywordAdapter(ArrayList<NotifyKeywordItem> arrayList) {
        this.arrayList = arrayList;

    }

    @NonNull
    @Override
    public NotifyKeywordAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.keyword_item,parent,false);
        context=parent.getContext();
        userInfoSave=new UserInfoSave(context);



        NotifyKeywordAdapter.CustomViewHolder holder=new NotifyKeywordAdapter.CustomViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final NotifyKeywordAdapter.CustomViewHolder holder,  int position) {
        holder.text.setText(arrayList.get(position).getText());
        holder.date.setText(arrayList.get(position).getDate());
        if (arrayList.get(position).getClear()){
            holder.clear.setVisibility(View.GONE);
        }
        else {
            holder.clear.setVisibility(View.VISIBLE);
        }

        Glide.with(context).load(API_URL+"image/"+arrayList.get(holder.getAdapterPosition()).getImage_path()).into(holder.imageView);

        holder.clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //로컬데이터베이스에서 실제 데이터 삭제 넣기
                arrayList.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                Toast.makeText(context, ""+holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Product.class);
                intent.putExtra("product_key",arrayList.get(holder.getAdapterPosition()).getProduct_key());
                context.startActivity(intent);
            }
        });


        holder.clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                KeyWordNotificationListDeleteTask keyWordNotificationListDeleteTask=new KeyWordNotificationListDeleteTask(arrayList.get(holder.getAdapterPosition()).getKey_word_key(),userInfoSave.return_account().getId(),handler);
                Thread key_word_notificaiton_list_delete_thread=new Thread(keyWordNotificationListDeleteTask);
                key_word_notificaiton_list_delete_thread.start();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
       ImageView imageView,clear;
       TextView text,date;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            clear=itemView.findViewById(R.id.keyword_clear);
            imageView=itemView.findViewById(R.id.keyword_image);
            text=itemView.findViewById(R.id.keyword_text);
            date=itemView.findViewById(R.id.keyword_date);
        }
    }


    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            switch (msg.what) {
                case 1:
                    try {
                        JSONObject delete_response_code = new JSONObject(msg.getData().getString("response_code"));
                        Log.e("delete_response_code", delete_response_code.toString());

                        if (delete_response_code.getBoolean("success")) {

                            //전체 삭제이라면
                            if (delete_response_code.getString("action").equals("all")) {
                                notifyItemRangeRemoved(0, arrayList.size());
                                arrayList.clear();
                            } else {
                                //부분 삭제이라면

                                for (int i = 0; i < arrayList.size(); i++) {
                                    if (arrayList.get(i).getKey_word_key().equals(delete_response_code.getString("action"))) {
                                        arrayList.remove(i);
                                        notifyItemRemoved(i);
                                    }
                                }
                            }


                        } else {
                            Toast.makeText(context, "키워드 삭제에 실패 했습니다.", Toast.LENGTH_SHORT).show();
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
