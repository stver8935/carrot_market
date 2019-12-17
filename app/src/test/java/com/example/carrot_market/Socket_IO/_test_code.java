package com.example.carrot_market.Socket_IO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public final class _test_code {


    //본 소스는 스트림 형태의 TCP IP 통신을 말함
    //자바 IO


    private ServerSocket serverSocket;

    //들어오는 정보를 저장할 소켓
    //클라이언트 소켓
    private Socket socket;

    //데이터 스트림 버퍼
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;


    //생성과 바인드드 서버 소켓을 생성하 과정 생성 -- 바인딩 --리슨 --- 어셉트
    public void serversetting(){
        try {
            //
            serverSocket=new ServerSocket(8083);
            //
            socket=serverSocket.accept();
            //소켓이 접속 완료된 부분 .
            System.out.println("클라이언트 소켓 연결");

            //클라이언트 소켓으로부터 데이터스트림을 받고 보내는 스트림 지정하는 부분


            String rev=dataInputStream.readUTF();

            System.out.println(rev);


            dataOutputStream.writeUTF("잘받았어요");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

public void streamsetting(){{
    //받은 데이터
    try {
        dataInputStream=new DataInputStream(socket.getInputStream());

    //보내는 데이터
    dataOutputStream=new DataOutputStream(socket.getOutputStream());
    } catch (IOException e) {
        e.printStackTrace();
    }

    }
}


//데이터를 받는 부분
public String dataRev(){

    try {
        return dataInputStream.readUTF();
    } catch (IOException e) {
        e.printStackTrace();
    }
return null;
    }

//데이터를 보내는 스트림
    public void sendData(String senddata){
        try {
            dataOutputStream.writeUTF(senddata);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//할당된 자원들을 반납 해주는 메서드
    //소켓이나 스트림 반납
    public void closeAll(){
        try {

        serverSocket.close();
        socket.close();
        dataOutputStream.close();
        dataInputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



public  _test_code(){
        serversetting();
        dataRev();
        sendData("잘받았어요");
        closeAll();
}


public static void main(String[] args){
        new _test_code();
}

}
