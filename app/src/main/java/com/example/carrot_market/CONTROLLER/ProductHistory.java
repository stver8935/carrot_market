package com.example.carrot_market.CONTROLLER;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.carrot_market.CONTROLLER.Dialog.ProductHistoryDialog;
import com.example.carrot_market.MODEL.DTO.HomeFragmentItem;
import com.example.carrot_market.MODEL.HttpConnect.RETROFIT.RetrofitService;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;
import com.example.carrot_market.ViewPager.Adapter.HomeSearchSectionPageAdapter;
import com.google.android.material.tabs.TabLayout;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class ProductHistory extends AppCompatActivity implements ProductHistoryDialog.changetext {
    private ViewPager viewPager;
    private ImageView product_history_back;

    HomeSearchSectionPageAdapter adapter=new HomeSearchSectionPageAdapter(getSupportFragmentManager());
    ProductHistoryFragment ing_fragmnet;
    ProductHistoryFragment commit_fragment;
    ProductHistoryFragment hidden_fragment;

    UserInfoSave userInfoSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_history);

        userInfoSave=new UserInfoSave(this);
        ing_fragmnet=new ProductHistoryFragment("0","0");
        commit_fragment=new ProductHistoryFragment("1","0");
        hidden_fragment=new ProductHistoryFragment(null,"1");

        product_history_back=findViewById(R.id.product_history_back);

        viewPager=findViewById(R.id.product_history_viewpager);
        setViewPager(viewPager);
        TabLayout tabLayout=findViewById(R.id.product_history_tab);
        tabLayout.setupWithViewPager(viewPager);


        product_history_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }




    private void setViewPager(ViewPager viewPager){

        adapter.addFragment(ing_fragmnet, "판매중");
        adapter.addFragment(commit_fragment, "거래완료");
        adapter.addFragment(hidden_fragment, "숨김");
        viewPager.setAdapter(adapter);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ing_fragmnet.onActivityResult(requestCode,resultCode,data);
        commit_fragment.onActivityResult(requestCode,resultCode,data);
        hidden_fragment.onActivityResult(requestCode,resultCode,data);

    }

    //다이얼로그 콜백 메서드
    @Override
    public void changetext(String fragment_type, String product_key, String work_type) {
        //어느프래그 먼트에서 온 데이터 인지 확인

        Log.e("cheange_text","fragment_type="+fragment_type+"  product_key="+product_key+"  work_type="+work_type);

        //프래그먼트 타입으로부터 워크타입에 따라 데이터 서버에 전송하는데
        //리스폰스를 받았다면 실행하기기
       switch (fragment_type){

            case "ing":
                dialog_work(work_type,product_key,ing_fragmnet);
                // 거래완료

                break;

                case "commit":
                    dialog_work(work_type,product_key,commit_fragment);
                    //거래후기
                break;


                case "hidden":
                    dialog_work(work_type,product_key,hidden_fragment);
                //숨기기 해제
                    break;
        }
    }
    public void dialog_work(String work_type, String product_key, final ProductHistoryFragment productHistoryFragment){
       Retrofit retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
       RetrofitService retrofitService=retrofit.create(RetrofitService.class);


        final String tmepproduct_key=product_key;

       Log.e("worktype",work_type+"///"+product_key);

        switch (work_type){

            case "commit":

                retrofitService.product_salescompleted(userInfoSave.return_account().getId(),product_key,1).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        for (int i=0;i<productHistoryFragment.arrayList.size();i++){
                            if (tmepproduct_key.equals(""+productHistoryFragment.arrayList.get(i).getProduct_key())){
                                productHistoryFragment.arrayList.get(i).setHidden("0");
                                productHistoryFragment.arrayList.get(i).setSales_completed("1");
                                commit_fragment.arrayList.add(productHistoryFragment.arrayList.get(i));
                                commit_fragment.adapter.notifyItemInserted(ing_fragmnet.arrayList.size()-1);

                                productHistoryFragment.adapter.notifyItemRemoved(i);
                                productHistoryFragment.arrayList.remove(i);

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(ProductHistory.this, "실패"+t, Toast.LENGTH_SHORT).show();
                    }
                });
                //거래후기
                break;
            case "ing":
                retrofitService.product_salescompleted(userInfoSave.return_account().getId(),product_key,0).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        for (int i=0;i<productHistoryFragment.arrayList.size();i++){
                            if (tmepproduct_key.equals(""+productHistoryFragment.arrayList.get(i).getProduct_key())){
                                productHistoryFragment.arrayList.get(i).setHidden("0");
                                productHistoryFragment.arrayList.get(i).setSales_completed("0");
                                ing_fragmnet.arrayList.add(productHistoryFragment.arrayList.get(i));
                                ing_fragmnet.adapter.notifyItemInserted(ing_fragmnet.arrayList.size()-1);

                                productHistoryFragment.adapter.notifyItemRemoved(i);
                                productHistoryFragment.arrayList.remove(i);

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

                break;
                //-0000000000000000000000000000000000000000
            case "hidden":

                retrofitService.product_hidden(userInfoSave.return_account().getId(),product_key,"1").enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        for (int i=0;i<productHistoryFragment.arrayList.size();i++){
                            if (tmepproduct_key.equals(""+productHistoryFragment.arrayList.get(i).getProduct_key())){
                                productHistoryFragment.arrayList.get(i).setHidden("1");

                                if (hidden_fragment.arrayList!=null) {
                                    hidden_fragment.arrayList.add(productHistoryFragment.arrayList.get(i));
                                    hidden_fragment.adapter.notifyItemInserted(hidden_fragment.arrayList.size() - 1);
                                }

                                productHistoryFragment.adapter.notifyItemRemoved(i);
                                productHistoryFragment.arrayList.remove(i);

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

                break;

                //히든 해제
            case "hidden_unlock":
                retrofitService.product_hidden(userInfoSave.return_account().getId(),product_key,"0").enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        for (int i=0;i<productHistoryFragment.arrayList.size();i++){
                            if (tmepproduct_key.equals(""+productHistoryFragment.arrayList.get(i).getProduct_key())){
                                productHistoryFragment.arrayList.get(i).setHidden("0");



                                if (productHistoryFragment.arrayList.get(i).getSales_completed().equals("0")){
                                    if (ing_fragmnet.arrayList!=null) {
                                        ing_fragmnet.arrayList.add(productHistoryFragment.arrayList.get(i));
                                        ing_fragmnet.adapter.notifyItemInserted(hidden_fragment.arrayList.size() - 1);
                                    }
                                }else if (productHistoryFragment.arrayList.get(i).getSales_completed().equals("1")){
                                    if (commit_fragment.arrayList!=null) {
                                        commit_fragment.arrayList.add(productHistoryFragment.arrayList.get(i));
                                        commit_fragment.adapter.notifyItemInserted(hidden_fragment.arrayList.size() - 1);
                                    }
                                }
                                productHistoryFragment.adapter.notifyItemRemoved(i);
                                productHistoryFragment.arrayList.remove(i);

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
                break;
            case "modify":
              //수정 액티비티
            Intent intent=new Intent(ProductHistory.this,AddProduct.class);
            intent.putExtra("product_key",product_key);
            startActivity(intent);

                break;
            case "delete":

                retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
                retrofitService=retrofit.create(RetrofitService.class);

                retrofitService.product_delete(userInfoSave.return_account().getId(),product_key).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        for (int i=0;i<productHistoryFragment.arrayList.size();i++){
                            if (tmepproduct_key.equals(""+productHistoryFragment.arrayList.get(i).getProduct_key())){
                                productHistoryFragment.adapter.notifyItemRemoved(i);
                                productHistoryFragment.arrayList.remove(i);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

                break;
            case "pull_up":

                retrofitService.product_pull_up(product_key).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        for (int i=0;i<productHistoryFragment.arrayList.size();i++){
                            if (tmepproduct_key.equals(""+productHistoryFragment.arrayList.get(i).getProduct_key())){
                                HomeFragmentItem temp_item=productHistoryFragment.arrayList.get(i);
                                productHistoryFragment.arrayList.remove(i);
                                productHistoryFragment.adapter.notifyItemRemoved(i);

                                productHistoryFragment.arrayList.add(0,temp_item);
                                productHistoryFragment.adapter.notifyItemInserted(0);

                            }
                        }
                        Toast.makeText(ProductHistory.this, "상품을 최신화 하셨습니다", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

                break;
            case "deal_review_leave":


//                Intent intent=new Intent(context, SelectBuyer.class);
//                intent.putExtra("product_key",""+product_key);
//                intent.putExtra("image_path",);
//                intent.putExtra("title",title);
//                startActivity(intent);


                break;
        }
    }

}
