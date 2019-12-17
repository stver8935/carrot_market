package com.example.carrot_market.ViewPager.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeNotifySelectionPageAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragment_list=new ArrayList<>();
    private final List<String> fragment_title_list=new ArrayList<>();

    public HomeNotifySelectionPageAdapter(@NonNull FragmentManager fm) {
        super(fm);

    }


    public void addFragment(Fragment fragment,String title){
        fragment_list.add(fragment);
        fragment_title_list.add(title);
    }

public String getPageTitle(int position){
return fragment_title_list.get(position);
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
