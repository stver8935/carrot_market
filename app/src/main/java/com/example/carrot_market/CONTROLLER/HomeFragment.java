package com.example.carrot_market.CONTROLLER;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;


import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.HomeFragmentAdapter;
import com.example.carrot_market.MODEL.DTO.HomeFragmentItem;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements View.OnClickListener {

     HomeFragmentAdapter adapter;
     RecyclerView recyclerView;
     LinearLayoutManager linearLayoutManager;
     ArrayList<HomeFragmentItem>  arrayList=new ArrayList<>();

     TextView select_my_location;
     ImageButton search_button,tune_button,notification_button;
    Context context;
  boolean test=true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //home fragment에서 사용될 버튼 변수 초기화

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
        adapter=new HomeFragmentAdapter(v.getContext(),arrayList);

        //리사이클러뷰에 어댑터 적용
        recyclerView.setAdapter(adapter);




        context=v.getContext();

        //HomeFragment 에서 사용할 버튼 초기화 부분
        select_my_location=v.findViewById(R.id.home_fragment_mylocation);
        search_button=v.findViewById(R.id.home_fragment_search);

        tune_button=v.findViewById(R.id.home_fragment_tune);
        notification_button=v.findViewById(R.id.home_fragment_notification);

        select_my_location.setOnClickListener(this);
        search_button.setOnClickListener(this);
        tune_button.setOnClickListener(this);
        notification_button.setOnClickListener(this);


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();


        //Recyclerview test code
        for (int a=0;a<10;a++) {
            HomeFragmentItem item = new HomeFragmentItem();
            Drawable drawable = getResources().getDrawable(R.drawable.test_chair);

            // drawable 타입을 bitmap으로 변경
            Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();


            item.setImage(bitmap);
            item.setMatch("10,000원");
            item.setTitle("의자");
            item.setPosition("사당동");
            arrayList.add(item);
            adapter.notifyItemInserted(a);
        }
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
                    MenuInflater inflater=getActivity().getMenuInflater();
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
                                startActivity(intent);
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
                Toast.makeText(context, "?????", Toast.LENGTH_SHORT).show();
                 intent=new Intent(context, HomeSearch.class);
                startActivity(intent);

                break;


            case R.id.home_fragment_tune:
                 intent=new Intent(context,AttentionCategory.class);
                startActivity(intent);
                break;
            case R.id.home_fragment_notification:
                 intent=new Intent(context,MainNotification.class);
                startActivity(intent);
                break;


        }

    }




}
