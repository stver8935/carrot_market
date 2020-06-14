package com.example.carrot_market.RecyclerView.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.carrot_market.CONTROLLER.METHOD.DateTimeConverter;
import com.example.carrot_market.CONTROLLER.MyLeaveDealReview;
import com.example.carrot_market.CONTROLLER.Product;
import com.example.carrot_market.CONTROLLER.ProfileDetail;
import com.example.carrot_market.MODEL.DTO.NotifyActiveItem;
import com.example.carrot_market.MODEL.HttpConnect.ActivityNotificationDeleteTask;
import com.example.carrot_market.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class NotifyActiveAdapter extends RecyclerView.Adapter<NotifyActiveAdapter.CustomViewHolder> {

    ArrayList<NotifyActiveItem> arrayList;
    Context context;

    public NotifyActiveAdapter(ArrayList<NotifyActiveItem> arrayList, Context context) {
        this.arrayList=arrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public NotifyActiveAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.active_item,parent,false);

        NotifyActiveAdapter.CustomViewHolder holder=new NotifyActiveAdapter.CustomViewHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull final NotifyActiveAdapter.CustomViewHolder holder,  int position) {


        if (arrayList.get(holder.getAdapterPosition()).isDelete_check()){
            holder.clear.setVisibility(View.GONE);
        }else {
            holder.clear.setVisibility(View.VISIBLE);
        }



        switch (arrayList.get(holder.getAdapterPosition()).getDivision_type()){

            case "favorite":
                holder.text.setText(arrayList.get(holder.getAdapterPosition()).getId()+" 님이 '"+
                        arrayList.get(holder.getAdapterPosition()).getDescription()+"' 게시물에 좋아요를 누르셨습니다.");


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context, ProfileDetail.class);
                        intent.putExtra("profile_id",arrayList.get(holder.getAdapterPosition()).getId());
                        context.startActivity(intent);
                    }
                });



                break;
            case "follow":
                holder.text.setText(arrayList.get(holder.getAdapterPosition()).getId()+" 님이 모아보기를 했습니다");


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context, ProfileDetail.class);
                        intent.putExtra("profile_id",arrayList.get(holder.getAdapterPosition()).getId());
                        context.startActivity(intent);
                    }
                });

                break;
            case "review":

                break;
            case "coment":
                holder.text.setText(arrayList.get(holder.getAdapterPosition()).getId()+" 님이 '"+
                        arrayList.get(holder.getAdapterPosition()).getDescription()+"' 게시물에 댓글을 다셨습니다.");

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context, Product.class);
                        intent.putExtra("product_key",arrayList.get(holder.getAdapterPosition()).getProduct_key());
                        context.startActivity(intent);

                    }
                });
                break;
            case "manner":
                holder.text.setText(arrayList.get(holder.getAdapterPosition()).getId()+" 님이 매너 평가를 남기셨습니다.");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context, MyLeaveDealReview.class);
                        intent.putExtra("product_key",arrayList.get(holder.getAdapterPosition()).getProduct_key());
                        intent.putExtra("opponent_id",arrayList.get(holder.getAdapterPosition()).getId());
                        intent.putExtra("accept_check",true);
                        context.startActivity(intent);

                    }
                });


                break;


        }

        if (arrayList.get(holder.getAdapterPosition()).getImage_path().isEmpty()||arrayList.get(holder.getAdapterPosition()).getImage_path().equals("null")){
         holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.profile_image_man));
        }else {
            Glide.with(context).load(API_URL + "image/" + arrayList.get(holder.getAdapterPosition()).getImage_path()).into(holder.imageView);
        }


        //데이터 베이스에서 실제 데이터로 받기
        holder.date.setText(arrayList.get(position).getTime_stamp());
        DateTimeConverter dateTimeConverter=new DateTimeConverter(arrayList.get(holder.getAdapterPosition()).getTime_stamp());
        try {
            holder.date.setText(dateTimeConverter.return_yaer_month_day());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        //holder.imageView.setImageURI();

//        if (arrayList.get(position).getClear()) {
//            holder.clear.setVisibility(View.GONE);
//        }
//        else {
//            holder.clear.setVisibility(View.VISIBLE);
//        }

        holder.clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityNotificationDeleteTask activityNotificationDeleteTask=new ActivityNotificationDeleteTask(arrayList.get(holder.getAdapterPosition()).getKey(),handler);
                Thread thread=new Thread(activityNotificationDeleteTask);
                thread.start();
            }
        });



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imageView;
        private TextView text,date;
        private ImageView clear;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            clear=itemView.findViewById(R.id.active_clear);
            imageView=itemView.findViewById(R.id.active_image);
            text=itemView.findViewById(R.id.active_text);
            date=itemView.findViewById(R.id.active_date);

        }
    }


    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

        switch (msg.what){

         //활동알람 삭제후 반환코드를 받아 클라이언트 데이터 삭제처리는 하는 부분
            case 0:
                try {
                    JSONObject delete_response=new JSONObject(msg.getData().getString("active_notify_delete_respponse"));

                for (int i=0;i<arrayList.size();i++){
                    if (arrayList.get(i).getKey().equals(delete_response.getString("key"))){
                        arrayList.remove(i);
                        notifyItemRemoved(i);
                    }
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
