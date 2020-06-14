package com.example.carrot_market.CONTROLLER;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.CONTROLLER.InterFace.Search;
import com.example.carrot_market.MODEL.DTO.HomeFragmentItem;
import com.example.carrot_market.MODEL.HttpConnect.SearchProductTask;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.HomeFragmentAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeSearchFragmentDeal extends Fragment implements Search {

     private LinearLayoutManager linearLayoutManager;
    private  RecyclerView recyclerView;
    private  HomeFragmentAdapter adapter;
    private  ProgressBar search_progress_bar;
    private ArrayList<HomeFragmentItem> arrayList=new ArrayList<>();
    private String search_word;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v=inflater.inflate(R.layout.activity_home_search_fragment_deal,container,false);
        //리사이클러 변수 초기화
        recyclerView=v.findViewById(R.id.home_search_deal_recyclerview);

        /*화면에 뿌려질 아이템의 구조를 정의하기 위해 레이아웃매니저 객체 생성*/
        //최근검색어
        linearLayoutManager=new LinearLayoutManager(v.getContext());
        search_progress_bar=v.findViewById(R.id.home_search_deal_progress_bar);

        //리사이클러뷰에 레이아웃 매니저 적용
        recyclerView.setLayoutManager(linearLayoutManager);

        //어댑터 생성자로 객체 생성하여 아이템리스트를 넣어줌
        adapter=new HomeFragmentAdapter(arrayList);
//        tagAdapter=new TagAdapter(arrayList_tag,v.getContext());

        //리사이클러뷰에 어댑터 적용
        recyclerView.setAdapter(adapter);




        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = adapter.getItemCount();
                int lastVisibleItemPosition =((LinearLayoutManager)recyclerView.getLayoutManager()).findLastVisibleItemPosition();

                if(10<=totalItemCount&&(totalItemCount-1)<=lastVisibleItemPosition){
                    SearchProductTask searchProductTask=new SearchProductTask(handler,new UserInfoSave(getContext()).return_account().getId(),search_word,""+(arrayList.size()+1));
                    Thread thread=new Thread(searchProductTask);
                    thread.start();
                }else{

                }
            }
        });

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }




    final Handler handler = new Handler(new Handler.Callback() {
        @Override

        public boolean handleMessage(@NonNull Message msg) {

            switch (msg.what) {
                case 0:
                    try {


                        JSONArray search_product_list_json_array = new JSONArray(msg.getData().getString("search_product_list"));

                        for (int i =0; i<search_product_list_json_array.length();i++) {
                            JSONObject search_product_json_obj=search_product_list_json_array.getJSONObject(i);
                            HomeFragmentItem item=new HomeFragmentItem();
                            item.setProduct_key(Integer.parseInt(search_product_json_obj.getString("product_key")));
                            item.setTitle(search_product_json_obj.getString("title"));
                            item.setPrice(Integer.parseInt(search_product_json_obj.getString("price")));
                            item.setLocation(search_product_json_obj.getString("location"));
                            item.setSales_completed(search_product_json_obj.getString("sales_completed"));
                            item.setFavorite_count(Integer.parseInt(search_product_json_obj.getString("favorite_count")));
                            item.setChatting_count(Integer.parseInt(search_product_json_obj.getString("chatting_room_count")));
                            item.setComnet_count(Integer.parseInt(search_product_json_obj.getString("coment_count")));
                            item.setImage_path(search_product_json_obj.getString("image_path"));
                            item.setDate(search_product_json_obj.getString("days"));
                            arrayList.add(item);
                          adapter.notifyItemInserted(arrayList.size()-1);

                        }
                        search_progress_bar.setVisibility(View.GONE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    break;
                case 1:

                    break;

                default:


            }


            return false;
        }
    });


    @Override
    public void search_deal(String content) {
        adapter.notifyItemRangeRemoved(0,arrayList.size());
        arrayList.clear();

        search_progress_bar.setVisibility(View.VISIBLE);
        search_word=content;
        SearchProductTask searchProductTask=new SearchProductTask(handler,new UserInfoSave(getContext()).return_account().getId(),content,"0");
        Thread thread=new Thread(searchProductTask);
        thread.start();
    }
}
