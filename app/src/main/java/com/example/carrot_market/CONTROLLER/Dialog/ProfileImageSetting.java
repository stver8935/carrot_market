package com.example.carrot_market.CONTROLLER.Dialog;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.carrot_market.CONTROLLER.MyProfileSetting;
import com.example.carrot_market.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;

import static android.widget.ListPopupWindow.MATCH_PARENT;
import static android.widget.ListPopupWindow.WRAP_CONTENT;

public class ProfileImageSetting extends BottomSheetDialog {

    private Context context;
    private TextView profile_setting,profile_delete;


    public ProfileImageSetting(@NonNull Context context)
    {
        super(context);
        this.context=context;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_profile_image_setting);
        getWindow().setLayout(MATCH_PARENT,WRAP_CONTENT);



        profile_setting=findViewById(R.id.profile_image_setting);
        profile_delete=findViewById(R.id.profile_image_delete);



        profile_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FishBun.with((Activity) context).setImageAdapter(new GlideAdapter()).setCamera(true).setMaxCount(1).startAlbum();
                dismiss();
            }
        });

        profile_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MyProfileSetting)context).profile_image.setImageDrawable(context.getResources().getDrawable(R.drawable.profile_image_man));
                ((MyProfileSetting)context).profile_image_uri= Uri.parse("null");
                dismiss();
            }
        });

    }
}
