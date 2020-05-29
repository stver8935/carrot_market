package com.example.carrot_market.RecyclerView.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.carrot_market.CONTROLLER.ProfileDetail;
import com.example.carrot_market.MODEL.DTO.UserListItem;
import com.example.carrot_market.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class SearchUserListAdapter extends RecyclerView.Adapter<SearchUserListAdapter.CustomViewHolder> {

    private ArrayList<UserListItem> arrayList;
    private Context context;


    public SearchUserListAdapter(ArrayList<UserListItem> arrayList) {
        this.arrayList = arrayList;
    }



    @NonNull
    @Override
    public SearchUserListAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_list,parent,false);
        SearchUserListAdapter.CustomViewHolder holder=new SearchUserListAdapter.CustomViewHolder(v);
        context=parent.getContext();

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchUserListAdapter.CustomViewHolder holder, int position) {



        if (!arrayList.get(holder.getAdapterPosition()).getProfile_image_path().equals("null")) {
            Glide.with(context).load(API_URL + "image/" + arrayList.get(holder.getAdapterPosition()).getProfile_image_path()).into(holder.profile_image);
        }

        holder.name.setText(arrayList.get(holder.getAdapterPosition()).getName());
        holder.id.setText(arrayList.get(holder.getAdapterPosition()).getId());
        holder.address.setText(arrayList.get(holder.getAdapterPosition()).getAddress());





        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ProfileDetail.class);
                intent.putExtra("profile_id",arrayList.get(holder.getAdapterPosition()).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView profile_image;
        private TextView name,id,address;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            profile_image=itemView.findViewById(R.id.user_list_profile_image);
            name=itemView.findViewById(R.id.user_list_name);
            id=itemView.findViewById(R.id.user_list_id);
            address=itemView.findViewById(R.id.user_list_location);

        }
    }
}
