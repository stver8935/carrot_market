package com.example.carrot_market.RecyclerView.Adapter;

import android.content.Context;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.R;
import com.example.carrot_market.MODEL.DTO.HomeSearchFragmentItem;

import java.util.ArrayList;

import static com.example.carrot_market.R.id.home_fragment_recyclerview_item_text;

public class HomeSearchFragmentAdapter extends RecyclerView.Adapter<HomeSearchFragmentAdapter.CustomViewHolder> {


    private ArrayList<HomeSearchFragmentItem> arrayList;
    protected Context context;
    private long  mLastClickTime=0;


    public HomeSearchFragmentAdapter(ArrayList<HomeSearchFragmentItem> arrayList,Context context) {
        this.arrayList = arrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public HomeSearchFragmentAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_search_deal_recycler_item,parent,false);

        HomeSearchFragmentAdapter.CustomViewHolder holder=new HomeSearchFragmentAdapter.CustomViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeSearchFragmentAdapter.CustomViewHolder holder,int position) {



        holder.searchword.setText(arrayList.get(position).getSearch_word());


        //아이템의 삭제를 위한 클릭 리스너
        holder.wordclerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 300){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

               removeitem(holder.getAdapterPosition());

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }




    public void removeitem(int position){


            arrayList.remove(position);
            notifyItemRemoved(position);
        }



    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView searchword;
        private ImageView wordclerbutton;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            searchword=itemView.findViewById(R.id.home_fragment_recyclerview_item_text);
            wordclerbutton=itemView.findViewById(R.id.home_fragment_recyclerview_item_clear);
        }
    }
}
