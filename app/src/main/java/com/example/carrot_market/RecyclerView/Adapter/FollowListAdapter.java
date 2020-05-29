package com.example.carrot_market.RecyclerView.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.carrot_market.MODEL.DTO.FollowListItem;
import com.example.carrot_market.MODEL.HttpConnect.FollowTask;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class FollowListAdapter extends RecyclerView.Adapter<FollowListAdapter.CustomViewholder> {


    private ArrayList<FollowListItem> arrayList;
    private Context context;
    private UserInfoSave userInfoSave;
    private boolean follow_check=false;




    public FollowListAdapter(ArrayList<FollowListItem> arrayList) {

        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public FollowListAdapter.CustomViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_follow_list,parent,false);
        FollowListAdapter.CustomViewholder holder = new FollowListAdapter.CustomViewholder(v);
        context=parent.getContext();
        userInfoSave=new UserInfoSave(parent.getContext());

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final FollowListAdapter.CustomViewholder holder, int position) {





        holder.id.setText(arrayList.get(holder.getAdapterPosition()).getId());

        String[] address_location=arrayList.get(holder.getAdapterPosition()).getAddress().split("구");

        holder.address.setText(address_location[0]+"구 "+arrayList.get(holder.getAdapterPosition()).getLocation());


        if (!arrayList.get(holder.getAdapterPosition()).getProfile_image_path().equals("null")) {
            Glide.with(context).load(API_URL + "image/" + arrayList.get(holder.getAdapterPosition()).getProfile_image_path()).into(holder.profile_image);
        }




        holder.follow_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (follow_check){
                    follow_check=false;
                    holder.follow_button.setBackground(context.getResources().getDrawable(R.drawable.corner_round_color_orange));
                }else {
                    follow_check=true;
                    holder.follow_button.setBackground(context.getResources().getDrawable(R.drawable.round_corner));
                }
                FollowTask followTask=new FollowTask(userInfoSave.return_account().getId(),arrayList.get(holder.getAdapterPosition()).getId(),null);
                Thread thread=new Thread(followTask);
                thread.start();

            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomViewholder extends RecyclerView.ViewHolder {

        CircleImageView profile_image;
        TextView id,address,follow_button;
        public CustomViewholder(@NonNull View itemView) {
            super(itemView);

            profile_image=itemView.findViewById(R.id.follow_list_item_profile_image);
            id=itemView.findViewById(R.id.follow_list_item_id);
            address=itemView.findViewById(R.id.follow_list_item_address);
            follow_button=itemView.findViewById(R.id.follow_list_item_follow_button);
        }
    }


}
