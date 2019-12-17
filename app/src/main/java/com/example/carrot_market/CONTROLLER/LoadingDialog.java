package com.example.carrot_market.CONTROLLER;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.carrot_market.R;

public class LoadingDialog extends Dialog {
    private TextView title;
    private ProgressBar loading_progress;
    private String title_text;

    public LoadingDialog(@NonNull Context context,String title) {
        super(context);
        this.title_text=title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);


        title=findViewById(R.id.loading_text);
        loading_progress=findViewById(R.id.loading_progress);
        title.setText(title_text);

    }
}
