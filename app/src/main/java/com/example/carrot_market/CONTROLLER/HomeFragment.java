package com.example.carrot_market.CONTROLLER;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.MODEL.DTO.HomeFragmentItem;
import com.example.carrot_market.MODEL.HttpConnect.MyLocationLoadTask;
import com.example.carrot_market.MODEL.HttpConnect.ProductActivityInfoTask;
import com.example.carrot_market.MODEL.HttpConnect.RETROFIT.ProductDownloadTask;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.HomeFragmentAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class HomeFragment extends Fragment implements View.OnClickListener {



     final int CATEGORY_SETTING_RESULT_CODE=0;
     final int LOCATION_SETTING_RESULT_CODE=1;
     final int PRODUCT_DETAILE_ACTIVITY_RESULT_CODE=2;
     LoadingDialog loadingDialog;



     HomeFragmentAdapter adapter;
     RecyclerView recyclerView;
     LinearLayoutManager linearLayoutManager;
     ArrayList<HomeFragmentItem>  arrayList=new ArrayList<>();




     TextView select_my_location;
     ImageButton search_button,tune_button,notification_button;
    Context context;
  boolean test=true;
  boolean data_load=true;

    private UserInfoSave UserInfo;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //home fragment에서 사용될 버튼 변수 초기화
        context=getContext();
        UserInfo=new UserInfoSave(getContext());


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_home_fragment,container,false);

        //리사이클러 변수 초기화
        recyclerView=v.findViewById(R.id.home_fragment_recyclerview);


        //화면에 뿌려질 아이템의 구조를 정의하기 위해 레이아웃매니저 객체 생성
        linearLayoutManager=new LinearLayoutManager(v.getContext());

        //리사이클러뷰에 레이아웃 매니저 적용
        recyclerView.setLayoutManager(linearLayoutManager);


        //어댑터 생성자로 객체 생성하여 아이템리스트를 넣어줌
        adapter=new HomeFragmentAdapter(arrayList);

        //리사이클러뷰에 어댑터 적용
        recyclerView.setAdapter(adapter);




        //HomeFragment 에서 사용할 버튼 초기화 부분
        select_my_location=v.findViewById(R.id.home_fragment_mylocation);
        search_button=v.findViewById(R.id.home_fragment_search);

        tune_button=v.findViewById(R.id.home_fragment_tune);
        notification_button=v.findViewById(R.id.home_fragment_notificaiton);


        select_my_location.setOnClickListener(this);
        search_button.setOnClickListener(this);
        tune_button.setOnClickListener(this);
        notification_button.setOnClickListener(this);






        //상품 리스트 페이징 처리
        //상품 리스트의 끝의 -1 개수 만큼의 위치에 도달 했을때 다른 상품 리스트 10개 로드
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

                if((totalItemCount-1)==lastVisibleItemPosition){
                    if (data_load){
                    ProductDownloadTask downloadTask=new ProductDownloadTask(handler,context,UserInfo.return_account().getId(),arrayList.size(),null,null);
                    Thread thread = new Thread(downloadTask);
                    thread.run();
                    data_load=false;
                    }
                }
            }
        });

        //초반 상품 리스트 10개 로드

        adapter.notifyItemRangeRemoved(0,arrayList.size());
        arrayList.clear();
        loadingDialog=new LoadingDialog(getContext(),"상품 정보 로드 중 입니다");
        loadingDialog.setCancelable(false);
        loadingDialog.show();
        ProductDownloadTask downloadTask=new ProductDownloadTask(handler,context,UserInfo.return_account().getId(),0,null,null);
        Thread thread = new Thread(downloadTask);
        thread.run();



        return v;
    }

    @Override
    public void onClick(final View v) {
        Intent intent;
        switch (v.getId())
        {
//-------------------------------------------------------------------------------------현재 위치 지정 버튼 이벤트 처리 시작
            //나의 위치를 지정하는 버튼을 눌렀을시 이벤트 처리하는 부분
            case R.id.home_fragment_mylocation:

                if (test) {
                    Drawable img = context.getResources().getDrawable(R.drawable.keyboard_arrow_up);
                    img.setBounds(0, 0, 90, 90);
                    select_my_location.setCompoundDrawables(null, null, img, null);
                    test=false;


                    //select_my_location_menu 라는 메뉴를 확장 시켜서 보여준다
                    PopupMenu mylocation_menu=new PopupMenu(getActivity(),v);
                    UserInfoSave address_menu=new UserInfoSave(context);

                    //서버에서 데이터 바로 로드

                    MyLocationLoadTask task=new MyLocationLoadTask();


                    JSONArray jsonArray= null;
                    try {
                        jsonArray = new JSONArray(task.execute(UserInfo.return_account().getId()).get());
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    for (int i=0;i<jsonArray.length();i++) {
                        try {
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            mylocation_menu.getMenu().add(0, i, 0, jsonObject.getString("location"));
//                            mylocation_menu.getMenu().getItem("myselect_location_num").setTitle()

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    MenuInflater inflater=getActivity().getMenuInflater();
                    //아이템 추가 골격

                    inflater.inflate(R.menu.select_my_location_menu, mylocation_menu.getMenu());

                //나의 현재 위치를 지정하는 팝업 이벤트 처리 부분
                mylocation_menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId())
                        {

                            case R.id.my_location_setting:

                                Drawable img = context.getResources().getDrawable(R.drawable.keyboard_arrow_down);
                                img.setBounds(0, 0, 90, 90);
                                select_my_location.setCompoundDrawables(null, null, img, null);
                                test=true;
                                Intent intent=new Intent(context,MyLocationSetting.class);
                                intent.putExtra("title","내 동네 설정");
                                intent.putExtra("product",false);
                                startActivityForResult(intent,LOCATION_SETTING_RESULT_CODE);
                                break;

                                //첫번째주소
                            case 0:
                                select_my_location.setText(item.getTitle());
                                SpannableString s=new SpannableString(item.getTitle());
                                s.setSpan(new ForegroundColorSpan(Color.RED),0,s.length(),0);
                                item.setTitle(s);
                                break;
                            //두번째 주송
                            case 1:
                                select_my_location.setText(item.getTitle());
                                select_my_location.setText(item.getTitle());
                                select_my_location.setTextColor(Color.parseColor("#999999"));
                                break;

                        }


                        return false;

                    }
                });

                //mylocation_menu 가 선택 되지 않았을때 실행되는 리스너
                    mylocation_menu.setOnDismissListener(new PopupMenu.OnDismissListener() {
                        @Override
                        public void onDismiss(PopupMenu menu) {
                            Drawable img1 = context.getResources().getDrawable(R.drawable.keyboard_arrow_down);
                            img1.setBounds(0, 0, 90, 90);
                            select_my_location.setCompoundDrawables(null, null, img1, null);
                            test=true;
                        }
                    });


                mylocation_menu.show();



                }
                else {
                    Drawable img = context.getResources().getDrawable(R.drawable.keyboard_arrow_down);
                    img.setBounds(0, 0, 90, 90);
                    select_my_location.setCompoundDrawables(null, null, img, null);
                    test=true;
                }

                break;
//-------------------------------------------------------------------------------------현재 위치 지정 버튼 이벤트 처리 끝
            //검색을 위한 액티비티로 이동
            case R.id.home_fragment_search:

                 intent=new Intent(context, HomeSearch.class);
                startActivity(intent);


                break;



                //상품 리스트에 보여질 카테고리를 세팅하는 액티비티로 이동
            case R.id.home_fragment_tune:
                 intent=new Intent(context,AttentionCategory.class);
                startActivityForResult(intent,CATEGORY_SETTING_RESULT_CODE);
                break;


            case R.id.home_fragment_notificaiton:
                intent=new Intent(context, MainNotification.class);
                startActivity(intent);
                break;


        }




    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    // 현재 액티비티에 호출하는 액티비티에서 설정하는 값이 현재 액티비티의 데이터에 영향을 주는 경우 반환 값을 받기 위한 onActivityforresult
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("onactivity_result","aa"+resultCode);
        //위치나 카테고리 세팅후 다시 돌아올때

        switch (requestCode){

            case CATEGORY_SETTING_RESULT_CODE:
                if (resultCode==1){
                        loadingDialog=new LoadingDialog(context,"상품 로드 중");
                        loadingDialog.setCancelable(false);
                        loadingDialog.show();

                    Log.e("onactivity_result","cayegory_update");
                    adapter.notifyItemRangeRemoved(0,arrayList.size());
                    arrayList.clear();
                    ProductDownloadTask downloadTask=new ProductDownloadTask(handler,context,UserInfo.return_account().getId(),0,null,null);
                    Thread thread = new Thread(downloadTask);
                    thread.run();
                }
                break;

            case LOCATION_SETTING_RESULT_CODE:
                if (resultCode==1){

                    loadingDialog=new LoadingDialog(context,"상품 로드 중");
                    loadingDialog.setCancelable(false);
                    loadingDialog.show();

                    Log.e("onactivity_result","location_update");
                    adapter.notifyItemRangeRemoved(0,arrayList.size());
                    arrayList.clear();
                    ProductDownloadTask downloadTask=new ProductDownloadTask(handler,context,UserInfo.return_account().getId(),0,null,null);
                    Thread thread = new Thread(downloadTask);
                    thread.run();
                }
                break;

            case PRODUCT_DETAILE_ACTIVITY_RESULT_CODE:
                if (resultCode==1){
                    loadingDialog=new LoadingDialog(context,"상품 로드 중");
                    loadingDialog.setCancelable(false);
                    loadingDialog.show();

                ProductActivityInfoTask productActivityInfoTask=new ProductActivityInfoTask(data.getStringExtra("product_key"),handler);
                Thread thread=new Thread(productActivityInfoTask);
                thread.start();
                }
                break;
        }







    }


    Handler handler=new Handler(){
    @Override
   public void handleMessage(Message message){


        switch (message.what){
            case 0:

                if (loadingDialog!=null) {
                    loadingDialog.dismiss();
                }

                try {
//                    adapter.notifyItemRangeRemoved(0,arrayList.size());
//                    arrayList.clear();
                    Log.e("product_error_chek",""+message.getData().getString("product_list"));
                    JSONArray jsonArray=new JSONArray(message.getData().getString("product_list"));

                    for (int i=0;i<jsonArray.length();i++){
                    HomeFragmentItem item=new HomeFragmentItem();

                    item.setSales_completed(jsonArray.getJSONObject(i).getString("sales_completed"));
                    item.setId(jsonArray.getJSONObject(i).getString("id"));
                    item.setProduct_key(Integer.parseInt(jsonArray.getJSONObject(i).getString("product_key")));
                        Log.e("itme_key",""+jsonArray.getJSONObject(i).getString("product_key"));
                    item.setTitle(jsonArray.getJSONObject(i).getString("title"));
                    item.setPrice(jsonArray.getJSONObject(i).getInt("price"));
                    item.setChatting_count(jsonArray.getJSONObject(i).getInt("chat_room_count"));
                    item.setFavorite_count(jsonArray.getJSONObject(i).getInt("favorite_count"));
                    item.setComnet_count(jsonArray.getJSONObject(i).getInt("product_coment_count"));
                    item.setDate(jsonArray.getJSONObject(i).getString("date"));
                    item.setLocation(jsonArray.getJSONObject(i).getString("location"));
                    item.setImage_path(jsonArray.getJSONObject(i).getString("main_image"));
                    arrayList.add(item);
                    adapter.notifyItemInserted(arrayList.size()-1);
                    }

                    if (10<arrayList.size()) {
                        data_load = true;
                    }else {
                        data_load=false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

                //상품 상세정보에서 좋아요나 채팅창 생성 댓글을 달았을때 호출 되는 작업
            case 1:
                Log.e("product_update","update");
                loadingDialog.dismiss();
                try {
                    JSONObject product_activity_info_json=new JSONObject(message.getData().getString("product_activity_info"));
                for (int i=0;i<arrayList.size();i++){
                if (arrayList.get(i).getProduct_key()==product_activity_info_json.getInt("product_key")){
                 arrayList.get(i).setComnet_count(product_activity_info_json.getInt("coment_count"));
                 arrayList.get(i).setChatting_count(product_activity_info_json.getInt("chatting_room_count"));
                 arrayList.get(i).setFavorite_count(product_activity_info_json.getInt("favorite_count"));
                 adapter.notifyDataSetChanged();
                }
                }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
        }
};




}
