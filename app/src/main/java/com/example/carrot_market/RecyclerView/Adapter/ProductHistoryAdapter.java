package com.example.carrot_market.RecyclerView.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.carrot_market.CONTROLLER.Dialog.ProductHistoryDialog;
import com.example.carrot_market.CONTROLLER.Product;
import com.example.carrot_market.CONTROLLER.ProductHistory;
import com.example.carrot_market.CONTROLLER.SelectBuyer;
import com.example.carrot_market.MODEL.DTO.HomeFragmentItem;
import com.example.carrot_market.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class ProductHistoryAdapter extends RecyclerView.Adapter<ProductHistoryAdapter.CustomViewHolder> implements ProductHistoryDialog.changetext {

    private ArrayList<HomeFragmentItem> arrayList;
    private Context context;
    private ProductHistoryDialog ProductHistoryDialog = null;

    public ProductHistoryAdapter(ArrayList<HomeFragmentItem>arrayList,Context context) {
        this.arrayList = arrayList;
        this.context=context;
    }


    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.itme_product_histoy,parent,false);
        ProductHistoryAdapter.CustomViewHolder holder=new ProductHistoryAdapter.CustomViewHolder(v);
//        context=parent.getContext();

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, int position) {

        Glide.with(context).load(API_URL+"image/"+arrayList.get(position).getImage_path()).into(holder.product_image);


//        deal_review_leave=itemView.findViewById(R.id.product_history_deal_review_leave);
//        more=itemView.findViewById(R.id.product_histoy_more);
//        sales_completed=itemView.findViewById(R.id.fragment_item_sales_completed);


        //숨기기 상태가 아니라면 0 숨기기 상태면 1
        if (arrayList.get(holder.getAdapterPosition()).getHidden().equals("0")){

            //판매중인것이라면
            if (arrayList.get(holder.getAdapterPosition()).getSales_completed().equals("0")){
                holder.deal_review_leave.setText("거래완료");
                holder.deal_review_leave.setTextColor(context.getResources().getColor(R.color.colororange));
                holder.deal_review_leave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((ProductHistory)context).changetext("ing",""+arrayList.get(holder.getAdapterPosition()).getProduct_key(),"commit");

                    }
                });
            }
            //아이템 상태가 판매 완료 된것
            else if (arrayList.get(holder.getAdapterPosition()).getSales_completed().equals("1")){

                holder.deal_review_leave.setText("거래 후기 남기기");
                holder.deal_review_leave.setTextColor(context.getResources().getColor(R.color.colororange));
                holder.deal_review_leave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context, SelectBuyer.class);
                        intent.putExtra("product_key",""+arrayList.get(holder.getAdapterPosition()).getProduct_key());
                        intent.putExtra("image_path",arrayList.get(holder.getAdapterPosition()).getImage_path());
                        intent.putExtra("title",arrayList.get(holder.getAdapterPosition()).getTitle());
                        context.startActivity(intent);
                    }
                });

            }
        }
        //숨기기 상태라면
        else if (arrayList.get(holder.getAdapterPosition()).getHidden().equals("1")){

            holder.deal_review_leave.setText("숨기기 해제");
            holder.deal_review_leave.setTextColor(context.getResources().getColor(R.color.colorblack));
            holder.deal_review_leave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.e("few","few");
                    String work_type="hidden_unlock";

                    ((ProductHistory)context).changetext("hidden",""+arrayList.get(holder.getAdapterPosition()).getProduct_key(),work_type);

                }
            });


        }

        DecimalFormat myFormatter = new DecimalFormat("###,###");
        String formattedStringPrice = myFormatter.format(arrayList.get(holder.getAdapterPosition()).getPrice());
        holder.price.setText(formattedStringPrice+" 원");

        holder.locaiton.setText(arrayList.get(holder.getAdapterPosition()).getLocation());


        if (arrayList.get(holder.getAdapterPosition()).getChatting_count()<1){
            holder.chat_count.setVisibility(View.GONE);
        }else {
           holder.chat_count.setVisibility(View.VISIBLE);
        }
        if (arrayList.get(holder.getAdapterPosition()).getFavorite_count()<1){
            holder.favorite_count.setVisibility(View.GONE);
        }else {
            holder.favorite_count.setVisibility(View.VISIBLE);
        }
        if (arrayList.get(holder.getAdapterPosition()).getComnet_count()<1){
            holder.coment_count.setVisibility(View.GONE);
        }else {
         holder.coment_count.setVisibility(View.VISIBLE);
        }


        holder.title.setText(arrayList.get(holder.getAdapterPosition()).getTitle());
        holder.chat_count.setText(""+arrayList.get(holder.getAdapterPosition()).getChatting_count());
        holder.favorite_count.setText(""+arrayList.get(holder.getAdapterPosition()).getFavorite_count());
        holder.coment_count.setText(""+arrayList.get(holder.getAdapterPosition()).getComnet_count());




        if (arrayList.get(holder.getAdapterPosition()).getSales_completed().equals("0")){
            holder.sales_completed.setVisibility(View.GONE);
            ConstraintLayout.LayoutParams params=(ConstraintLayout.LayoutParams)holder.price.getLayoutParams();
            params.topMargin=100;
            params.leftMargin=0;
            holder.price.setLayoutParams(params);
        }else {
        holder.sales_completed.setVisibility(View.VISIBLE);
        }



        //아이템 상태가 판매중인 것



        //거래 후기 남기기 버튼
        //최근대화방에서 찾기



        //상품 설정 클릭 리스너
        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String product_key=""+arrayList.get(holder.getAdapterPosition()).getProduct_key();
                Log.e("sales_completed",""+arrayList.get(holder.getAdapterPosition()).getSales_completed());
                Log.e("product_key",""+product_key);

                //숨김상태일때 또는 숨김 상태가 아닐때
            if (arrayList.get(holder.getAdapterPosition()).getHidden().equals("0")) {
                if (arrayList.get(holder.getAdapterPosition()).getSales_completed().equals("0")) {
                    ProductHistoryDialog = new ProductHistoryDialog(context,"ing",product_key); // 왼쪽 버튼 이벤트
                } else if (arrayList.get(holder.getAdapterPosition()).getSales_completed().equals("1")) {
                    ProductHistoryDialog = new ProductHistoryDialog(context,"commit",product_key); // 왼쪽 버튼 이벤트
                }
            }else if (arrayList.get(holder.getAdapterPosition()).getHidden().equals("1")){
                ProductHistoryDialog = new ProductHistoryDialog(context,"hidden",product_key); // 왼쪽 버튼 이벤트
            }



                ProductHistoryDialog.setCancelable(true);
                ProductHistoryDialog.getWindow().setGravity(Gravity.TOP);
                ProductHistoryDialog.show();



            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Product.class);
                intent.putExtra("product_key",""+arrayList.get(holder.getAdapterPosition()).getProduct_key());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public void changetext(String fragment_type, String product_key, String work_type) {

    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView product_image,more;
        protected TextView title,price,locaiton,sales_completed,deal_review_leave,chat_count,favorite_count,coment_count;

        public CustomViewHolder(@NonNull View itemView)
        {
            super(itemView);
            title=itemView.findViewById(R.id.product_histroy_item_title);
            product_image=itemView.findViewById(R.id.product_history_imageview);
            more=itemView.findViewById(R.id.product_histoy_more);
            price=itemView.findViewById(R.id.product_histroy_item_price);
            locaiton=itemView.findViewById(R.id.product_history_item_location);
            sales_completed=itemView.findViewById(R.id.product_histroy_item_sales_completed);
            deal_review_leave=itemView.findViewById(R.id.product_history_deal_review_leave);
            chat_count=itemView.findViewById(R.id.product_history_item_chat_count);
            favorite_count=itemView.findViewById(R.id.product_history_item_favorite_count);
            coment_count=itemView.findViewById(R.id.product_history_item_coment_count);



        }
    }

}
