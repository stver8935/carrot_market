package com.example.carrot_market.ViewPager.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeSearchSectionPageAdapter extends FragmentPagerAdapter {

    //뷰페이저 내에서 프래그 먼트와 그에 맞는 탭 레이아웃 타이틀을 넣기위한 어레이리스트 객체 생성
    private final List<Fragment> fragment_list=new ArrayList<>();
    private final List<String> fragment_title_list=new ArrayList<>();



    //프래그먼트와 프레그먼트의 제목을 넣어주기위한 함수 구현
    public void addFragment(Fragment fragment,String title){
        fragment_list.add(fragment);
        fragment_title_list.add(title);
    }

    //탭 레이아웃의 클릭한 타이틀의 문자열을 반환하는 메서드
    public CharSequence getPageTitle(int position){
        return fragment_title_list.get(position);
    }

    public HomeSearchSectionPageAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    //클릭한 탭레이아웃의 타이틀에 맞는 프레그 먼트를 반환하기위한 메서드
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragment_list.get(position);
    }


    //뷰페이저의 아이템 갯수 지정 하는 메서드 현재는 FragmentPagerAdapter 이므로 즉 프래그먼트의 갯수
   @Override
    public int getCount() {
        return fragment_list.size();
    }
}
