package com.example.carrot_market.CONTROLLER;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ScrollView;

import android.widget.TextView;
import android.widget.Toast;


import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.ProductComentAdapter;
import com.example.carrot_market.RecyclerView.Adapter.ProductSellerProductListAdapter;
import com.example.carrot_market.MODEL.DTO.ProductComentItem;
import com.example.carrot_market.MODEL.DTO.ProductSellerProductListItem;
import com.example.carrot_market.ViewPager.Adapter.ProductViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;


import java.util.ArrayList;



public class Product extends AppCompatActivity {

    private ViewPager viewPager;
    private ProductViewPagerAdapter pagerAdapter;

    protected RecyclerView seller_product_recycler,product_coment_recycler;
    protected ProductSellerProductListAdapter seller_product_adapter;
    private ProductComentAdapter product_coment_adapter;
    protected GridLayoutManager seller_product_layout_manager;
    private LinearLayoutManager product_coment_layout_manager;
    protected ArrayList<ProductSellerProductListItem> seller_product_list=new ArrayList<>();
    private ArrayList<ProductComentItem> coment_list=new ArrayList<>();



    private TabLayout product_indicator;

    protected ScrollView scrollView;
    boolean aa=true;

    protected ImageButton backbutton,favorit_button,product_more;
    protected ConstraintLayout linearLayout;
    protected TextView title;
    private Button write_coment;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);


        //판매 상품의 사진 뷰페이저 초기화
        viewPager=findViewById(R.id.product_view_pager);
        pagerAdapter=new ProductViewPagerAdapter(this,10);
        viewPager.setAdapter(pagerAdapter);
        product_indicator=findViewById(R.id.product_view_pager_indicator_tab_layout);
        product_indicator.setupWithViewPager(viewPager);


        //해당 상품에 작성된 댓글을 보여주는 리사이클러뷰 초기화
        product_coment_recycler=findViewById(R.id.product_in_coment_recycler);
        product_coment_adapter=new ProductComentAdapter(coment_list,this);
        product_coment_layout_manager=new LinearLayoutManager(this);
        product_coment_recycler.setLayoutManager(product_coment_layout_manager);
        product_coment_recycler.setAdapter(product_coment_adapter);


        //판매자가 판매하고 있는 리스트를 보여주는 리사이클러뷰 초기화
        seller_product_recycler=findViewById(R.id.product_seller_product_list_recycler);
        seller_product_recycler.setHasFixedSize(true);
        seller_product_adapter=new ProductSellerProductListAdapter(this,seller_product_list);
        seller_product_layout_manager=new GridLayoutManager(this,1);
        seller_product_layout_manager.setOrientation(GridLayoutManager.HORIZONTAL);

        seller_product_recycler.setAdapter(seller_product_adapter);
        seller_product_recycler.setLayoutManager(seller_product_layout_manager);


        scrollView=findViewById(R.id.product_srollview);

        //버튼 초기화
        backbutton=findViewById(R.id.product_back);
        linearLayout=findViewById(R.id.product_linear1);
        favorit_button=findViewById(R.id.product_favorite);
        product_more=findViewById(R.id.product_more);
        title=findViewById(R.id.product_title);
        write_coment=findViewById(R.id.product_insert_coment);


        //제품의 상세 내용 데이터 로드

        /*
        * 더미 데이터
        * */


        for (int a=0;a<5;a++) {
            ProductComentItem item = new ProductComentItem();
            item.setProfile_image(R.drawable.test_chair);
            item.setId("stver8935");
            item.setComent("안녕하세요");
            item.setDate("2019-05-11");
            item.setLocation("대전광역시");
            item.setText("hello");
            item.setKey("awgaweg");
            coment_list.add(item);
            product_coment_adapter.notifyItemInserted(coment_list.size());
        }

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




        //관심 상품 등록 버튼 클릭 이벤트 처리 구현
        favorit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //로그인 안되어 있을떄 처리하기


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
                    title.setText("의자");
                }
                else {

                    linearLayout.setBackgroundResource(R.color.Nocolor);
                    title.setText("");
                }

                Log.d("스크롤 위치",""+scrollY);


                }
        });
        scrollView.scrollTo(0,100);

        //seller_product_list_recyclerview 테스트 코드
        for (int a=0; a<10;a++) {
            ProductSellerProductListItem item = new ProductSellerProductListItem();
            item.setProduct_title("아이폰");
            item.setProduct_price("1000원");

            Drawable drawable = getResources().getDrawable(R.drawable.test_chair);
            // drawable 타입을 bitmap으로 변경
            Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
            item.setProduct_image(bitmap);

            seller_product_list.add(item);
            seller_product_adapter.notifyItemInserted(a);
        }
    }


    //액티비티 생명주기 온 스타트 --데이터를 리셋후 데이터 로드 시키기
    //로딩 구현-


    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
