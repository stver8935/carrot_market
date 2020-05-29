package com.example.carrot_market.CONTROLLER;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.carrot_market.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class CreatePostDialog extends BottomSheetDialog {
    private ConstraintLayout promotion_btn,deal_btn;
    private View.OnClickListener deal_btn_listener;


    public CreatePostDialog(@NonNull Context context,View.OnClickListener deal_btn_listener) {
        super(context);
        this.deal_btn_listener=deal_btn_listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_post_dialog);

        deal_btn=findViewById(R.id.create_post_deal_btn);
        deal_btn.setOnClickListener(deal_btn_listener);

    }

}
