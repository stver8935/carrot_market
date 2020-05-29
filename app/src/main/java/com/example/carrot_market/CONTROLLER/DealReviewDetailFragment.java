package com.example.carrot_market.CONTROLLER;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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

import com.example.carrot_market.CONTROLLER.METHOD.DateTimeConverter;
import com.example.carrot_market.MODEL.DTO.DealReviewItem;
import com.example.carrot_market.MODEL.HttpConnect.DealReviewLoadTask;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.DealReviewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

public class DealReviewDetailFragment extends Fragment {

    private RecyclerView deal_review_recycerview;
     TextView review_count;
     DealReviewAdapter adapter;
     ArrayList<DealReviewItem> arrayList=new ArrayList<>();
    private Context context;
    private String user_type;
    UserInfoSave userInfoSave;
    private String user_id;

    public DealReviewDetailFragment(String user_type,String user_id) {
       this.user_type=user_type;
       this.user_id=user_id;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userInfoSave=new UserInfoSave(getContext());


        DealReviewLoadTask dealReviewLoadTask = new DealReviewLoadTask(user_type,user_id,0, 10, handler);
        Thread thread1 = new Thread(dealReviewLoadTask);
        thread1.run();

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.deal_review_deatail_fragment,container,false);



        review_count=v.findViewById(R.id.deal_review_detail_fragment_review_count);
        context=getContext();
        deal_review_recycerview=v.findViewById(R.id.deal_review_detail_fragment_recycler);
        adapter=new DealReviewAdapter(arrayList,context);
        //스크롤 뷰안에 뷰들이 있으므로 리사이클러뷰 세로 스크롤 제거
        deal_review_recycerview.setLayoutManager(new LinearLayoutManager(context));

        if (savedInstanceState!=null) {
            review_count.setText(savedInstanceState.getString("review_count"));
        }

        Toast.makeText(context, "create_view", Toast.LENGTH_SHORT).show();
        if (getArguments()!=null) {
            review_count.setText(getArguments().getString("review_count"));
        }

        RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int itemTotalCount = recyclerView.getAdapter().getItemCount()-1;

                Log.e("scroll", "lastVisibled"+arrayList.size());
                if (lastVisibleItemPosition == itemTotalCount) {
                    DealReviewLoadTask dealReviewLoadTask = new DealReviewLoadTask(user_type,user_id,arrayList.size(), 10, handler);
                    Thread thread1 = new Thread(dealReviewLoadTask);
                    thread1.run();
                }

            }
        };
        deal_review_recycerview.setAdapter(adapter);
        deal_review_recycerview.addOnScrollListener(onScrollListener);


        //부모 액티비티의 탭 레이아웃 리스너를 구현
        //여기서 모든데이터 받아오기
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        outState.putString("review_count",review_count.getText().toString());
    }

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            JSONArray review_json_array= null;

            try {
                review_json_array = new JSONArray(msg.getData().getString("review"));
                review_count.setText("후기 "+review_json_array.getJSONObject(0).getString("review_count")+" 개");

                Bundle bundle=new Bundle();
                bundle.putString("review_count",review_count.getText().toString());
                setArguments(bundle);

            for (int i=1;i<review_json_array.length();i++){
                Log.e("deal_review_insert",""+review_json_array.getJSONObject(i));
                JSONObject deal_review_obj=review_json_array.getJSONObject(i);
                DealReviewItem dealReviewItem=new DealReviewItem();
                dealReviewItem.setUser_type(deal_review_obj.getString("user_type"));
                dealReviewItem.setId(deal_review_obj.getString("id"));
                dealReviewItem.setComent(deal_review_obj.getString("coment"));
                dealReviewItem.setProduct_image(deal_review_obj.getString("review_image_path"));
                DateTimeConverter dateTimeConverter=new DateTimeConverter(deal_review_obj.getString("time_stamp"));
                dealReviewItem.setDate(""+dateTimeConverter.return_month_day());
                dealReviewItem.setProfile_image(deal_review_obj.getString("profile_image_path"));
                dealReviewItem.setAddress(deal_review_obj.getString("address"));
                dealReviewItem.setLocation(deal_review_obj.getString("location"));
                dealReviewItem.setLat(deal_review_obj.getDouble("lat"));
                dealReviewItem.setLng(deal_review_obj.getDouble("lng"));
                arrayList.add(dealReviewItem);
                adapter.notifyItemInserted(arrayList.size()-1);

            }} catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return false;
        }
    });


}
