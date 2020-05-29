package com.example.carrot_market.CONTROLLER;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.carrot_market.MODEL.DTO.DealReviewItem;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;
import com.example.carrot_market.ViewPager.Adapter.SaleItemsAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class DealReviewDetail extends AppCompatActivity {


    public TabLayout tabLayout;
    private ViewPager viewPager;
    private SaleItemsAdapter adapter = new SaleItemsAdapter(getSupportFragmentManager());
    private ImageButton back;
    private DealReviewDetailFragment all_review ;
    private DealReviewDetailFragment seller_review ;
    private DealReviewDetailFragment buyer_review ;

    private ArrayList<DealReviewItem> deal_review_detail_array = new ArrayList<>();
    private UserInfoSave userInfoSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_review_detail);

        userInfoSave=new UserInfoSave(DealReviewDetail.this);

        tabLayout = findViewById(R.id.deal_review_detail_tab_layout);
        viewPager = findViewById(R.id.deal_review_detail_view_pager);
        tabLayout.setupWithViewPager(viewPager);






        all_review = new DealReviewDetailFragment(null,getIntent().getStringExtra("id"));
          seller_review = new DealReviewDetailFragment("seller",getIntent().getStringExtra("id"));
          buyer_review = new DealReviewDetailFragment("buyer",getIntent().getStringExtra("id"));
        pager_setting();

        back = findViewById(R.id.deal_review_detail_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        DealReviewLoadTask dealReviewLoadTask = new DealReviewLoadTask(null,userInfoSave.return_account().getId(),0, 10, handler);
//        Thread thread1 = new Thread(dealReviewLoadTask);
//        thread1.run();
        
    }

    private void pager_setting() {
        adapter.addFragment(all_review, "전체 후기", DealReviewDetail.this);
        adapter.addFragment(buyer_review, "판매자 후기", DealReviewDetail.this);
        adapter.addFragment(seller_review, "구매자 후기", DealReviewDetail.this);
        viewPager.setAdapter(adapter);

    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

//            switch (msg.what){
//                case 2:
//                Log.e("review",msg.getData().getString("review"));
//
//                JSONArray review_json_array= null;
//                try {
//                    review_json_array = new JSONArray(msg.getData().getString("review"));
//
//
//                for (int i=1;i<review_json_array.length();i++){
//                    JSONObject deal_review_obj=review_json_array.getJSONObject(i);
//                    DealReviewItem dealReviewItem=new DealReviewItem();
//                    dealReviewItem.setUser_type(deal_review_obj.getString("user_type"));
//                    dealReviewItem.setId(deal_review_obj.getString("id"));
//                    dealReviewItem.setComent(deal_review_obj.getString("coment"));
//                    dealReviewItem.setProduct_image(deal_review_obj.getString("review_image_path"));
//                    DateTimeConverter dateTimeConverter=new DateTimeConverter(deal_review_obj.getString("time_stamp"));
//                    dealReviewItem.setDate(""+dateTimeConverter.return_month_day());
//                    dealReviewItem.setProfile_image(deal_review_obj.getString("profile_image_path"));
//                    dealReviewItem.setAddress(deal_review_obj.getString("address"));
//                    dealReviewItem.setLocation(deal_review_obj.getString("location"));
//                    dealReviewItem.setLat(deal_review_obj.getDouble("lat"));
//                    dealReviewItem.setLng(deal_review_obj.getDouble("lng"));
//                    all_review.arrayList.add(dealReviewItem);
//                    all_review.adapter.notifyItemInserted(i);
//                    if (deal_review_obj.getString("user_type").equals("seller")){
//                     seller_review.arrayList.add(dealReviewItem);
//                     seller_review.adapter.notifyItemInserted(seller_review.arrayList.size()-1);
//                    }
//                    else if (deal_review_obj.getString("user_type").equals("buyer")){
//                        buyer_review.arrayList.add(dealReviewItem);
//                        Log.e("buyer_list",""+buyer_review.adapter);
//                        buyer_review.adapter.notifyItemInserted(buyer_review.arrayList.size()-1);
//                    }
//
//                }
//
//                if (0<all_review.arrayList.size()) {
//                    all_review.review_count.setText("후기 " + review_json_array.getJSONObject(0).getString("all_review_count")+ "개");
//                }
//                if (0<seller_review.arrayList.size()) {
//                    seller_review.review_count.setText("후기 "+review_json_array.getJSONObject(0).getString("seller_review_count") + "개");
//                }
//                if (0<buyer_review.arrayList.size()) {
//                    buyer_review.review_count.setText("후기 " + review_json_array.getJSONObject(0).getString("buyer_review_count")+ "개");
//                }
//                } catch (JSONException | ParseException e) {
//                    e.printStackTrace();
//                }
//                break;
//            }




            return false;
        }
    });
}