package com.example.carrot_market.ViewPager.Adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class SaleItemsAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> fragment_list=new ArrayList<>();
    ArrayList<String> fragment_title_list=new ArrayList<>();
    Context context;

    public void addFragment(Fragment fragment,String title,Context context){
        fragment_list.add(fragment);
        fragment_title_list.add(title);
        this.context=context;
    }

    @Override
    public void finishUpdate(@NonNull ViewGroup container) {
        super.finishUpdate(container);
    }

    //탭 레이아웃의 클릭한 타이틀의 문자열을 반환하는 메서드
    public CharSequence getPageTitle(int position){
        return fragment_title_list.get(position);
    }

    public SaleItemsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
//        Bundle bundle = new Bundle(1); // 파라미터는 전달할 데이터 개수
//        bundle.putString("title", ""+position); // key , value
//        fragment_list.get(position).setArguments(bundle);

        return fragment_list.get(position);
    }

    @Override
    public int getCount() {
        return 3;
    }

}
