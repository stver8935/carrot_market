package com.example.carrot_market.ViewPager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.carrot_market.R;

public class ProductViewPagerAdapter extends PagerAdapter {
    Context context;
    int product_image_count=3;
    public ProductViewPagerAdapter(Context context,int product_image_count) {
        this.product_image_count=product_image_count;
        this.context = context;
    }


    @Override
    public int getCount() {
        return product_image_count;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
    View view=null;
    if (context!=null){
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.product_view_pager_item,container,false);
    }

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    //    super.destroyItem(container, position, object);
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {


        return (view == object);
    }
}
