package com.example.carrot_market;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class ChatServerHandler extends ChannelInboundHandlerAdapter {


    //채팅서버에서  데이터 처리를 담당하는 부분입니다.
    //논 블로킹 방식으로 구현 했습니다

    private static final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private Retrofit retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
    private ServerRetrofitService retrofitService=retrofit.create(ServerRetrofitService.class);


    //상대에게 메시지를 보낼때 추가될 내 정보
    String id,profile_image,address,location;
    //나에게 메시지를 보낼때 추가될 상대정보
    String opponent,opponent_profile_image,opponent_address,opponent_location;
    //메시지 전송시 필요한 기본 데이터
    String product_key,chatting_message,message_type,chatting_room_key,time_stamp,product_image;


    JsonParser jsonParser=new JsonParser();

    //접근하는 채널의 변수
    Channel incoming;
    //메시지를 담을 변수

    String message;





    //사용자가 추가되었을때 호출되는 함수
    @Override
    public void handlerAdded(ChannelHandlerContext ctx){
        Channel incoming = ctx.channel();
        channelGroup.add(incoming);
        System.out.println("handlerAdded of [SERVER]"+incoming.id());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // 사용자가 접속했을 때 서버에 표시.

        Channel coming_user_chanel=ctx.channel();
        ctx.channel().read();


        System.out.println("User Access! ip"+coming_user_chanel.remoteAddress()+"\nchanel+id"+coming_user_chanel.id());
        System.out.println("Access meta"+coming_user_chanel.metadata());

    }





    //사용자가 나갔을때 호출되는 함수
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        System.out.println("handlerRemoved of [SERVER]");
        Channel incoming = ctx.channel();
        channelGroup.remove(incoming);
    }


    //데이터 수신이 완료 되었을떄 호출 되는 함수
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }


    //사용자가 서버에 데이터를 전송했을때 데이터를 받아들이는 함수
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        incoming = ctx.channel();
        message = (String) msg;

        //json 형태의 메시지
        final JsonElement jsonObject = jsonParser.parse(message);
        System.out.println("json"+jsonObject);
        message_type=jsonObject.getAsJsonObject().get("message_type").getAsString();




        //message_type
        //0 예약
        //1 일반 메시지
        //4 나가기 메시지
        System.out.println("user_added");

        if (message_type.equals("5")){

            retrofitService.chatting_channel_update(jsonObject.getAsJsonObject().get("id").getAsString(),ctx.channel().id().asShortText()).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });


        }

        //약속시간 설정 메시지
        else if (message_type.equals("0")){
            id=jsonObject.getAsJsonObject().get("id").getAsString();
            opponent=jsonObject.getAsJsonObject().get("opponent").getAsString();
            chatting_message=jsonObject.getAsJsonObject().get("message").getAsJsonObject().toString();
            product_key=jsonObject.getAsJsonObject().get("product_key").getAsString();

            retrofitService.chatting_channel_update(id,ctx.channel().id().asShortText()).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    upload_message();
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });

        }
        else {
            id=jsonObject.getAsJsonObject().get("id").getAsString();
            opponent=jsonObject.getAsJsonObject().get("opponent").getAsString();
            chatting_message=jsonObject.getAsJsonObject().get("message").getAsString();
            product_key=jsonObject.getAsJsonObject().get("product_key").getAsString();

            retrofitService.chatting_channel_update(id,ctx.channel().id().asShortText()).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    upload_message();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });


        }

        }










        //메시지 받는 핸들러 서버에 메시지 저장 되었다면 수신자한테 메시지 전달
public void upload_message(){



    System.out.println("messagesssssssssss------"+id+"//"+chatting_message+"//"+opponent+"//"+product_key);



    //채팅을 업로드 하고 메시지 전송에 필요한 데이터 받는 부분분
   retrofitService.chatting_upload(id,opponent,chatting_message,product_key,message_type).enqueue(new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            try {

                JsonElement jsonObject1=jsonParser.parse(response.body().string());

                //??로그찍기
                    System.out.println("return_message_json"+jsonObject1);
                product_image=jsonObject1.getAsJsonObject().get("product_image_path").getAsString();
                time_stamp=jsonObject1.getAsJsonObject().get("time_stamp").getAsString();
                chatting_room_key=jsonObject1.getAsJsonObject().get("chatting_room_key").getAsString();
                //상대에게 보낼 메시지


                if (!jsonObject1.getAsJsonObject().get("profile_image").isJsonNull()) {
                    profile_image = jsonObject1.getAsJsonObject().get("profile_image").getAsString();
                }
                address=jsonObject1.getAsJsonObject().get("address").getAsString();
                location=jsonObject1.getAsJsonObject().get("location").getAsString();

                //나에게 보낼 메시지
                if (!jsonObject1.getAsJsonObject().get("opponent_profile_image").isJsonNull()) {
                    opponent_profile_image = jsonObject1.getAsJsonObject().get("opponent_profile_image").getAsString();
                }

                opponent_address=jsonObject1.getAsJsonObject().get("opponent_address").getAsString();
                opponent_location=jsonObject1.getAsJsonObject().get("opponent_location").getAsString();

            }catch (IOException e) {
                e.printStackTrace();
            }


            JsonElement jsonObject_message = jsonParser.parse(message);

            //아래 4개 정보는 기본적으로 보내는 정보
            jsonObject_message.getAsJsonObject().addProperty("chat_room_key",chatting_room_key);
            jsonObject_message.getAsJsonObject().addProperty("time_stamp",time_stamp);
            jsonObject_message.getAsJsonObject().addProperty("message_type",message_type);
            jsonObject_message.getAsJsonObject().addProperty("product_image",product_image);
            jsonObject_message.getAsJsonObject().addProperty("profile_image",profile_image);
            jsonObject_message.getAsJsonObject().addProperty("opponent_profile_image",opponent_profile_image);



            System.out.println("jsonmessage_before"+jsonObject_message);
            //유저 타입
            // 0 메시지를 보낸사람
            //2 상대편
            if (!message_type.equals("4")){
                send_message("0",id,jsonObject_message.getAsJsonObject());
                send_message("1",opponent,jsonObject_message.getAsJsonObject());
            }else {
                send_message("0",id,jsonObject_message.getAsJsonObject());
                send_message("1",opponent,jsonObject_message.getAsJsonObject());
                //나가메시지 일 경우에 상대편한테만 메시지 전송
                    retrofitService.exit_chatting_room(id,chatting_room_key).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                System.out.println("ok"+response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            System.out.println("no");
                        }
                    });
            }

        }
        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {


        }
    });

}




public void send_message(String user_type,String id, final JsonObject default_message_Json){


        //유저 타입 0 나 자신
        //1 상대편

    //유저 타입이 나일때
        if (user_type.equals("0")) {
            retrofitService.chatting_channel_load(id).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String channl_id = response.body().string();

                        for (Channel channel : channelGroup) {
                            if (channel.id().asShortText().equals(channl_id)) {
                                default_message_Json.getAsJsonObject().addProperty("profile_image", opponent_profile_image);
                                default_message_Json.getAsJsonObject().addProperty("address", opponent_address);
                                default_message_Json.getAsJsonObject().addProperty("location", opponent_location);

                                default_message_Json.getAsJsonObject().addProperty("opponent_profile_image", profile_image);
                                default_message_Json.getAsJsonObject().addProperty("opponent_address", address);
                                default_message_Json.getAsJsonObject().addProperty("opponent_location", location);
                                channelGroup.find(channel.id()).writeAndFlush(default_message_Json.getAsJsonObject() + "\n");
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });}
        else if (user_type.equals("1")){
            //유저 타입이 상대편일때
            retrofitService.chatting_channel_load(opponent).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String opponent_channl_id = response.body().string();
                        for (Channel channel : channelGroup) {
                            if (channel.id().asShortText().equals(opponent_channl_id)) {

                                default_message_Json.getAsJsonObject().addProperty("profile_image", profile_image);
                                default_message_Json.getAsJsonObject().addProperty("address", address);
                                default_message_Json.getAsJsonObject().addProperty("location", location);


                                default_message_Json.getAsJsonObject().addProperty("opponent_profile_image", opponent_profile_image);
                                default_message_Json.getAsJsonObject().addProperty("opponent_address", opponent_address);
                                default_message_Json.getAsJsonObject().addProperty("opponent_location", opponent_location);

                                channelGroup.find(channel.id()).writeAndFlush(default_message_Json.getAsJsonObject() + "\n");


                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }
}
}

