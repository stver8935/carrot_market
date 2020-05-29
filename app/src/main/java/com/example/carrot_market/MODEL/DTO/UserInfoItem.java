package com.example.carrot_market.MODEL.DTO;

public class UserInfoItem {
    String id;
    String password;

    public UserInfoItem() {
        this.auto_login_check=false;
    }

    public boolean isAuto_login_check() {
        return auto_login_check;
    }

    public void setAuto_login_check(boolean auto_login_check) {
        this.auto_login_check = auto_login_check;
    }

    boolean auto_login_check;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
