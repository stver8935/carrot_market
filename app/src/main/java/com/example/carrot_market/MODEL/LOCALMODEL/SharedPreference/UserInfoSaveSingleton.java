package com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference;

public class UserInfoSaveSingleton {
    private boolean login_check_boolean=false;

    private static UserInfoSaveSingleton userInfoSaveSingleton=new UserInfoSaveSingleton();

    public static synchronized UserInfoSaveSingleton return_userinfo_sigleton(){
        return userInfoSaveSingleton;
    }

    public boolean get_login_check(){
       return login_check_boolean;
    }
    public void set_login_check(boolean login_check_boolean){
        this.login_check_boolean=login_check_boolean;
    }



}
