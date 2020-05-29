package com.example.carrot_market.CONTROLLER.METHOD;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendFcmMessage implements Runnable {
    private String title;
    private JSONObject fcm_body;

    public SendFcmMessage(String title,JSONObject fcm_body) {
        this.title=title;
        this.fcm_body=fcm_body;
        //fcm_body 구조 product_key, id,message,message_type
    }

    @Override
    public void run() {
        try {
            // FMC 메시지 생성 start
            JSONObject root = new JSONObject();
            JSONObject notification = new JSONObject();
            notification.put("body", fcm_body.toString());
            notification.put("title", title);
            root.put("notification", notification);
            root.put("to", "cXUDMdjXH4w:APA91bGYxjpYl8KZi6CSIO4Wvzcak2Lb0sWqrIVNJUmEFVB-UNshA3sqyzMhjRSZRKfQkjVpQqQrOONsauWHhQ-6lCpT4Y4uvqXkvahfTipiYPXuvRmq3dMwAHqRdoCV1eCMj1hr1AjU");
            // FMC 메시지 생성 end

            URL Url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection conn = (HttpURLConnection) Url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.addRequestProperty("Authorization", "key=AAAAdL1kXlE:APA91bEmodC9TvbDhP2iNWTuqUQjADqOUK5HBJsLt48duWZbllVdE2_erixBamiUkgdbPuvW_orRqQrnomXWki5wfoXT5-ztHI83aVTKwdT-Y8HYIlILTfDDMIWYStxjggFmt7DZnWrO");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-type", "application/json");
            OutputStream os = conn.getOutputStream();
            os.write(root.toString().getBytes("utf-8"));
            os.flush();
            conn.getResponseCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
