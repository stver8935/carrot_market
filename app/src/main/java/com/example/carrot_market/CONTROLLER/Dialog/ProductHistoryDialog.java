package com.example.carrot_market.CONTROLLER.Dialog;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.carrot_market.MODEL.DTO.ProductHistoryDialogItem;
import com.example.carrot_market.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class ProductHistoryDialog extends BottomSheetDialog implements View.OnClickListener {


    private ProductHistoryDialogItem select_dialog_item;

    private Context context;
    private TextView product_history_title,grow_up,modify,hidden,delete,deal_review_leave,ing;

    private String dialog_type;
    private String product_key;



    public ProductHistoryDialog(@NonNull Context context,String dialog_type,String product_key) {
        super(context);
        this.context=context;
        this.dialog_type=dialog_type;
        this.product_key=product_key;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_product_history);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT /*our width*/, ViewGroup.LayoutParams.WRAP_CONTENT);

        product_history_title=findViewById(R.id.dialog_product_history_title);
        grow_up=findViewById(R.id.dialog_product_history_growup);
        modify=findViewById(R.id.dialog_product_history_modify);
        hidden=findViewById(R.id.dialog_product_history_hidden);
        delete=findViewById(R.id.dialog_product_history_delete);
        deal_review_leave=findViewById(R.id.dialog_product_history_deal_review_leave);
        ing=findViewById(R.id.dialog_product_history_ing);

        grow_up.setOnClickListener(this);
        modify.setOnClickListener(this);
        hidden.setOnClickListener(this);
        delete.setOnClickListener(this);
        deal_review_leave.setOnClickListener(this);
        ing.setOnClickListener(this);

        Log.e("type",dialog_type);

        switch (dialog_type){
            case "ing":
                ing.setVisibility(View.GONE);
                grow_up.setVisibility(View.VISIBLE);
                deal_review_leave.setVisibility(View.GONE);
                modify.setVisibility(View.VISIBLE);
                break;
            case "commit":
                grow_up.setVisibility(View.GONE);
                modify.setVisibility(View.GONE);
                deal_review_leave.setVisibility(View.GONE);
                ing.setVisibility(View.VISIBLE);
                hidden.setVisibility(View.VISIBLE);
                break;
            case "hidden":
                ing.setVisibility(View.GONE);
                grow_up.setVisibility(View.GONE);
                deal_review_leave.setVisibility(View.VISIBLE);
                hidden.setVisibility(View.GONE);
                break;
        }




    }

    @Override
    public void onClick(View v) {
        changetext changetext= (ProductHistoryDialog.changetext) context;
        switch (v.getId()){

            //끌어올리기
            case R.id.dialog_product_history_growup:
                changetext.changetext(dialog_type,product_key,"pull_up");
                break;

                //수정
            case R.id.dialog_product_history_modify:
                changetext.changetext(dialog_type,product_key,"modify");
                break;

                //숨기기
            case R.id.dialog_product_history_hidden:
                changetext.changetext(dialog_type,product_key,"hidden");
                break;

                //삭제
            case R.id.dialog_product_history_delete:
                changetext.changetext(dialog_type,product_key,"delete");
                break;

                //리뷰 남기기
            case R.id.dialog_product_history_deal_review_leave:
                changetext.changetext(dialog_type,product_key,"deal_review_leave");
                break;

                //거래중으로 변환하기
            case R.id.dialog_product_history_ing:
                changetext.changetext(dialog_type,product_key,"ing");
                break;

        }
        dismiss();
    }
    public interface changetext{
        void changetext(String fragment_type,String product_key,String work_type);
    }
}
