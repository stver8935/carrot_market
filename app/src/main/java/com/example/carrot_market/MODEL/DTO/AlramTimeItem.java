package com.example.carrot_market.MODEL.DTO;

import org.json.JSONException;
import org.json.JSONObject;

public class AlramTimeItem {
    String time_text;

    int minite;



    public String getTime_text() {
        return time_text;
    }

    public void setTime_text(String time_text) {
        this.time_text = time_text;
    }

    public int getMinite() {
        return minite;
    }

    public void setMinite(int minite) {
        this.minite = minite;
    }

    public JSONObject return_to_json() throws JSONException {
        JSONObject alram_time_json=new JSONObject();

        alram_time_json.put("minite",minite);
        alram_time_json.put("time_text",time_text);

        return alram_time_json;
    }


}
