package com.example.carrot_market.CONTROLLER;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.DealReviewAdapter;
import com.example.carrot_market.RecyclerView.Adapter.DealReviewDetailAdapter;
import com.example.carrot_market.MODEL.DTO.DealReviewDetailItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class DealReviewDetailFragment extends Fragment {

    private RecyclerView deal_review_recycerview;
    private TextView review_count;
    private DealReviewDetailAdapter adapter;
    private ArrayList<DealReviewDetailItem> arrayList=new ArrayList<>();
    private Context context;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v=inflater.inflate(R.layout.deal_review_deatail_fragment,container,false);

    context=getContext();

        deal_review_recycerview=v.findViewById(R.id.deal_review_detail_fragment_recycler);
        adapter=new DealReviewDetailAdapter(arrayList,context);


        //스크롤 뷰안에 뷰들이 있으므로 리사이클러뷰 세로 스크롤 제거
        deal_review_recycerview.setLayoutManager(new LinearLayoutManager(context){
            @Override
            public boolean canScrollVertically(){
              return false;
            }
        });


        deal_review_recycerview.setAdapter(adapter);

        review_count=v.findViewById(R.id.deal_review_detail_fragment_review_count);


        for (int a=0;a<10;a++){
            DealReviewDetailItem item=new DealReviewDetailItem();
            item.setId("stver8935");
            item.setDate("5일전");
            item.setLocation("뱅배동");
            item.setProfile_image(R.drawable.profile_image_man);
            arrayList.add(item);
            adapter.notifyItemInserted(a);
        }


        //부모 액티비티의 탭 레이아웃 리스너를 구현
        //여기서 모든데이터 받아오기

        ((DealReviewDetail)getActivity()).tabLayout.setOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:

                        break;

                     case 1:

                        break;

                     case 2:

                        break;

                        default:
                            Toast.makeText(getContext(), "잠시후 다시 시도해 주세요", Toast.LENGTH_SHORT).show();
                            break;
                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }
}
