package com.example.carrot_market.MODEL.HttpConnect;

import android.content.Context;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class TESTTASK implements Runnable {

    private Handler handler;
    private String key = "6016f076ca3197ff11576743133242";

    //사용자가 입력한  주소
    private String putAddress="업성동";
    //우체국으로부터 반환 받은 우편주소 리스트
    private ArrayList<String> addressSearchResultArr = new ArrayList<>();
    ArrayList<String> arrayList=new ArrayList<>();
    JSONArray jsonArray=new JSONArray();


    int page_count;
    Context context;
    Geocoder geocoder;

    public TESTTASK(Handler handler,Context context,String keyword,int page_count) {
        this.putAddress=keyword;
        this.handler = handler;
        this.page_count=page_count;
        this.context=context;
        geocoder=new Geocoder(context);
    }

    @Override
    public void run() {


        final String apiurl="http://biz.epost.go.kr/KpostPortal/openapi2";
        HttpURLConnection conn = null;
        try {
            StringBuffer sb = new StringBuffer(3);
            sb.append(apiurl);
            sb.append("?regkey=" + key + "&target=postNew&query=");
            sb.append(URLEncoder.encode(putAddress, "UTF-8"));
            sb.append("&countPerPage=15&currentPage="+page_count);

            String query = sb.toString();
            URL url = new URL(query);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("accept-language", "ko");
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            byte[] bytes = new byte[4096];
            InputStream in = conn.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while (true) {
                int red = in.read(bytes);
                if (red < 0) break;
                baos.write(bytes, 0, red);
            }
            String xmlData = baos.toString("UTF-8");
            baos.close();
            in.close();
            conn.disconnect();
            Document doc = docBuilder.parse(new InputSource(new StringReader(xmlData)));
            Element el = (Element) doc.getElementsByTagName("itemlist").item(0);

            for (int i = 0; i < ((Node) el).getChildNodes().getLength(); i++) {
                Node node = ((Node) el).getChildNodes().item(i);
                if (!node.getNodeName().equals("item")) {
                    continue;
                }

                String post = node.getChildNodes().item(3).getFirstChild().getNodeValue();
                Log.w("jaeha", "address = " + post);



                JSONObject jsonObject=new JSONObject();
                jsonObject.put("address",post);

                jsonArray.put(jsonObject);

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.disconnect();
            } catch (Exception e) {
            }


                Message msg = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("address", "" + jsonArray);
                msg.setData(bundle);
                msg.what = 0;
                handler.sendMessage(msg);


        }


    }


}

