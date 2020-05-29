package com.example.carrot_market.RecyclerView.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.carrot_market.CONTROLLER.METHOD.DateTimeConverter;
import com.example.carrot_market.MODEL.DTO.ChattingItem;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class ChattingAdapter extends RecyclerView.Adapter<ChattingAdapter.CustomViewHolder> {

    ArrayList<ChattingItem> arrayList;
    Context context;
    UserInfoSave userInfoSave;
    InputMethodManager imm;


    public ChattingAdapter( Context context , ArrayList<ChattingItem> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
        imm = (InputMethodManager)context.getSystemService(INPUT_METHOD_SERVICE);
    }

    @NonNull
    @Override
    public ChattingAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.chatting_item,parent,false);
        ChattingAdapter.CustomViewHolder holder=new ChattingAdapter.CustomViewHolder(v);

        userInfoSave=new UserInfoSave(parent.getContext());

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ChattingAdapter.CustomViewHolder holder, int position) {

        if (0<holder.getAdapterPosition()){

        SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date=simpleDateFormat1.parse(arrayList.get(holder.getAdapterPosition()).getContent_time());
            Date date1=simpleDateFormat1.parse(arrayList.get(holder.getAdapterPosition()-1).getContent_time());


            if (date.getDay()!=date1.getDay()){
                holder.chatting_date_linear.setVisibility(View.VISIBLE);
                holder.chatting_date_text.setText(new DateTimeConverter(arrayList.get(holder.getAdapterPosition()).getContent_time())
                        .return_yaer_month_day());
            }else {
                holder.chatting_date_linear.setVisibility(View.GONE);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        }else if (holder.getAdapterPosition()==0){
            holder.chatting_date_linear.setVisibility(View.VISIBLE);
            try {
                holder.chatting_date_text.setText(new DateTimeConverter(arrayList.get(holder.getAdapterPosition()).getContent_time())
                        .return_yaer_month_day());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        holder.id_other.setText(arrayList.get(holder.getAdapterPosition()).getId());
        Log.e("chatting_adapter","insert");
        //message_type 0는 약속메시지 알림
        //message_type 1은 일반 메시지
        //message_type 4는 채팅방 나감 메시지
        if (arrayList.get(holder.getAdapterPosition()).getMessage_type().equals("0")){

                holder.item_oppointment.setVisibility(View.VISIBLE);
                holder.item_other.setVisibility(View.GONE);
                holder.item_my.setVisibility(View.GONE);
                holder.chatting_item_line_text.setVisibility(View.VISIBLE);
                //contents is
                //ex)12 월 31 (화) 4:30 오후
                // 날짜 형식 에러

//            chattingItem.setContents
//            alram_json_object.put("date_format",date_format);
//            alram_json_object.put("alrma_time_min",select_alram_time_item.getMinite());
//            "message":{"date_format":"2020-03-22 09:26:37","alram_time_min":60}

            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREAN);
            try {
                Log.e("alram_time_message",arrayList.get(holder.getAdapterPosition()).getContents());

                JSONObject jsonObject=new JSONObject(arrayList.get(holder.getAdapterPosition()).getContents());
                Date date=simpleDateFormat.parse(jsonObject.getString("date_format"));
                 simpleDateFormat=new SimpleDateFormat("MM 월 dd (EEE) HH:mm", Locale.KOREAN);
                String alram_message=simpleDateFormat.format(date);
                holder.oppointment_time.setText(alram_message);
                holder.chatting_item_line_text.setText("약속시간"+jsonObject.get("alram_time_min").toString()+"분 전에 알람이 울립니다.");

            } catch (ParseException | JSONException e) {
                e.printStackTrace();
            }

//            holder.chatting_item_line_text.setText();
            //몇분전에 알람이 오는지 보이는 텍스트

            Log.e("arr",arrayList.get(holder.getAdapterPosition()).getContents());



                holder.oppointment_time_text.setText(arrayList.get(holder.getAdapterPosition()).getId()+" 님이 거래 약속을 만들었어요.");


                Log.e("message_type","0");
        }
        else if (arrayList.get(holder.getAdapterPosition()).getMessage_type().equals("1")) {
            Log.e("message_type", "1");
            if (arrayList.get(holder.getAdapterPosition()).getId().equals(userInfoSave.return_account().getId())) {
                //아이디가 내 계정의 아이디오 같을때 내 채팅 아이템 로드

                holder.item_other.setVisibility(View.GONE);
                holder.item_oppointment.setVisibility(View.GONE);
                holder.item_my.setVisibility(View.VISIBLE);
                holder.contents.setText(arrayList.get(position).getContents());


                //내가 보낸 채팅메시지의 시간 변환
                DateTimeConverter dateTimeConverter=new DateTimeConverter(arrayList.get(holder.getAdapterPosition()).getContent_time());
                try {
                    holder.contents_time.setText(dateTimeConverter.return_hour_min());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                holder.chatting_item_line_text.setVisibility(View.GONE);

            }else {
                //상대편 채팅 아이템 로드

                Log.e("chatting_log", "other");

                holder.item_other.setVisibility(View.VISIBLE);
                holder.item_my.setVisibility(View.GONE);
                holder.item_oppointment.setVisibility(View.GONE);
                holder.chatting_item_line_text.setVisibility(View.GONE);

                holder.contents_other.setText(arrayList.get(holder.getAdapterPosition()).getContents());
                DateTimeConverter dateTimeConverter=new DateTimeConverter(arrayList.get(holder.getAdapterPosition()).getContent_time());
                try {
                    holder.contents_time_other.setText(dateTimeConverter.return_hour_min());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (!arrayList.get(holder.getAdapterPosition()).getOther_profile_image_path().equals("null")) {
                    Glide.with(context).load(API_URL + "image/" + arrayList.get(holder.getAdapterPosition()).getOther_profile_image_path()).into(holder.id_other_profile_image);
                }else {
                    holder.id_other_profile_image.setImageDrawable(context.getResources().getDrawable(R.drawable.profile_image_man));
                }
                //채팅방이 없을때 채팅방 생성 하고 있을때 최근채팅내용 업데이트 하고 최상단으로 이동
            }

            Log.e("chatting_contents",""+arrayList.get(holder.getAdapterPosition()).getContents());

        }else if (arrayList.get(holder.getAdapterPosition()).getMessage_type().equals("4")){
            holder.item_other.setVisibility(View.GONE);
            holder.item_my.setVisibility(View.GONE);
            holder.item_oppointment.setVisibility(View.GONE);
            holder.chatting_item_line_text.setVisibility(View.VISIBLE);
            holder.chatting_item_line_text.setText(arrayList.get(holder.getAdapterPosition()).getContents());
            Log.e("message_type","4");

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imm.hideSoftInputFromWindow(holder.itemView.getWindowToken(),0);

            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout item_my,item_other,item_oppointment;

        ImageView contents_image,contents_image_other;
        TextView image_time,image_time_other,contents_time,contents_time_other,contents,contents_other,id_other,oppointment_time,oppointment_time_text,chatting_item_line_text;
        ProgressBar Image_progress,Image_progress_other;
        CircleImageView id_other_profile_image;
        LinearLayout chatting_date_linear;
        TextView chatting_date_text;




        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            //년 월 일 을 알려주는 뷰
            chatting_date_linear=itemView.findViewById(R.id.chatting_date_layout);
            chatting_date_text=itemView.findViewById(R.id.chatting_date_text);



            //약속 시간 설정 알림
            item_oppointment= itemView.findViewById(R.id.chatting_item_oppointment_time_layout);
            oppointment_time=itemView.findViewById(R.id.chatting_item_oppointment_time);
            oppointment_time_text=itemView.findViewById(R.id.chatting_item_oppointment_time_text);
            chatting_item_line_text=itemView.findViewById(R.id.chatting_item_line_text);


            //나의 채팅 아이템
            id_other=itemView.findViewById(R.id.chatting_item_other_id);
            item_my=itemView.findViewById(R.id.chatting_item);
            image_time=itemView.findViewById(R.id.chatting_contents_iamge_time);
            contents_time=itemView.findViewById(R.id.chatting_contents_time);
            contents=itemView.findViewById(R.id.chatting_item_contents);
            contents_image=itemView.findViewById(R.id.chatting_item_contents_image);


            //상대편 채팅 아이템
            item_other=itemView.findViewById(R.id.chatting_item_other);
            image_time_other=itemView.findViewById(R.id.chatting_contents_iamge_time_other);
            contents_time_other=itemView.findViewById(R.id.chatting_contents_time_other);
            contents_other=itemView.findViewById(R.id.chatting_item_contents_other);
            id_other_profile_image=itemView.findViewById(R.id.chatting_item_other_profile_image);



            Image_progress=itemView.findViewById(R.id.chatting_item_contents_image_progress);

        }
    }
}
