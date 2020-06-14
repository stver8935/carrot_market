package com.example.carrot_market.RecyclerView.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.carrot_market.CONTROLLER.Chatting;
import com.example.carrot_market.CONTROLLER.METHOD.DateTimeConverter;
import com.example.carrot_market.MODEL.DTO.ChattingListItem;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;

import java.text.ParseException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class ChattingListAdapter extends RecyclerView.Adapter<ChattingListAdapter.CustomViewHolder> {

    Context context;
    ArrayList<ChattingListItem> arrayList;
    UserInfoSave userInfoSave;


    public ChattingListAdapter(Context context, ArrayList<ChattingListItem> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.chatting_list_item,parent,false);
        ChattingListAdapter.CustomViewHolder holder=new ChattingListAdapter.CustomViewHolder(v);
        userInfoSave=new UserInfoSave(context);

        return holder;


    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, final int i) {

        holder.id.setText(arrayList.get(i).getProfile_id());
        holder.location.setText(arrayList.get(i).getLocation());


        DateTimeConverter dateTimeConverter=new DateTimeConverter(arrayList.get(i).getTime());
        try {
            holder.time.setText(dateTimeConverter.return_month_day());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.contents.setText(arrayList.get(i).getContents());


         Glide.with(context).load(API_URL+"/image/"+arrayList.get(i).getProduct_image_path()).into(holder.product_image);

        Log.e("profile_image",arrayList.get(i).getProfile_image_path());

        if (arrayList.get(i).getProfile_image_path().equals("null")){
            holder.profile_image.setImageDrawable(context.getResources().getDrawable(R.drawable.profile_image_man));
        }else {

            Glide.with(context).load(API_URL+"/image/"+arrayList.get(i).getProfile_image_path()).into(holder.profile_image);

        }
        Log.e("adapter_call","adapter_call");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, Chatting.class);
                intent.putExtra("chatting_room_key",arrayList.get(holder.getAdapterPosition()).getChatting_room_key());
                intent.putExtra("product_key",arrayList.get(holder.getAdapterPosition()).getProduct_key());
                intent.putExtra("id",arrayList.get(holder.getAdapterPosition()).getProfile_id());
                context.startActivity(intent);


            }
        });


    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView id,location,time,count,contents;
        protected CircleImageView profile_image;
        protected ImageView product_image;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            id=itemView.findViewById(R.id.chatting_list_item_profile_id);
            location=itemView.findViewById(R.id.chatting_list_item_location);
            time=itemView.findViewById(R.id.chatting_list_item_chatting_time);
            contents=itemView.findViewById(R.id.chatting_list_item_chatting_contents);
            profile_image=itemView.findViewById(R.id.chatting_list_item_profile_image);
            product_image=itemView.findViewById(R.id.chatting_list_item_chatting_product_image);


        }


    }
}
