package com.example.carrot_market.CONTROLLER.METHOD;

import android.util.Log;

import com.example.carrot_market.MODEL.DTO.AlramTimeItem;

public class AlramTimeCallback {

    public interface callback {
        void onCallbackListener(AlramTimeItem item);
    }
    private callback callback;

    public void setCallback(callback callback){
        this.callback=callback;
    }

    public void settext(AlramTimeItem item){

        Log.e("settext","item"+item);
        callback.onCallbackListener(item);

    }
}




