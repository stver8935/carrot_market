package com.example.carrot_market.RecyclerView.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.CONTROLLER.Product;
import com.example.carrot_market.R;
import com.example.carrot_market.MODEL.DTO.HomeFragmentItem;

import java.util.ArrayList;

public class HomeFragmentAdapter extends RecyclerView.Adapter<HomeFragmentAdapter.CustomViewHolder> {

    private ArrayList<HomeFragmentItem> arrayList;
    Context context;

    public HomeFragmentAdapter(Context context, ArrayList<HomeFragmentItem> arrayList) {
        this.arrayList = arrayList;
        this.context=context;
    }


    @NonNull
    @Override
    public HomeFragmentAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_fragment_item,parent,false);

        HomeFragmentAdapter.CustomViewHolder holder=new HomeFragmentAdapter.CustomViewHolder(v);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull HomeFragmentAdapter.CustomViewHolder holder, final int i) {

        //상품이미지의 둥근 모서리 적용
        holder.product_image.setClipToOutline(true);


        //아이템 리스트에 넣어줄 데이터들은 연결 해준다.

        holder.product_match.setText(arrayList.get(i).getMatch());
        holder.product_title.setText(arrayList.get(i).getTitle());
        holder.product_position.setText(arrayList.get(i).getPosition());

        holder.product_image.setImageBitmap(arrayList.get(i).getImage());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              Intent intent=new Intent(context, Product.class);
              context.startActivity(intent);

            }
        });
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }



    public class CustomViewHolder extends RecyclerView.ViewHolder {

        //제품의 메인이미지
        protected ImageView product_image;
        //제품의 제목 제품거래 위치, 제품 가격
        protected TextView product_title,product_position,product_match;

        public CustomViewHolder(@NonNull View item) {
            super(item);
        this.product_image=item.findViewById(R.id.fragment_item_imageView);
        this.product_title=item.findViewById(R.id.fragment_item_title);
        this.product_position=item.findViewById(R.id.fragment_item_position);
        this.product_match=item.findViewById(R.id.fragment_item_match);

        }
    }
}
