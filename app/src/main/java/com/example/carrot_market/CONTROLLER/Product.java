package com.example.carrot_market.CONTROLLER;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.carrot_market.MODEL.DTO.ProductComentItem;
import com.example.carrot_market.MODEL.DTO.ProductSellerProductListItem;
import com.example.carrot_market.MODEL.HttpConnect.ProductDetaileDownloadTask;
import com.example.carrot_market.MODEL.HttpConnect.ProductFavoriteTask;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.ProductComentAdapter;
import com.example.carrot_market.RecyclerView.Adapter.ProductSellerProductListAdapter;
import com.example.carrot_market.ViewPager.Adapter.ProductViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;


public class Product extends AppCompatActivity {

    private ViewPager viewPager;
    private ProductViewPagerAdapter pagerAdapter;

    protected RecyclerView product_coment_recycler;
    protected ProductSellerProductListAdapter seller_product_adapter;
    private ProductComentAdapter product_coment_adapter;
    protected GridLayoutManager seller_product_layout_manager;
    private LinearLayoutManager product_coment_layout_manager;
    protected ArrayList<ProductSellerProductListItem> seller_product_list=new ArrayList<>();
    private ArrayList<ProductComentItem> coment_list=new ArrayList<>();
    private ConstraintLayout product_user_profile_constraint_layout;
    UserInfoSave userInfoSave;

    private TabLayout product_indicator;

    protected ScrollView scrollView;
    boolean aa=true;

    protected ImageButton backbutton,favorit_button,product_more;
    private Button product_buy_button;
    protected ConstraintLayout linearLayout;
    protected TextView Top_title,title,user_id,category,product_descripion,user_tamperature_num,product_price,product_user_location
            ,product_coment_all_see,product_coment_count;
    private Button write_coment;
    private CircleImageView user_profile_image;
    private ProgressBar user_tamperature;
    private String product_id;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);


        userInfoSave=new UserInfoSave(Product.this);
        //판매 상품의 사진 뷰페이저 초기화
        viewPager=findViewById(R.id.product_view_pager);


        product_indicator=findViewById(R.id.product_view_pager_indicator_tab_layout);
        product_indicator.setupWithViewPager(viewPager);


        //해당 상품에 작성된 댓글을 보여주는 리사이클러뷰 초기화
        product_coment_recycler=findViewById(R.id.product_in_coment_recycler);
        product_coment_adapter=new ProductComentAdapter(coment_list,this);
        product_coment_layout_manager=new LinearLayoutManager(this);

        product_coment_recycler.setLayoutManager(product_coment_layout_manager);
        product_coment_recycler.setAdapter(product_coment_adapter);


        //판매자가 판매하고 있는 리스트를 보여주는 리사이클러뷰 초기화

        seller_product_adapter=new ProductSellerProductListAdapter(this,seller_product_list);
        seller_product_layout_manager=new GridLayoutManager(this,1);
        seller_product_layout_manager.setOrientation(GridLayoutManager.HORIZONTAL);




        scrollView=findViewById(R.id.product_srollview);



        //판매자 프로필을 담고 있는 레이아웃
        product_user_profile_constraint_layout=findViewById(R.id.product_user_profile_constraint_layout);

        //버튼 초기화
        backbutton=findViewById(R.id.product_back);
        linearLayout=findViewById(R.id.product_linear1);
        favorit_button=findViewById(R.id.product_favorite);
        product_more=findViewById(R.id.product_more);
        Top_title=findViewById(R.id.product_title);
        product_buy_button=findViewById(R.id.product_buy_button);

        write_coment=findViewById(R.id.product_insert_coment);
        title=findViewById(R.id.product_product_title);
        user_id=findViewById(R.id.product_user_id);
        category=findViewById(R.id.product_product_category_and_time);
        product_descripion=findViewById(R.id.product_product_description);
        user_tamperature_num=findViewById(R.id.product_user_tamperature_num);
        product_price=findViewById(R.id.product_price);
        user_profile_image=findViewById(R.id.product_user_profile_image);
        user_tamperature=findViewById(R.id.product_user_tamperature);
        product_user_location=findViewById(R.id.product_user_location);
        product_coment_all_see=findViewById(R.id.product_coment_all_see);
        product_coment_count=findViewById(R.id.product_coment_count);



        //제품의 상세 내용 데이터 로드

        /*
        * 더미 데이터
        * */

        String product_key=getIntent().getStringExtra("product_key");
        ProductDetaileDownloadTask task=new ProductDetaileDownloadTask(product_key,userInfoSave.return_account().getId(),"0","3",handler);
        Thread thread=new Thread(task);
        thread.run();


//        product_coment_recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                int last=((LinearLayoutManager)product_coment_recycler.getLayoutManager()).findLastCompletelyVisibleItemPosition();
//                int itemcount=product_coment_recycler.getAdapter().getItemCount();
//
//                if (last==itemcount-1){
//                    String product_key=getIntent().getStringExtra("product_key");
//                    ProductDetaileDownloadTask task=new ProductDetaileDownloadTask(product_key,userInfoSave.return_account().getId(),"0","3",handler);
//                    Thread thread=new Thread(task);
//                    thread.run();
//
//
//                }
//
//            }
//        });





        product_user_profile_constraint_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Product.this,ProfileDetail.class);
                intent.putExtra("profile_id",user_id.getText().toString());
                startActivity(intent);
            }
        });

        product_buy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //로그인중이라면 채팅창으로 이동
                //로그인중이 아니라면 회원가입창으로 이동

                    Intent intent=new Intent(Product.this,Chatting.class);
                    intent.putExtra("product_key",getIntent().getStringExtra("product_key"));
                    intent.putExtra("opponent",getIntent().getStringExtra("id"));
                    intent.putExtra("seller",product_id);
                    intent.putExtra("buyer",userInfoSave.return_account().getId());
                    intent.putExtra("id",product_id);


                    Log.e("Product.class",""+getIntent().getStringExtra("seller")+"//"+getIntent().getStringExtra("buyer"));
                    startActivity(intent);



            }
        });

        //제품의 댓글 작성 버튼 클릭 이벤트 처리
        write_coment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Product.this,ProductComent.class);
                intent.putExtra("key","product");
                intent.putExtra("product_key",getIntent().getStringExtra("product_key"));
                startActivity(intent);
            }
        });


        product_coment_all_see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Product.this,ProductComent.class);
                intent.putExtra("key","product");
                intent.putExtra("product_key",getIntent().getStringExtra("product_key"));
                startActivity(intent);
            }
        });

        //관심 상품 등록 버튼 클릭 이벤트 처리 구현
        favorit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //로그인 안되어 있을떄 처리하기
                ProductFavoriteTask task=new ProductFavoriteTask(userInfoSave.return_account().getId(),
                        getIntent().getStringExtra("product_key"));
                Thread thread=new Thread(task);
                thread.run();


                //로그인 되어있을시 처리
                if (aa) {
                    Drawable drawable = getResources().getDrawable(R.drawable.favorite_red);
                    favorit_button.setImageDrawable(drawable);
                    Toast.makeText(Product.this, "관심 상품를 등록 했습니다", Toast.LENGTH_SHORT).show();
                    aa=false;
                }else {

                    Drawable drawable = getResources().getDrawable(R.drawable.favorite_border);
                    favorit_button.setImageDrawable(drawable);
                    Toast.makeText(Product.this, "관심 상품을 해제 했습니다", Toast.LENGTH_SHORT).show();
                    aa=true;

                }

            }
        });


        //새로고침 버튼 구현 팜업 메뉴 형태
        product_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                PopupMenu mylocation_menu=new PopupMenu(getApplicationContext(),v);
                MenuInflater inflater=getMenuInflater();
                inflater.inflate(R.menu.refresh_menu, mylocation_menu.getMenu());

                mylocation_menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.refresh:

                                onRestart();

                                break;

                        }

                        return false;
                    }
                });
                mylocation_menu.show();



            }
        });


        //뒤로가기 버튼 이벤트 구현
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();


            }
        });





        //일정 크기 이상 스크롤 했을때 타이틀이 보이게 되는 이벤트 처리
        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (500<scrollY) {
                    linearLayout.setBackgroundResource(R.color.colorwhite);
                    Top_title.setVisibility(View.VISIBLE);
                }
                else {
                    linearLayout.setBackgroundResource(R.color.Nocolor);
                    Top_title.setVisibility(View.INVISIBLE);
                }


                }
        });
        scrollView.scrollTo(0,100);


    }


    //액티비티 생명주기 온 스타트 --데이터를 리셋후 데이터 로드 시키기
    //로딩 구현-


    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        product_coment_adapter.notifyItemRangeRemoved(0,coment_list.size());
        coment_list.clear();
        String product_key=getIntent().getStringExtra("product_key");

        ProductDetaileDownloadTask task=new ProductDetaileDownloadTask(product_key,userInfoSave.return_account().getId(),"0","3",handler);
        Thread thread=new Thread(task);
        thread.run();


    }

Handler handler=new Handler(){

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);

        switch (msg.what){
            case 0:
                try {

                    Log.e("error",""+msg.getData().getString("product_detaile_list").toString());

                    JSONObject jsonobj=new JSONObject(msg.getData().getString("product_detaile_list").toString());

                    Log.e("jsonarray",""+jsonobj);

                    Top_title.setText(jsonobj.getString("title"));
                    title.setText(jsonobj.getString("title"));
                    user_id.setText(jsonobj.getString("id"));


                    if (userInfoSave.return_account().getId().equals(user_id.getText().toString())){
                        product_buy_button.setVisibility(View.GONE);
                    }else {
                        product_buy_button.setText("채팅으로 거래하기");
                        product_buy_button.setVisibility(View.VISIBLE);
                    }

                    product_id=jsonobj.getString("id");
                    category.setText(jsonobj.getString("category")+"."+jsonobj.getString("days"));
                    product_descripion.setText(jsonobj.getString("description"));

                    user_tamperature_num=findViewById(R.id.product_user_tamperature_num);
                    user_tamperature_num.setText(""+jsonobj.getDouble("manner_temperature"));
                    DecimalFormat myFormatter = new DecimalFormat("###,###");
                    String formattedStringPrice = myFormatter.format(Integer.parseInt(jsonobj.getString("price")));
                    product_price.setText(formattedStringPrice+" 원");

                    product_coment_all_see.setText(jsonobj.getString("coment_count")+"개 댓글 전체 보기");
                    product_coment_count.setText("댓글 "+jsonobj.getString("coment_count"));


                    if (!jsonobj.getString("profile_image").equals("null")){
                        Glide.with(Product.this).load(API_URL+"image/"+jsonobj.getString("profile_image")).into(user_profile_image);
                    }else {
                        user_profile_image.setImageDrawable(getResources().getDrawable(R.drawable.profile_image_man));
                    }


                    product_user_location.setText(jsonobj.getString("address"));
                    int tam= (int) jsonobj.getDouble("manner_temperature");
                    user_tamperature.setProgress(tam);


                    if (jsonobj.getBoolean("favorite")) {
                        Drawable drawable = getResources().getDrawable(R.drawable.favorite_red);
                        favorit_button.setImageDrawable(drawable);
                        aa=false;

                    }else {
                        Drawable drawable = getResources().getDrawable(R.drawable.favorite_border);
                        favorit_button.setImageDrawable(drawable);
                        aa=true;
                    }


                    JSONArray jsonArray=new JSONArray(jsonobj.getString("image_path_list"));

                            pagerAdapter=new ProductViewPagerAdapter(Product.this,jsonArray.length(),jsonArray);
                            viewPager.setAdapter(pagerAdapter);


                            JSONArray json_coment_list=new JSONArray(jsonobj.getString("coment_list"));
                                Log.e("json_len",""+json_coment_list);

                            for (int i =0;i<json_coment_list.length();i++){
                                JSONObject jsonObject=json_coment_list.getJSONObject(i);
                                ProductComentItem item = new ProductComentItem();
                                item.setKey(jsonObject.getString("coment_key"));
                                item.setProfile_image_path(jsonObject.getString("profile_image"));
                                item.setId(jsonObject.getString("id"));
                                item.setComent(jsonObject.getString("coment"));
                                item.setDate(jsonObject.getString("time_stamp"));
                                coment_list.add(item);
                                product_coment_adapter.notifyItemInserted(i);
                            }



                    /*
                     * 더미 데이터
                     * */

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;

        }
    }
};
}
