package com.example.carrot_market.CONTROLLER.Dialog;


import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.carrot_market.MODEL.DTO.ProductHistoryDialogItem;
import com.example.carrot_market.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class PurchaseHistoryDialog extends BottomSheetDialog implements View.OnClickListener {


    private ProductHistoryDialogItem select_dialog_item;

    private Context context;
    private TextView product_history_title,grow_up,modify,hidden,delete,deal_review_leave,ing;

    private String dialog_type;
    private String product_key;
    private TextView dialog_purchase_history_deal_review_leave,dialog_purchase_history_delete;



    public PurchaseHistoryDialog(@NonNull Context context,String product_key) {
        super(context);
        this.context=context;
        this.product_key=product_key;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_purchase_history);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT /*our width*/, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog_purchase_history_deal_review_leave=findViewById(R.id.dialog_purchase_history_deal_review_leave);
        dialog_purchase_history_delete=findViewById(R.id.dialog_purchase_history_delete);
        dialog_purchase_history_deal_review_leave.setOnClickListener(this);
        dialog_purchase_history_delete.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {
        purchase_history_work work= (PurchaseHistoryDialog.purchase_history_work) context;

      switch (v.getId()){
        case R.id.dialog_purchase_history_deal_review_leave:
            Toast.makeText(context, "leave", Toast.LENGTH_SHORT).show();
            work.purchase_history_works(product_key,"deal_review_leave");
            dismiss();
            break;
        case R.id.dialog_purchase_history_delete:
            work.purchase_history_works(product_key,"delete");
         dismiss();
            break;
        }

    }

    public interface purchase_history_work{
        void purchase_history_works(String product_key,String work_type);
    }

}
