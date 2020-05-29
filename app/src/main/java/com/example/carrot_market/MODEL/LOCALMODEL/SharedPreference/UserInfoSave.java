package com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.carrot_market.MODEL.DTO.AddProductImageItem;
import com.example.carrot_market.MODEL.DTO.UserInfoItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserInfoSave {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;


    public UserInfoSave(Context context) {
        this.pref = context.getSharedPreferences("User_Info", Context.MODE_PRIVATE);
        this.editor=pref.edit();
        this.context=context;


    }

    private JSONObject userinfoitem_to_jsonobject_convert(UserInfoItem item){

        JSONObject jsonObject=new JSONObject();

        try {
        jsonObject.put("auto_login_check",item.isAuto_login_check());
        jsonObject.put("id",item.getId());
        jsonObject.put("password",item.getPassword());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    private UserInfoItem jsonobject_to_userinfoitem_convert(JSONObject jsonObject){
    UserInfoItem infoItem=new UserInfoItem();
        try {
            infoItem.setId(jsonObject.getString("id"));
            infoItem.setPassword(jsonObject.getString("password"));
            infoItem.setAuto_login_check(jsonObject.getBoolean("auto_login_check"));

        } catch (JSONException e) {
                    e.printStackTrace();
        }
        return infoItem;
    }

    public void insert_account(UserInfoItem infoItem) throws JSONException {
        JSONObject object=new JSONObject();
        object.put("auto_login_check",infoItem.isAuto_login_check());
        object.put("id",infoItem.getId());
        object.put("password",infoItem.getPassword());
        editor.putString("account",object.toString());
        Log.e("로컬로그인","에러");
        editor.apply();
        editor.commit();

    }



        public boolean check() throws JSONException {

        boolean auto_login_true_n_false=false;

        if (pref.getString("account","").equals("")){
            return auto_login_true_n_false;

        }else{
            JSONObject jsonObject=new JSONObject(pref.getString("account",""));
            auto_login_true_n_false= jsonObject.getBoolean("auto_login_check");
        }

       return auto_login_true_n_false;
    }

    public UserInfoItem return_account(){
        UserInfoItem infoItem = null;
        try {
            UserInfoSaveSingleton userInfoSaveSingleton=UserInfoSaveSingleton.return_userinfo_sigleton();

            JSONObject jsonObject=new JSONObject(pref.getString("account",""));
            infoItem=jsonobject_to_userinfoitem_convert(jsonObject);

            if (!infoItem.getId().equals("")){
                userInfoSaveSingleton.set_login_check(true);
            }else {
                userInfoSaveSingleton.set_login_check(false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return infoItem;
    }

    public void add_product_save(JSONObject product_text, ArrayList<AddProductImageItem> product_image){
        JSONArray jsonArray_image=new JSONArray();
        editor.putString("writing_product_text",product_text.toString());

        for (int i=0;i<product_image.size();i++){
        jsonArray_image.put(product_image.get(i).getImage());
        }
        editor.putString("writing_product_image",jsonArray_image.toString());
        editor.commit();

    }



}
