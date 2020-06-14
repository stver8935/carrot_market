package com.example.carrot_market.RecyclerView.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.carrot_market.CONTROLLER.FavoriteListActivity;
import com.example.carrot_market.CONTROLLER.MainFragment;
import com.example.carrot_market.CONTROLLER.Product;
import com.example.carrot_market.MODEL.DTO.HomeFragmentItem;
import com.example.carrot_market.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class HomeFragmentAdapter extends RecyclerView.Adapter<HomeFragmentAdapter.CustomViewHolder> {

    private ArrayList<HomeFragmentItem> arrayList;
    Context context;

    public HomeFragmentAdapter( ArrayList<HomeFragmentItem> arrayList) {
        this.arrayList = arrayList;

    }


    @NonNull
    @Override
    public HomeFragmentAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_fragment_item,parent,false);
        context=parent.getContext();
        HomeFragmentAdapter.CustomViewHolder holder=new HomeFragmentAdapter.CustomViewHolder(v);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final HomeFragmentAdapter.CustomViewHolder holder, final int i) {
        GradientDrawable drawable=
                (GradientDrawable) context.getDrawable(R.drawable.background_rounding);
        holder.product_image.setBackground(drawable);
        holder.product_image.setClipToOutline(true);

        if (arrayList.get(holder.getAdapterPosition()).getSales_completed().equals("0")){
            holder.sales_completed.setVisibility(View.GONE);

            ConstraintLayout.LayoutParams params=(ConstraintLayout.LayoutParams)holder.product_match.getLayoutParams();
            params.topMargin=100;
            params.leftMargin=0;
            holder.product_match.setLayoutParams(params);

        }else if (arrayList.get(holder.getAdapterPosition()).getSales_completed().equals("1")){
            holder.sales_completed.setVisibility(View.VISIBLE);
        }

        if (arrayList.get(holder.getAdapterPosition()).getChatting_count()==0){
            holder.chat_room_count.setVisibility(View.GONE);
        }else {
            holder.chat_room_count.setVisibility(View.VISIBLE);

        }
        if (arrayList.get(holder.getAdapterPosition()).getComnet_count()==0){
            holder.coment_count.setVisibility(View.GONE);
        }else {
            holder.coment_count.setVisibility(View.VISIBLE);
        }
        if (arrayList.get(holder.getAdapterPosition()).getFavorite_count()==0){
            holder.favorite_count.setVisibility(View.GONE);
        }else {
            holder.favorite_count.setVisibility(View.VISIBLE);
        }


        //상품이미지의 둥근 모서리 적용
        Glide.with(context).load(API_URL+"/image/"+arrayList.get(holder.getAdapterPosition()).getImage_path()).into(holder.product_image);
        holder.product_image.setClipToOutline(true);

        //아이템 리스트에 넣어줄 데이터들은 연결 해준다.


        DecimalFormat myFormatter = new DecimalFormat("###,###");
        String formattedStringPrice = myFormatter.format(arrayList.get(i).getPrice());
        holder.product_match.setText(formattedStringPrice+" 원");



        holder.product_title.setText(arrayList.get(i).getTitle());
        holder.product_position.setText(arrayList.get(i).getLocation());
        holder.favorite_count.setText(""+arrayList.get(i).getFavorite_count());
        holder.chat_room_count.setText(""+arrayList.get(i).getChatting_count());
        holder.coment_count.setText(""+arrayList.get(holder.getAdapterPosition()).getComnet_count());




        //상품 상세정보를 보기위해 구현된 클릭 리스너
        //코드 재사용성을 높이기 위해 상품 정보와 상대방 정보를 로드하기 위한 작업을 분할 했으므로
        //상품정보를 얻어오기 위한 키값  product_key 판매자의 정보를 얻기위한 키값 상대방의 아이디를 보내준다.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, Product.class);
                  intent.putExtra("product_key",""+arrayList.get(holder.getAdapterPosition()).getProduct_key());
                  intent.putExtra("id",""+arrayList.get(holder.getAdapterPosition()).getId());
                  intent.putExtra("request_code",2);


                Log.e("homefragmnetadaptercall",""+arrayList.get(holder.getAdapterPosition()).getProduct_key());

                    //이 어댑터는 좋아요 리스트 액티비티와 메인 상품리스트 액티비티가 공유하고 있으므로 분기 처리 해준다
                 if (context instanceof MainFragment){
                     ((MainFragment)context).startActivityForResult(intent,2);
                  }else if (context instanceof FavoriteListActivity){
                     ((FavoriteListActivity)context).startActivityForResult(intent,2);
                  }


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
        protected TextView product_title,product_position,product_match,chat_room_count,favorite_count,coment_count;
        protected TextView sales_completed;

        public CustomViewHolder(@NonNull View item) {
            super(item);
        this.product_image=item.findViewById(R.id.fragment_item_imageView);
        this.product_title=item.findViewById(R.id.fragment_item_title);
        this.product_position=item.findViewById(R.id.fragment_item_position);
        this.product_match=item.findViewById(R.id.fragment_item_match);
        chat_room_count=item.findViewById(R.id.fragment_item_chat_count);
        favorite_count=item.findViewById(R.id.fragment_item_favorite);
        coment_count=item.findViewById(R.id.fragment_item_coment_count);
        this.sales_completed=item.findViewById(R.id.fragment_item_sales_completed);

        }
    }
}
