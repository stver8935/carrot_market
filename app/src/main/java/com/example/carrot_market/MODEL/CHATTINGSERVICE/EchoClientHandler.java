package com.example.carrot_market.MODEL.CHATTINGSERVICE;


import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;

import com.example.carrot_market.CONTROLLER.Chatting;
import com.example.carrot_market.CONTROLLER.ChattingList;
import com.example.carrot_market.CONTROLLER.METHOD.AlramNotification;
import com.example.carrot_market.CONTROLLER.METHOD.WorkManager;
import com.example.carrot_market.CONTROLLER.MainFragment;
import com.example.carrot_market.MODEL.DTO.ChattingItem;
import com.example.carrot_market.MODEL.DTO.ChattingListItem;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.Receiver.ChattingReceiver;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


public class EchoClientHandler extends ChannelInboundHandlerAdapter {
    JSONObject chatting_message_json;
    Context Chatting_context;
    Context MainFragment_context;
    UserInfoSave userInfoSave;

    @Override

    public void channelRead(final ChannelHandlerContext arg0, Object arg1) throws Exception {

        MainFragment_context=MainFragment.context;
        Chatting_context=  Chatting.context;
        userInfoSave=new UserInfoSave(MainFragment_context);

        //상대방한테온 메시지;
        System.out.println((String) arg1);
        //에러 날수도 있음
         chatting_message_json=new JSONObject(arg1.toString());


         //채팅룸 업데이트
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {




                ((MainFragment)MainFragment_context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("chatting_room_json",""+chatting_message_json);

                        String message_type;
                        try {
                            message_type=chatting_message_json.getString("message_type");

                            ChattingListItem chattingListItem=new ChattingListItem();
                            chattingListItem.setProduct_key(chatting_message_json.getString("product_key"));
                            chattingListItem.setChatting_room_key(chatting_message_json.getString("chat_room_key"));


                            if (chatting_message_json.getString("id").equals(userInfoSave.return_account().getId())){

                                chattingListItem.setProfile_id(chatting_message_json.getString("opponent"));

                            }else {
                                chattingListItem.setProfile_id(chatting_message_json.getString("id"));
                            }

                            chattingListItem.setProfile_image_path(chatting_message_json.getString("profile_image"));


                            chattingListItem.setLocation(chatting_message_json.getString("location"));
                            chattingListItem.setProduct_image_path(chatting_message_json.getString("product_image"));
                            chattingListItem.setTime(chatting_message_json.getString("time_stamp"));


                            //채팅 메시지 타입 체크 0 예약 메시지 1 일반 메시지 4 나가기 메시지
                            //3??
                            if (message_type.equals("0")) {
                                chattingListItem.setContents(chatting_message_json.getString("id") + "님이 거래 약속을 만들었어요.");


                                JSONObject alram_message_json=chatting_message_json;

                                AlramNotification alramNotification=new AlramNotification(MainFragment_context,alram_message_json.toString());
                                alramNotification.send_notification();



                            } else {
                                chattingListItem.setContents(chatting_message_json.getString("message"));
                            }


                            //들어온 메시지가 나가기 메시지라면
                            //내아이디와 비교해서 같다면 채팅방 제거
                            //같지 않다면 채팅방 유지
                            UserInfoSave userInfoSave=new UserInfoSave(MainFragment_context);

                            boolean empty_chatting_room_check=true;


                            //나가기 메시지이고 채팅방 나가기 메시지를 보낸사람과 내 아이디가 같다면
                             //채팅방 제거
                                if (message_type.equals("4")&&userInfoSave.return_account().getId().equals(chatting_message_json.getString("id"))){
                                for (int i=0;i<ChattingList.arrayList.size();i++){
                                    if (chattingListItem.getChatting_room_key().equals(ChattingList.arrayList.get(i).getChatting_room_key())){
                                        Log.e("chatting_room_remove","remove");
                                        ChattingList.arrayList.remove(i);
                                        ChattingList.adapter.notifyItemRemoved(i);
                                    break;
                                    }
                                }
                            }
                                else{
                                    //채팅방 나가기 메시지가 아니라면
                                    for (int i=0;i<ChattingList.arrayList.size();i++){
                                        //채팅룸키가 같다면
                                    if (ChattingList.arrayList.get(i).getChatting_room_key().equals(chattingListItem.getChatting_room_key())) {
                                        Log.e("chatting_room_structure", "" + ChattingList.arrayList.get(i).return_json());
                                        Log.e("chatting_room_update","update");

                                        ChattingList.arrayList.remove(i);
                                        ChattingList.adapter.notifyItemRemoved(i);

                                        ChattingList.arrayList.add(0, chattingListItem);
                                        ChattingList.adapter.notifyItemInserted(0);
                                        empty_chatting_room_check=false;
                                    break;
                                    }

                                    }
                                    //없는 채팅방이라면 true 있는 채팅방이라면 false
                                    if (empty_chatting_room_check){

                                        Log.e("chatting_room_insert","insert");
                                        ChattingList.arrayList.add(0,chattingListItem);
                                        ChattingList.adapter.notifyItemInserted(0);

                                    }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });



                if(((Chatting)Chatting_context)!=null) {
                    //채팅 업데이트
                    ((Chatting) Chatting_context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ChattingItem item = new ChattingItem();

                            try {

                                Log.e("message_info", "" + chatting_message_json + "+ and //" + ((Chatting) Chatting_context).chatting_room_key + "//" + ((Chatting) Chatting_context).chatting_room_key);

                                //채팅메시지가 현재 보고 있는 채팅 액티비티와 같을때 처리
                                //같지 않다면 알람 보여주기
                                if (!((Chatting) Chatting_context).chatting_room_key.isEmpty() && ((Chatting) Chatting_context).chatting_room_key.equals(chatting_message_json.getString("chat_room_key"))) {


                                    item.setChatting_room_key(chatting_message_json.getString("chat_room_key"));
                                    item.setMessage_type(chatting_message_json.getString("message_type"));
                                    item.setId(chatting_message_json.getString("id"));
                                    item.setContent_time(chatting_message_json.getString("time_stamp"));
                                    item.setOther_profile_image_path(chatting_message_json.getString("profile_image"));

                                    item.setContents(chatting_message_json.getString("message"));

                                    ((Chatting) Chatting_context).arrayList_c.add(item);
                                    ((Chatting) Chatting_context).adapter_c.notifyItemInserted(((Chatting) Chatting_context).arrayList_c.size());
                                    Chatting.recyclerView.scrollToPosition(((Chatting) Chatting_context).arrayList_c.size() - 1);


                                } else {

                                    //메시지 아이디와 내아이디가 같지 않다면 알람 메시지 보내기
                                    //애뮬레이터에서 동일 채팅룸키인데도 불구하고 작동함

                                    if (!new UserInfoSave(MainFragment_context).return_account().getId().equals(chatting_message_json.getString("id"))) {
                                        Intent intent = new Intent(MainFragment_context, ChattingReceiver.class);
                                        intent.putExtra("name", chatting_message_json.getString("id"));
                                        intent.putExtra("product_key", chatting_message_json.getString("product_key"));
                                        intent.putExtra("message", chatting_message_json.getString("message"));
                                        MainFragment_context.sendBroadcast(intent);
                                        Log.e("call_alram",chatting_message_json.getString("id"));

                                    }

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });

                }else{
                    try {
                        if (!new UserInfoSave(MainFragment_context).return_account().getId().equals(chatting_message_json.getString("id"))) {
                            Intent intent = new Intent(MainFragment_context, ChattingReceiver.class);
                            Log.e("call_alram",chatting_message_json.getString("id"));

                            intent.putExtra("name", chatting_message_json.getString("id"));
                            intent.putExtra("product_key", chatting_message_json.getString("product_key"));
                            intent.putExtra("message", chatting_message_json.getString("message"));
                            MainFragment_context.sendBroadcast(intent);
//                            Intent asd=new Intent(MainFragment_context,AlramReceiver.class);
//                            MainFragment_context.sendBroadcast(asd);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

        });
        thread.run();


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }




    public void reservation_alarm(JSONObject message_body) throws JSONException {

        //예약 알림을 위한 워크 매니저
        androidx.work.WorkManager workManager = androidx.work.WorkManager.getInstance();
        Data worker_manager_data = new Data.Builder()
                .putString("message", ""+message_body)
                .build();


        JSONObject alram_json_body=new JSONObject(message_body.getString("message")) ;

        try {

            String promise_date_string=alram_json_body.getString("date_format");
            int alram_time_min=alram_json_body.getInt("alram_time_min")*1000*60;

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long now = System.currentTimeMillis();

            //현재시간
            Date now_date = new Date(now);
            //약속된 시간
            Date promise_date = format.parse(promise_date_string);
            //알림받을 시간
            long date=promise_date.getTime()-now_date.getTime()-alram_time_min;
            long sub_date=promise_date.getTime()-now_date.getTime();


            Log.e("현재시간과 약속시간의차",""+sub_date);
            Log.e("현재시간",""+now_date.getTime());
            Log.e("약속된 시간",""+promise_date.getTime());
            Log.e("몇분전",""+alram_time_min);
            Log.e("times",""+date);

            OneTimeWorkRequest uploadWorkRequest = new OneTimeWorkRequest.Builder(WorkManager.class)
                    .setInitialDelay(date/1000, TimeUnit.SECONDS)
                    .setInputData(worker_manager_data)
                    .build();
            workManager.getInstance(MainFragment_context).enqueue(uploadWorkRequest);


        } catch (ParseException e) {
            e.printStackTrace();
        }



    }




}
