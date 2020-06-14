package com.example.carrot_market.CONTROLLER;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.carrot_market.MODEL.DTO.DealReviewLeaveItem;
import com.example.carrot_market.MODEL.HttpConnect.DealReviewLeaveTask;
import com.example.carrot_market.MODEL.HttpConnect.ProductDetaileDownloadTask;
import com.example.carrot_market.MODEL.HttpConnect.UserBlockTask;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.DealReviewLeaveAdapter;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;
import com.sangcomz.fishbun.define.Define;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class DealReviewLeave extends AppCompatActivity {

    private TextView bad,nomal,good,product_state,product_title,question_title,check_title,check_info
            ,coment_title,coment_info,coment,commit;
    private ImageView coment_image,back,product_image;

    private EditText coment_text;

    private CircleImageView coment_image_remove;

    private ConstraintLayout product_info_layout;
    private LinearLayout deal_review_leave_body;

    private CheckBox user_block;

    private RecyclerView check_list;
    private ArrayList<DealReviewLeaveItem> arrayList=new ArrayList<>();
    private DealReviewLeaveAdapter dealReviewLeaveAdapter;

    private UserInfoSave userInfoSave;
    private int denial_and_positive;
    private Uri coment_image_uri;
    private String other_id,product_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userInfoSave=new UserInfoSave(DealReviewLeave.this);

               other_id=getIntent().getStringExtra("opponent_id");
                product_key=getIntent().getStringExtra("product_key");

        ProductDetaileDownloadTask productDetaileDownloadTask=new ProductDetaileDownloadTask(product_key,userInfoSave.return_account().getId(),"0","0",handler);
        Thread thread=new Thread(productDetaileDownloadTask);
        thread.run();


        setContentView(R.layout.activity_deal_review_leave);


        back=findViewById(R.id.deal_review_leave_back);

        product_info_layout=findViewById(R.id.deal_review_leave_product_info);
        product_image=findViewById(R.id.deal_review_leave_product_image);

        bad=findViewById(R.id.deal_review_leave_bad);
        nomal=findViewById(R.id.deal_review_leave_nomal);
        good=findViewById(R.id.deal_review_leave_good);

        product_state=findViewById(R.id.deal_review_leave_product_state);
        product_title=findViewById(R.id.deal_review_leave_product_title);

        user_block=findViewById(R.id.deal_review_leave_user_block);

        question_title=findViewById(R.id.deal_review_leave_question_title);
        check_title=findViewById(R.id.deal_review_leave_check_title);

        check_info=findViewById(R.id.deal_review_leave_check_info);
        coment_title=findViewById(R.id.deal_revieW_leave_coment_title);
        coment_info=findViewById(R.id.deal_review_leave_coment_info);
        coment=findViewById(R.id.deal_review_leave_coment);
        coment_image=findViewById(R.id.deal_review_leave_coment_image);
        coment_image_remove=findViewById(R.id.deal_review_leave_coment_image_remove);
        coment_text=findViewById(R.id.deal_review_leave_coment);
        commit=findViewById(R.id.deal_review_leave_commit);

        deal_review_leave_body=findViewById(R.id.deal_review_leave_body);
        deal_review_leave_body.setVisibility(View.GONE);

        check_list=findViewById(R.id.deal_review_leave_check_list);
        dealReviewLeaveAdapter=new DealReviewLeaveAdapter(arrayList);
        //리사이클러뷰내에 아이템이 변경될때 깜박임을 제거하기위한
        //속성
        dealReviewLeaveAdapter.setHasStableIds(true);




        question_title.setText(other_id+" 님과 거래가 어떠셨나요");



        check_list.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() { // 세로스크롤 막기
                return false;
            }

            @Override
            public boolean canScrollHorizontally() { //가로 스크롤막기
                return false;
            }
        });


        check_list.setAdapter(dealReviewLeaveAdapter);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit_dialog();

            }
        });


        coment_image_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coment_image_remove.setVisibility(View.GONE);
                Drawable drawable=getResources().getDrawable(R.drawable.camera);
                coment_image.setImageDrawable(drawable);
            }
        });


        //상품 정보란을 클릭 했을때 이벤트처리

        product_info_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        // 후기 감정 선택 버튼
        bad.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                select_face(v);

            }

        });


        nomal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_face(v);

            }
        });


        good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_face(v);
            }
        });


        coment_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //앨범 호출
                FishBun.with(DealReviewLeave.this).setImageAdapter(new GlideAdapter()).setCamera(true).setMaxCount(1).startAlbum();
            }
        });


        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor c = DealReviewLeave.this.getContentResolver().query(coment_image_uri, null, null, null, null);
                c.moveToFirst();
                String path = c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA));

                if (user_block.isChecked()){
                    UserBlockTask userBlockTask=new UserBlockTask(userInfoSave.return_account().getId(),other_id);
                    Thread block_thread=new Thread(userBlockTask);
                    block_thread.run();
                    Log.e("user_block","true");
                }else {
                    Log.e("user_block","false");

                }

                JSONArray deal_review_check_List=new JSONArray();

                for (int i=0;i<arrayList.size();i++){
                    if (arrayList.get(i).getCheck()){
                        JSONObject deal_review_check_obj=new JSONObject();
                        try {
                            deal_review_check_obj.put("review_title",arrayList.get(i).getTitle());
                            deal_review_check_List.put(deal_review_check_obj);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }

                Log.e("check_list","+"+deal_review_check_List);
                DealReviewLeaveTask task=new DealReviewLeaveTask(handler,denial_and_positive,deal_review_check_List.toString(),userInfoSave.return_account().getId(),other_id,coment.getText().toString(),product_key,path);
                Thread thread1=new Thread(task);
                thread1.run();

                Intent intent=new Intent();
                intent.putExtra("review_id",other_id);
                setResult(0,intent);

                finish();

            }
        });
    }


    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //위에서 인텐트에 담아준 리캐스트 코트를 액티비티 리슐트에서 받아서 처리한다.
        switch (requestCode){

            case Define.ALBUM_REQUEST_CODE:
                if (resultCode== RESULT_OK){
                    ArrayList<Parcelable> path = data.getParcelableArrayListExtra(Define.INTENT_PATH);

                         coment_image_uri = (Uri) path.get(0);
                        coment_image.setImageURI(coment_image_uri);
                        coment_image_remove.setVisibility(View.VISIBLE);

                    Toast.makeText(this, "picture", Toast.LENGTH_SHORT).show();
                }
        }
    }




    //선택한 후기의 성향에 따라 데이터를 보여주는 메서드

    private void select_face(View v){
        Toast.makeText(DealReviewLeave.this, "change", Toast.LENGTH_SHORT).show();
        dealReviewLeaveAdapter.notifyItemRangeRemoved(0,arrayList.size());
        arrayList.clear();

        int reset_color = ContextCompat.getColor(DealReviewLeave.this,R.color.default_gray);
        int select_color= ContextCompat.getColor(DealReviewLeave.this,R.color.colorblack);
        Drawable bad_drawable = null;
        Drawable nomal_drawable=null;
        Drawable good_drawable=null;
        ArrayList<String> check_title_list=new ArrayList<>();

        switch (v.getId()){
            case R.id.deal_review_leave_bad:
                denial_and_positive=0;

                check_title_list.add("거래 시간과 정소를 정한 후 연락이 안돼요.");
                check_title_list.add("약속 장소에 나타나지 않았어요");
                check_title_list.add("거래 시간과 장소를 정한 후 거래 직전 취소했어요");
                check_title_list.add("무조건 택배거래만 하려고 해요.");
                check_title_list.add("상품 가치없는 물건을 팔아요");
                check_title_list.add("차에서 내리지도 않고 창문만 열고 거래하려고 해요.");
                check_title_list.add("약속시간을 안 지켜요.");
                check_title_list.add("구매 가격보다 비싼 가격으로 판매해요.");
                check_title_list.add("상품 상태가 설명과 달라요");
                check_title_list.add("무리하게 가격을 깎아요.");
                check_title_list.add("불친절해요.");
                check_title_list.add("예약만 해놓고 거래시간을 명확하게 알려주지 않아요");
                check_title_list.add("상품 설명에 중요한 정보가 누락 됐어요");
                check_title_list.add("너무 늦은 시간이나 새벽에 연락해요.");
                check_title_list.add("질문해도 답이 없어요.");

                coment_text.setHint("여기에 신고사항을 작성하세요");

                bad_drawable=getResources().getDrawable(R.drawable.badface);
                bad_drawable.setColorFilter(select_color, PorterDuff.Mode.SRC_ATOP);

                nomal_drawable=getResources().getDrawable(R.drawable.nomalface);
                nomal_drawable.setColorFilter(reset_color, PorterDuff.Mode.SRC_ATOP);

                good_drawable=getResources().getDrawable(R.drawable.goodface);
                good_drawable.setColorFilter(reset_color, PorterDuff.Mode.SRC_ATOP);
                bad.setTextColor(select_color);
                nomal.setTextColor(reset_color);
                good.setTextColor(reset_color);
                user_block.setVisibility(View.VISIBLE);

                check_title.setText("거래하며 불편했던 점을 선택해 주세요");
                check_info.setVisibility(View.VISIBLE);



                coment_info.setText("신고 내용은 상대방에게 공개되지 않습니다. (선택사항)");
                break;

            case R.id.deal_review_leave_nomal:
                denial_and_positive=1;
                check_title_list.add("무료로 나눠주셨어요.");
                check_title_list.add("상품상태가 설명한 것과 같아요.");
                check_title_list.add("상품설명이 자세해요.");
                check_title_list.add("좋은 상품을 저렴하게 판매해요.");
                check_title_list.add("약속시간을 잘 지켜요.");
                check_title_list.add("응답이 빨라요.");
                check_title_list.add("친절하고 매너가 좋아요.");

                coment_text.setHint("여기에 감사인사를 작성하세요");
                check_title.setText("거래하며 좋았던 점을 선택해 주세요");
                coment_info.setText("작성한 내용은 상대방 프로필에 공개됩니다.");
                coment_info.setText("작성한 내용은 상대방 프로필에 공개됩니다.");

                check_info.setVisibility(View.GONE);
                user_block.setVisibility(View.GONE);

                bad_drawable=getResources().getDrawable(R.drawable.badface);
                bad_drawable.setColorFilter(reset_color, PorterDuff.Mode.SRC_ATOP);

                nomal_drawable=getResources().getDrawable(R.drawable.nomalface);
                nomal_drawable.setColorFilter(select_color, PorterDuff.Mode.SRC_ATOP);

                good_drawable=getResources().getDrawable(R.drawable.goodface);
                good_drawable.setColorFilter(reset_color, PorterDuff.Mode.SRC_ATOP);

                bad.setTextColor(reset_color);
                nomal.setTextColor(select_color);
                good.setTextColor(reset_color);

                break;

            case R.id.deal_review_leave_good:
                denial_and_positive=1;
                check_title_list.add("무료로 나눠주셨어요.");
                check_title_list.add("상품상태가 설명한 것과 같아요.");
                check_title_list.add("상품설명이 자세해요.");
                check_title_list.add("좋은 상품을 저렴하게 판매해요.");
                check_title_list.add("약속시간을 잘 지켜요.");
                check_title_list.add("응답이 빨라요.");
                check_title_list.add("친절하고 매너가 좋아요.");

                coment_text.setHint("여기에 감사인사를 작성하세요");
                check_title.setText("거래하며 좋았던 점을 선택해 주세요");
                coment_info.setText("작성한 내용은 상대방 프로필에 공개됩니다.");

                user_block.setVisibility(View.GONE);
                check_info.setVisibility(View.GONE);

                bad_drawable=getResources().getDrawable(R.drawable.badface);
                bad_drawable.setColorFilter(reset_color, PorterDuff.Mode.SRC_ATOP);

                nomal_drawable=getResources().getDrawable(R.drawable.nomalface);
                nomal_drawable.setColorFilter(reset_color, PorterDuff.Mode.SRC_ATOP);

                good_drawable=getResources().getDrawable(R.drawable.goodface);
                good_drawable.setColorFilter(select_color, PorterDuff.Mode.SRC_ATOP);

                bad.setTextColor(reset_color);
                nomal.setTextColor(reset_color);
                good.setTextColor(select_color);

                break;

            default:
                Toast.makeText(DealReviewLeave.this, "잠시후에 이용해 주세요", Toast.LENGTH_SHORT).show();
        }
        DealReviewLeaveItem item;
        for (int i=0;i<check_title_list.size();i++) {
             item= new DealReviewLeaveItem(check_title_list.get(i));
            dealReviewLeaveAdapter.addItem(item);
            dealReviewLeaveAdapter.notifyItemInserted(i);
            Log.e("item_inset","inter");
        }

        bad.setCompoundDrawablesWithIntrinsicBounds(null,bad_drawable,null,null);
        nomal.setCompoundDrawablesWithIntrinsicBounds(null,nomal_drawable, null, null);
        good.setCompoundDrawablesWithIntrinsicBounds(null,good_drawable,null, null);

        deal_review_leave_body.setVisibility(View.VISIBLE);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        exit_dialog();
    }



    //작성하던 정보가 있었을시 나갈 의향이 있는지 체크하는 다이얼로그

    public void exit_dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(DealReviewLeave.this);

        builder.setTitle("작성하던 후기 남기기 페이지를 나갑니다.");

        builder.setPositiveButton("나가기", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                finish();
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
            }
        });


        AlertDialog alertDialog = builder.create();

        if (deal_review_leave_body.getVisibility()==View.VISIBLE){
            alertDialog.show();
        }else {
            finish();
        }
    }

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {


            JSONObject jsonobj= null;
            try {
                jsonobj = new JSONObject(msg.getData().getString("product_detaile_list").toString());

                Log.e("deal_review_json",""+jsonobj);


                product_title.setText(jsonobj.getString("title"));
                JSONArray jsonArray=new JSONArray(jsonobj.getString("image_path_list"));
                Glide.with(DealReviewLeave.this).load(API_URL+"image/"+jsonArray.getJSONObject(0).getString("image_path")).into(product_image);



            } catch (JSONException e) {
                e.printStackTrace();
            }

            return false;
        }
    });




}
