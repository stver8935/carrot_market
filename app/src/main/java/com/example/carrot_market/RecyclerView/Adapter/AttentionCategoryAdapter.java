package com.example.carrot_market.RecyclerView.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.MODEL.DTO.AttentionCategoryItem;
import com.example.carrot_market.MODEL.HttpConnect.CategoryUpadteTask;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;

import java.util.ArrayList;

public class AttentionCategoryAdapter extends RecyclerView.Adapter<AttentionCategoryAdapter.CustomVIewHolder> {

    ArrayList<AttentionCategoryItem> arrayList;
    Context context;
    UserInfoSave userInfoSave;
    public AttentionCategoryAdapter(ArrayList<AttentionCategoryItem> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public AttentionCategoryAdapter.CustomVIewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_setting_item,parent,false);
        AttentionCategoryAdapter.CustomVIewHolder holder=new AttentionCategoryAdapter.CustomVIewHolder(v);
    context=parent.getContext();
    userInfoSave=new UserInfoSave(context);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AttentionCategoryAdapter.CustomVIewHolder holder, int position) {

        holder.category.setText(arrayList.get(holder.getAdapterPosition()).getTitle());
        holder.category.setChecked(arrayList.get(holder.getAdapterPosition()).getCheck());


        //데이터 업데이트
        holder.category.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                arrayList.get(holder.getAdapterPosition()).setCheck(holder.category.isChecked());
                int category_count=0;

                for (int i=0;i<arrayList.size();i++){

                    if (arrayList.get(i).getCheck()){
                        category_count++;
                    }
                }
                if (category_count<=0){
                    holder.category.setChecked(true);
                    arrayList.get(holder.getAdapterPosition()).setCheck(true);

                    Toast.makeText(context, "최소 한개 이상 선택 되어야 합니다.", Toast.LENGTH_SHORT).show();
                }else {


                    Log.e("checked", "" + holder.category.getText().toString());

                    CategoryUpadteTask task = new CategoryUpadteTask(userInfoSave.return_account().getId(), holder.category.getText().toString());
                    Thread thread = new Thread(task);
                    thread.run();
                }

            notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomVIewHolder extends RecyclerView.ViewHolder {
        CheckBox category;

        public CustomVIewHolder(@NonNull View itemView) {
            super(itemView);
            category=itemView.findViewById(R.id.category_setting_item);
        }
    }
}
