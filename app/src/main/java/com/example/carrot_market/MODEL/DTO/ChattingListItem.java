package com.example.carrot_market.MODEL.DTO;

import org.json.JSONException;
import org.json.JSONObject;

public class ChattingListItem {
    String profile_id;
    String location;
    String time;
    String count;
    String contents;
    String product_image_path;
    String profile_image_path;


    String chatting_room_key;
    String product_key;


    public String getChatting_room_key() {
        return chatting_room_key;
    }

    public void setChatting_room_key(String chatting_room_key) {
        this.chatting_room_key = chatting_room_key;
    }

    public String getProduct_key() {
        return product_key;
    }

    public void setProduct_key(String product_key) {
        this.product_key = product_key;
    }



    public String getAppointment_time() {
        return appointment_time;
    }

    public void setAppointment_time(String appointment_time) {
        this.appointment_time = appointment_time;
    }

    String appointment_time;


    public String getProduct_image_path() {
        return product_image_path;
    }

    public void setProduct_image_path(String product_image_path) {
        this.product_image_path = product_image_path;
    }

    public String getProfile_image_path() {
        return profile_image_path;
    }

    public void setProfile_image_path(String profile_image_path) {
        this.profile_image_path = profile_image_path;
    }

    public String getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(String profile_id) {
        this.profile_id = profile_id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }


    public JSONObject return_json(){
        JSONObject chatting_room_info_json=new JSONObject();
        try {
            chatting_room_info_json.put("chatting_room_key",chatting_room_key);

        chatting_room_info_json.put("appointment_time",appointment_time);
        chatting_room_info_json.put("product_key",product_key);
        chatting_room_info_json.put("chatting_list_id",profile_id);
        chatting_room_info_json.put("profile_image",profile_image_path);
        chatting_room_info_json.put("text",contents);
        chatting_room_info_json.put("time_stamp",time);
        chatting_room_info_json.put("image_path",product_image_path);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //위치
        //        chatting_room_info_json.put();


        return chatting_room_info_json;
    }


}
