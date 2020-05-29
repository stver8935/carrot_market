package com.example.carrot_market.MODEL.DTO;

public class ChattingItem {
    String contents;
    String content_time;
    String contents_image_time;
    String id;
    String other_profile_image_path;
    String Chatting_room_key;
    String message_type;

    String oppointment_time;;



    public String getOppointment_time() {
        return oppointment_time;
    }

    public void setOppointment_time(String oppointment_time) {
        this.oppointment_time = oppointment_time;
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }


    public String getChatting_room_key() {
        return Chatting_room_key;
    }

    public void setChatting_room_key(String chatting_room_key) {
        Chatting_room_key = chatting_room_key;
    }


    public String getContent_time() {
        return content_time;
    }

    public void setContent_time(String content_tim) {
        this.content_time = content_tim;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOther_profile_image_path() {
        return other_profile_image_path;
    }

    public void setOther_profile_image_path(String other_profile_image_path) {
        this.other_profile_image_path = other_profile_image_path;
    }


    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getContents_image_time() {
        return contents_image_time;
    }

    public void setContents_image_time(String contents_image_time) {
        this.contents_image_time = contents_image_time;
    }



}
