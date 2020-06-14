package com.example.carrot_market.CONTROLLER;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.MODEL.DTO.ChattingListItem;
import com.example.carrot_market.MODEL.HttpConnect.ChattingRoomLoadTask;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.ChattingListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;




public class ChattingList extends Fragment {

    public static ChattingListAdapter adapter;

    RecyclerView recyclerView;
//컴퓨터 내부 아이피 주소 -110.70.16.56

    //핸드폰 내부 아이피 주소AND 컴퓨터 외부 아이피 주소 -- 118.235.8.79


      LinearLayoutManager linearLayoutManager;
   public static ArrayList<ChattingListItem> arrayList=new ArrayList<>();
    UserInfoSave userInfoSave;


   public static TextView title;

    public static Context context;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


        userInfoSave=new UserInfoSave(getContext());


        ChattingRoomLoadTask chatting_room_load_task=new ChattingRoomLoadTask(handler,userInfoSave.return_account().getId());
        Thread thread=new Thread(chatting_room_load_task);
        thread.run();

    }







    @Override
    public void onDestroy() {
        super.onDestroy();

        try {
//            socketChannel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_chatting_list,container,false);

        //리사이클러 변수 초기화
        recyclerView=v.findViewById(R.id.chatting_list_recycler);


        //화면에 뿌려질 아이템의 구조를 정의하기 위해 레이아웃매니저 객체 생성
        linearLayoutManager=new LinearLayoutManager(v.getContext());

        //리사이클러뷰에 레이아웃 매니저 적용
        recyclerView.setLayoutManager(linearLayoutManager);


        //어댑터 생성자로 객체 생성하여 아이템리스트를 넣어줌
        adapter=new ChattingListAdapter(v.getContext(),arrayList);

        //리사이클러뷰에 어댑터 적용
        recyclerView.setAdapter(adapter);







v.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


    }
});

        return v;

    }



    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message message){
            switch (message.what){
                case 0:
                    try {

                        adapter.notifyItemRangeRemoved(0,arrayList.size());
                        arrayList.clear();
                        Log.e("chat_room_list",""+message.getData().getString("chat_room_list"));
                        JSONArray chat_room_json_array=new JSONArray(message.getData().getString("chat_room_list"));
                        for (int i=0;i<chat_room_json_array.length();i++){
                            ChattingListItem chatting_room_item=new ChattingListItem();
                            JSONObject chat_room_json=chat_room_json_array.getJSONObject(i);


                            //채팅방 클릭시 보내야될 데이터
                            chatting_room_item.setChatting_room_key(chat_room_json.getString("chatting_room_key"));
                            chatting_room_item.setAppointment_time(chat_room_json.getString("appointment_time"));
                            chatting_room_item.setProduct_key(chat_room_json.getString("product_key"));

                            chatting_room_item.setTime(chat_room_json.getString("time_stamp"));
                            chatting_room_item.setLocation(chat_room_json.getString("location"));
                            
                            chatting_room_item.setProfile_id(chat_room_json.getString("opponent_id"));
                            chatting_room_item.setProduct_image_path(chat_room_json.getString("image_path"));
                            chatting_room_item.setProfile_image_path(chat_room_json.getString("opponent_profile_image"));



                            if (chat_room_json.getString("message_type").equals("0")) {

                                    chatting_room_item.setContents(chat_room_json.getString("id") + "님이 약속시간을 정하셨습니다.");


                            }else if (chat_room_json.get("message_type").equals("1")){

                                chatting_room_item.setContents(chat_room_json.getString("message"));
                            }


                            arrayList.add(chatting_room_item);
                            adapter.notifyItemInserted(i);
                            //지역 추가 예정

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

                case 1:

                    break;

                    default:

                        break;
            }


        }
    };

}
