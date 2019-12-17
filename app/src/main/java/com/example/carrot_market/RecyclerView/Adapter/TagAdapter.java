package com.example.carrot_market.RecyclerView.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.R;
import com.example.carrot_market.MODEL.DTO.TagItem;

import java.util.ArrayList;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.CustomViewHolder> {

    ArrayList<TagItem> arrayList;
    Context context;

    public TagAdapter(ArrayList<TagItem> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public TagAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_item,parent,false);
        TagAdapter.CustomViewHolder holder=new TagAdapter.CustomViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TagAdapter.CustomViewHolder holder, final int position) {

        holder.tag.setText(arrayList.get(position).getTag());


        //각 태그 클릭시 클릭한 태그의 제목으로 검색 구현
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                //부모 액티비티의 컨택스트로 액티비티를 호출한다.
//                Intent intent=new Intent(context,);
//                //클릭한 태그의 제목을 인텐트에 담아서 검색 결과를 보여주는 액티비티로 전송한다.
//                intent.putExtra("검색키",arrayList.get(position).getTag());
//                context.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView tag;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            tag=itemView.findViewById(R.id.tag_item);
        }
    }
}
