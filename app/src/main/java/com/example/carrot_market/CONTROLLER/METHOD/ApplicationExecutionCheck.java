package com.example.carrot_market.CONTROLLER.METHOD;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

public class ApplicationExecutionCheck {
    private boolean isAppRunning(Context context){
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
        for(int i = 0; i < procInfos.size(); i++){
            if(procInfos.get(i).processName.equals(context.getPackageName())){
                return true;
            }
        }

        return false;
    }
}
