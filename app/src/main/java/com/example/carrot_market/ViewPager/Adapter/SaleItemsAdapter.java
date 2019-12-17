package com.example.carrot_market.ViewPager.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class SaleItemsAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> fragment_list=new ArrayList<>();
    ArrayList<String> fragment_title_list=new ArrayList<>();

    public void addFragment(Fragment fragment,String title){
        fragment_list.add(fragment);
        fragment_title_list.add(title);
    }

    public Fragment getFragment(int fragment_count) {
        return fragment_list.get(fragment_count);
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
        return fragment_list.get(position);
    }

    @Override
    public int getCount() {
        return fragment_list.size();
    }
}
