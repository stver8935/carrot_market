package com.example.carrot_market.Socket_IO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class clientt {
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;


    public void connect(){
        //
        try {
            socket=new Socket("192.168.56.1",8083);
            System.out.println("접속완료료");

       } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void streamsetting(){
        try {
            dataInputStream=new DataInputStream(socket.getInputStream());
        dataOutputStream=new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String dataRev(){
        try {
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void sendData(String send){
        try {
            dataOutputStream.writeUTF(send);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public  clientt(){
        connect();
        streamsetting();
        sendData("안녕 들리니?");
        System.out.println(dataRev());
    }

    public static void main(String[] args){
        new clientt();

    }


}
