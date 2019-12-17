package com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.carrot_market.MODEL.HttpConnect.RETROFIT.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

public class AutoLoginCheck {
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public AutoLoginCheck(Context context) {

        this.pref = context.getSharedPreferences("auto_login", Context.MODE_PRIVATE);
        this.editor=pref.edit();

    }

    public void insert_auto_login(Boolean bool,String id, String password) throws JSONException {
        JSONObject object=new JSONObject();

        object.put("auto_login_check",bool);
        object.put("id",id);
        object.put("password",password);
        editor.putString("auto_login",object.toString());
        editor.apply();
        editor.commit();

        Log.e("asdasd","asdasd23123");
    }

    public void insert_auto_login(Boolean bool){

        editor.putBoolean("auto_login",bool);
        editor.commit();
    }

    public boolean check() throws JSONException {
        boolean auto_login_true_n_false;

        JSONObject jsonObject=new JSONObject(pref.getString("auto_login",""));

       auto_login_true_n_false= jsonObject.getBoolean("auto_login_check");

    return auto_login_true_n_false;



    }


    public UserInfo return_account(){
        String id,password;

        try {
            if (check()) {

                JSONObject object=new JSONObject(pref.getString("auto_login",""));

                id=object.getString("id");
                password=object.getString("password");


                UserInfo item = new UserInfo();
                item.setId(id);
                item.setPassword(password);

            return item;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
