package com.example.carrot_market.CONTROLLER;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.carrot_market.MODEL.DTO.ChattingItem;
import com.example.carrot_market.MODEL.HttpConnect.ChattingInfoLoadTask;
import com.example.carrot_market.MODEL.HttpConnect.ChattingLoadTask;
import com.example.carrot_market.MODEL.HttpConnect.ChattingRoomInsertTask;
import com.example.carrot_market.MODEL.HttpConnect.DealReviewCheckTask;
import com.example.carrot_market.MODEL.HttpConnect.SendMessageTask;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.ChattingAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static android.graphics.Color.GRAY;
import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;


public class Chatting extends AppCompatActivity {

    private ConstraintLayout chatting_deal_review_layout;
    private TextView chatting_deal_review;

    public  ChattingAdapter adapter_c;
    public static RecyclerView recyclerView;
    public static LinearLayoutManager linearLayoutManager;
    public  ArrayList<ChattingItem> arrayList_c=new ArrayList<>();
    public static Context context;
    ScrollView myScrollView;

    //채팅창 내부에서 사용할 뷰의 변수 선언
    TextView product_title,product_price,id,temperature;
    EditText edit_contents;
    ImageView product_image,chatting_send,chatting_more,chatting_back;
    Button deal_review;
    ImageButton add,deal_promise_button;
    UserInfoSave userInfoSave;


    String product_key;
    public static String chatting_room_key;

    InputMethodManager imm;
    ViewGroup linearLayout_parent;
//    SoftKeyboard softKeyboard;
    String intent_id;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


        chatting_deal_review_layout=findViewById(R.id.chatting_deal_review_layout);

        chatting_deal_review=findViewById(R.id.chatting_deal_review);
        deal_promise_button=findViewById(R.id.chatting_deal_promise);
        context=this;
        //리사이클러 변수 초기화
        recyclerView=findViewById(R.id.chatting_recycler);


        //화면에 뿌려질 아이템의 구조를 정의하기 위해 레이아웃매니저 객체 생성
        linearLayoutManager=new LinearLayoutManager(this);

        //리사이클러뷰에 레이아웃 매니저 적용
        recyclerView.setLayoutManager(linearLayoutManager);

        //어댑터 생성자로 객체 생성하여 아이템리스트를 넣어줌
        adapter_c=new ChattingAdapter(this, arrayList_c);

        //리사이클러뷰에 어댑터 적용
        recyclerView.setAdapter(adapter_c);

        add=findViewById(R.id.chatting_add);
        product_image=findViewById(R.id.chatting_product_image);
        product_title=findViewById(R.id.chatting_product_title);
        product_price=findViewById(R.id.chatting_product_price);
        id=findViewById(R.id.chatting_id);
        temperature=findViewById(R.id.chatting_temperature);
        userInfoSave=new UserInfoSave(Chatting.this);
        edit_contents=findViewById(R.id.chatting_contents);

        chatting_send=findViewById(R.id.chatting_send);
        chatting_send.setClickable(false);
        chatting_more=findViewById(R.id.chatting_more);
        chatting_back=findViewById(R.id.chatting_back);



        linearLayout_parent=(LinearLayout)findViewById(R.id.chatting_parent_linear);

        imm = (InputMethodManager)Chatting.this.getSystemService(INPUT_METHOD_SERVICE);
//        softKeyboard=new SoftKeyboard(linearLayout_parent,imm);


        //ChattingList 액티비티에서 넘겨받은 채팅방 키와 제품키 상대편 아이디를 참고하여 채팅방 상세 정보를 로드 시킴
        final Intent intent=getIntent();

        int check=intent.getIntExtra("check",0);

        //위에 있는 것은 채팅리스트를 통해 들어 왔을때
        //아래는 상품 자세히 보기를 통해 들어 왔을때

        if (check==0){

        }else if (check==1){

        }

        product_key= intent.getStringExtra("product_key");
        //상대편 아이디
         intent_id =intent.getStringExtra("id");


         Log.e("intent_info",""+product_key+"//"+intent_id);



        ChattingRoomInsertTask chattingRoomInsertTask=new ChattingRoomInsertTask(intent_id,new UserInfoSave(this).return_account().getId(),product_key,handler);
        Thread chatting_room_insert_thread=new Thread(chattingRoomInsertTask);
        chatting_room_insert_thread.start();








        ChattingInfoLoadTask chattingInfoLoadTask=new ChattingInfoLoadTask(intent_id,product_key,handler);
        final Thread chatting_info_thread=new Thread(chattingInfoLoadTask);
        chatting_info_thread.run();





        chatting_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edit_contents.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()<=0) {
                    chatting_send.setClickable(false);
                    chatting_send.setColorFilter(GRAY);
                }else {
                    chatting_send.setClickable(true);
                    chatting_send.setColorFilter(Color.parseColor("#ff9900"));
                }

            }
        });


    chatting_more.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(Chatting.this, ""+chatting_room_key, Toast.LENGTH_SHORT).show();
            PopupMenu chatting_room_more=new PopupMenu(Chatting.this,v);

            MenuInflater inflater=getMenuInflater();
            //아이템 추가 골격

            inflater.inflate(R.menu.chatting_room_menu, chatting_room_more.getMenu());

            chatting_room_more.show();
            //나의 현재 위치를 지정하는 팝업 이벤트 처리 부분



            chatting_room_more.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    switch (item.getItemId()){


                        case R.id.chatting_room_exit:


                            JSONObject jsonObject=new JSONObject();
                            try {
                                jsonObject.put("message_type","4");
                            jsonObject.put("chat_room_key",chatting_room_key);
                            jsonObject.put("product_key",product_key);
                            jsonObject.put("id",userInfoSave.return_account().getId());
                            jsonObject.put("opponent",intent_id);
                            jsonObject.put("message",userInfoSave.return_account().getId()+"님이 나가셨습니다.");
//                            for (int i=0; i<ChattingList.arrayList.size();i++){
//                                if (ChattingList.arrayList.get(i).getChatting_room_key().equals(chatting_room_key)){
//                                    ChattingList.arrayList.remove(i);
//                                    ChattingList.adapter.notifyItemRemoved(i);
//                                }
//                            }

                                Log.e("chatting_struct",""+jsonObject);

                                for (int i=0;i<ChattingList.arrayList.size();i++) {
                                    if (ChattingList.arrayList.get(i).getChatting_room_key().equals(chatting_room_key)) {
                                        ChattingList.adapter.notifyItemRemoved(i);
                                        ChattingList.arrayList.remove(i);
                                        break;
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            SendMessageTask sendMessageTask=new SendMessageTask(jsonObject.toString());
                            Thread thread1=new Thread(sendMessageTask);
                            thread1.run();

                            finish();

                            break;

                    }




                    return false;
                }
            });


        }
    });



        //상품 정보를 보여주는 레이아웃 을 처티 했을때의 이벤트 처리
        chatting_deal_review_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(Chatting.this,Product.class);
                intent1.putExtra("product_key",product_key);

                startActivity(intent1);
            }
        });




        //거래후기를 작성하기 위한 버튼 이벤트 처리
        chatting_deal_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //거래 후기 작성 했으면 리스트 보여주는 창으로 가기
//
//            Intent intent1=new Intent(Chatting.this,DealReviewLeave.class);
//            intent1.putExtra("product_key",product_key);
//            intent1.putExtra("opponent_id",intent_id);
//            startActivity(intent1);

//
//                Intent intent=new Intent(context, MyLeaveDealReview.class);
//                intent.putExtra("product_key",product_key);
//                intent.putExtra("opponent_id",userInfoSave.return_account().getId());
//                intent.putExtra("accept_check",true);
//                context.startActivity(intent);
                DealReviewCheckTask dealReviewCheckTask=new DealReviewCheckTask(userInfoSave.return_account().getId(),
                        intent_id,product_key,handler);
            Thread thread1=new Thread(dealReviewCheckTask);
            thread1.start();



            }
        });





        //약속 예약 메시지를 보내기 위한 버튼 이벤트 처리
        deal_promise_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONObject jsonObject=new JSONObject();
                try {
                jsonObject.put("chat_room_key",chatting_room_key);
                jsonObject.put("product_key",product_key);
                jsonObject.put("id",userInfoSave.return_account().getId());
                jsonObject.put("opponent",intent_id);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("deal_promise_start",""+jsonObject.toString());
                Intent intent1=new Intent(Chatting.this,DealPromise.class);
                intent1.putExtra("deal_promise_message",jsonObject.toString());
                startActivity(intent1);


            }
        });


chatting_send.setOnClickListener(new View.OnClickListener() {
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onClick(View v) {

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("message_type","1");
            jsonObject.put("chat_room_key",chatting_room_key);
            jsonObject.put("product_key",product_key);
            jsonObject.put("id",userInfoSave.return_account().getId());
            jsonObject.put("opponent",intent_id);
            jsonObject.put("message",edit_contents.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }


        //chat_room_key
        //product_key
        //id
        //opponent
        //message
        //message_key

        SendMessageTask sendMessageTask=new SendMessageTask(jsonObject.toString());
        Thread thread1=new Thread(sendMessageTask);

        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread1.run();


        edit_contents.setText(null);





    }
});

    }


    @Override
    public void onProvideAssistData(Bundle data) {
        super.onProvideAssistData(data);
    }

    public Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

       switch (msg.what){
           //채팅 액티비티를 보고 있을때 채팅 로드
           case 0:
               try {
                   Log.e("ca",""+msg.getData().getString("chatting_info"));
                   JSONArray jsonArray=new JSONArray(msg.getData().getString("chatting_info"));
                   //채팅 리스트 로드

                   for (int cl=0;cl<jsonArray.length();cl++){
                       JSONObject chatting_item_json=jsonArray.getJSONObject(cl);
                       ChattingItem chattingItem=new ChattingItem();
                       chattingItem.setId(chatting_item_json.getString("id"));
                       chattingItem.setContents(chatting_item_json.getString("message"));
                       chattingItem.setContent_time(chatting_item_json.getString("time_stamp"));
                       chattingItem.setMessage_type(chatting_item_json.getString("message_type"));
//                       chattingItem.setContents_image_time()
                       chattingItem.setOther_profile_image_path(chatting_item_json.getString("profile_image"));
                       arrayList_c.add(chattingItem);
                       adapter_c.notifyItemInserted(cl);
                       recyclerView.scrollToPosition(arrayList_c.size()-1);
                   }


               } catch (JSONException e) {
                   e.printStackTrace();
               }


               break;

               //채팅방에 대한 정보 로드
           case 1:
               try {

                   JSONObject jsonObject1=new JSONObject(msg.getData().getString("chatting_info"));

                   Log.e("채팅방 상품에대한 정보",""+jsonObject1.toString());
                   Glide.with(Chatting.this).load(API_URL+"/image/"+jsonObject1.getString("image_path")).into(product_image);
                   product_title.setText(jsonObject1.getString("title"));

                   DecimalFormat myFormatter = new DecimalFormat("###,###");
                   String formattedStringPrice = myFormatter.format(Integer.parseInt(jsonObject1.getString("price")));
                   product_price.setText(formattedStringPrice+" 원");



                   temperature.setText(jsonObject1.getString("manner_temperature"));
                    id.setText(jsonObject1.getString("id"));

               } catch (JSONException e) {
                   e.printStackTrace();
               }



//                   if (!chatting_product_info_jsonobj.getString("opponent_id").equals(null)){
//                   id.setText(chatting_product_info_jsonobj.getString("opponent_id"));
//                       temperature.setText(chatting_product_info_jsonobj.getString("opponent_manner_temperature").replace("0",""));
//                   }else {
//                       id.setText("탈퇴한계정 입니다.");
//                       temperature.setText("36.5");
//                   }
//
//                   product_title.setText(chatting_product_info_jsonobj.getString("title"));
//                   DecimalFormat myFormatter = new DecimalFormat("###,###");
//                   String formattedStringPrice = myFormatter.format(chatting_product_info_jsonobj.getInt("price"));
//                   product_price.setText(formattedStringPrice+" 원");
//

               break;

           case 3:
               try {
                   JSONObject jsonObject=new JSONObject(msg.getData().getString("chatting_info"));
                    Log.e("chatting_3",jsonObject.toString());

               } catch (JSONException e) {
                   e.printStackTrace();
               }

               break;


               //채팅룸키를 받아 업데이트를 해주는 곳
           case 5:
               chatting_room_key=msg.getData().getString("chatting_room_key");
               //상품을 통해 들어 왔을때 null
               ChattingLoadTask chattingLoadTask=new ChattingLoadTask(chatting_room_key,userInfoSave.return_account().getId(),handler);
               Thread thread=new Thread(chattingLoadTask);
               thread.run();
               break;

               default:

                   break;

           case 10:

               Log.e("deal_review_what",msg.getData().getString("deal_review_check"));


               if (msg.getData().getString("deal_review_check").equals("1")){

                Intent intent=new Intent(context, MyLeaveDealReview.class);
                intent.putExtra("product_key",product_key);
                intent.putExtra("opponent_id",intent_id);
                intent.putExtra("accept_check",false);
               context.startActivity(intent);
               }else {
                   Intent intent1=new Intent(Chatting.this,DealReviewLeave.class);
                   intent1.putExtra("product_key",product_key);
                   intent1.putExtra("opponent_id",intent_id);
                   startActivity(intent1);
               }





               break;
       }



            return false;
        }
    });

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Chatting.chatting_room_key="null";

    }


}
