package com.example.carrot_market.ViewPager.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.carrot_market.R;

import org.json.JSONArray;
import org.json.JSONException;

public class ProductViewPagerAdapter extends PagerAdapter {
    Context context;
    int product_image_count=3;

    //이미지경로 jsonarray 주기
    JSONArray jsonArray;
    public ProductViewPagerAdapter(Context context,int product_image_count,JSONArray jsonArray) {
        this.product_image_count=product_image_count;
        this.context = context;
//        this.image_index_array=image_index_array;
    this.jsonArray=jsonArray;
    }


    @Override
    public int getCount() {
        return product_image_count;
    }



    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
    View view=null;
    ImageView imageView = null;
    if (context!=null){
        
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.product_view_pager_item,container,false);
        imageView=view.findViewById(R.id.product_view_pager_item);
    }

        Log.e("pager",""+position);

        try {
            Glide.with(context).load("http://13.125.130.142/image/"+jsonArray.getJSONObject(position).getString("image_path")).into(imageView);
        } catch (JSONException e) {
            e.printStackTrace();
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
