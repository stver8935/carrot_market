package com.example.carrot_market.RecyclerView.Adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.CONTROLLER.Dialog.ProductHistoryDialog;
import com.example.carrot_market.MODEL.DTO.ProductHistoryDialogItem;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;

import java.util.ArrayList;

public class ProductHistoryDialogAdapter extends RecyclerView.Adapter<ProductHistoryDialogAdapter.CustomViewHolder> {

    ArrayList<ProductHistoryDialogItem> arrayList;
    Context context;
    ProductHistoryDialog productHistoryDialog;
    UserInfoSave userInfoSave;
    public ProductHistoryDialogAdapter(ArrayList<ProductHistoryDialogItem> arrayList,ProductHistoryDialog productHistoryDialog) {
        this.productHistoryDialog=productHistoryDialog;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_history_dialog,parent,false);
        context=parent.getContext();
        ProductHistoryDialogAdapter.CustomViewHolder holder=new ProductHistoryDialogAdapter.CustomViewHolder(v);
        userInfoSave=new UserInfoSave(context);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, int position) {


        holder.dialog_title.setText(arrayList.get(holder.getAdapterPosition()).getButton_title());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                switch (arrayList.get(holder.getAdapterPosition()).getButton_type()){

                    //판매중으로 변경
                    case "ing":
//                        Toast.makeText(context, "product_ing", Toast.LENGTH_SHORT).show();
                        break;
                    //게시글 수정
                    case "modify":
//                        Toast.makeText(context, "product_modify", Toast.LENGTH_SHORT).show();
                        break;
                        //게시글 숨기기
                    case "hidden":
//                        Toast.makeText(context, "product_hidden", Toast.LENGTH_SHORT).show();
                        break;
                        //게시글 삭제
                    case "delete":


                        break;

                    case "deal_review_leave":
//                        Toast.makeText(context, "deal_review_leave", Toast.LENGTH_SHORT).show();
                        break;

                }


                productHistoryDialog.dismiss();




            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView dialog_title;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            dialog_title=itemView.findViewById(R.id.item_product_history_dialog_title);


        }
    }




    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

       switch (msg.what){

           case 0:
               break;
           case 1:
               break;
           case 2:
               break;
           case 3:
               break;
       }


            return false;
        }
    });
}
