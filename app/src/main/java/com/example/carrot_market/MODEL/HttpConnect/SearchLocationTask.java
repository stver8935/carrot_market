package com.example.carrot_market.MODEL.HttpConnect;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.carrot_market.MODEL.DTO.SearchMyLocationItem;

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

public class SearchLocationTask extends AsyncTask<String, String, ArrayList<String>> implements Runnable {

        private String key="5416f0d72e3fb7ff01576625140319";
        private String putAddress="asd";

        private ArrayList<SearchMyLocationItem> arrayList=new ArrayList<>();
        private ArrayList<String> addressSearchResultArr=new ArrayList<>();



    @Override
        protected ArrayList<String> doInBackground(String... param) {
            ArrayList<String> response = null;
            int page= Integer.parseInt(param[1]);
            final String apiurl = "http://biz.epost.go.kr/KpostPortal/openapi";
            ArrayList<String> addressInfo = new ArrayList<>();


            HttpURLConnection conn = null;

            try {
                StringBuffer sb = new StringBuffer(3);
                sb.append(apiurl);
                sb.append("?regkey=" + key + "&target=postNew&query=");
                sb.append(URLEncoder.encode(param[0], "EUC-KR"));
                sb.append("&countPerPage=10&currentPage="+(page/10)+1);

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
                String xmlData = baos.toString("utf-8");
                baos.close();
                in.close();
                conn.disconnect();
                Document doc = docBuilder.parse(new InputSource(new StringReader(xmlData)));
                Log.e("XMLDATA",""+xmlData);
                Element el = (Element) doc.getElementsByTagName("itemlist").item(0);

                for (int i = 0; i < ((Node) el).getChildNodes().getLength(); i++) {
                    Node node = ((Node) el).getChildNodes().item(i);
                    if (!node.getNodeName().equals("item")) {
                        continue;
                    }
                    String address = node.getChildNodes().item(1).getFirstChild().getNodeValue();
                    String post = node.getChildNodes().item(3).getFirstChild().getNodeValue();
                    Log.w("jaeha", "address = " + address);
                    addressInfo.add(post.substring(0, 3) + "-" + post.substring(3));
                }
                addressSearchResultArr = addressInfo;
                return addressSearchResultArr;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (conn != null) conn.disconnect();
                } catch (Exception e) {
                }
            }
            return response;
        }


    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);


        arrayList.clear();


        ArrayList<String> searchlist=new ArrayList<>();

        for (int a=0;a<addressSearchResultArr.size();a++) {

            String index[]= addressSearchResultArr.get(a).split("[\\(\\)]");
            String aaaa[]=index[1].split(",");
            searchlist.add(aaaa[0]);

            Log.e("인텍스",aaaa[0]);
        }


        ArrayList<String> del=new ArrayList<>();
        for (int a=0;a<searchlist.size();a++) {
            if (!del.contains(searchlist.get(a))) {
                SearchMyLocationItem item = new SearchMyLocationItem();
                del.add(searchlist.get(a));
                item.setAddress(searchlist.get(a));
                arrayList.add(item);

            }
        }
    }

    @Override
    public void run() {
        Handler handler=new Handler();

        Message msg=handler.obtainMessage();
        Bundle bundle=new Bundle();
        bundle.putString("hello","hello");
        msg.setData(bundle);
        handler.sendMessage(msg);
    }


}

