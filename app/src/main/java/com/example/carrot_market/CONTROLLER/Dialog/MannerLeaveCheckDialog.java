package com.example.carrot_market.CONTROLLER.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.carrot_market.CONTROLLER.MannerLeave;
import com.example.carrot_market.R;

public class MannerLeaveCheckDialog extends Dialog {
    private TextView good_manner_button,bad_manner_button;
    private String opponent_id;
    public MannerLeaveCheckDialog(@NonNull Context context,String opponent_id) {
        super(context);
        this.opponent_id=opponent_id;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.dialog_manner_leave_check);
        good_manner_button=findViewById(R.id.dialog_manner_leave_check_good);
        bad_manner_button=findViewById(R.id.dialog_manner_leave_check_bad);


        good_manner_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), MannerLeave.class);
                intent.putExtra("profile_id",opponent_id);
                intent.putExtra("manner_type","1");
                getContext().startActivity(intent);
                dismiss();
            }
        });

        bad_manner_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(), MannerLeave.class);
                intent.putExtra("profile_id",opponent_id);
                intent.putExtra("manner_type","0");
                getContext().startActivity(intent);
                dismiss();
            }
        });


    }
}
