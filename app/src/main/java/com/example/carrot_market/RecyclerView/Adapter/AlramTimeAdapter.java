package com.example.carrot_market.RecyclerView.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.CONTROLLER.AlramTimeDialog;
import com.example.carrot_market.MODEL.DTO.AlramTimeItem;
import com.example.carrot_market.R;

import org.json.JSONException;

import java.util.ArrayList;

public class AlramTimeAdapter extends RecyclerView.Adapter<AlramTimeAdapter.CustomViewHolder> {
    private ArrayList<AlramTimeItem> arrayList;
    private AlramTimeAdapter.CustomViewHolder holder;
    public Context context;

    settext settext;
    TextView textView;

    public AlramTimeAdapter(ArrayList<AlramTimeItem> arrayList, TextView textView) {
        this.arrayList=arrayList;
        this.textView=textView;

    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alram_time,parent,false);
         holder=new AlramTimeAdapter.CustomViewHolder(v);
         context=parent.getContext();
         return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, int position) {
        holder.alram_time_text.setText(arrayList.get(holder.getAdapterPosition()).getTime_text());


    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            Intent intent=new Intent();

            try {
                intent.putExtra("alram_time_data",arrayList.get(holder.getAdapterPosition()).return_to_json().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ((AlramTimeDialog)context).setResult(1,intent);
            ((AlramTimeDialog)context).finish();

        }
    });


    }




    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView alram_time_text;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            alram_time_text=itemView.findViewById(R.id.itme_alram_time_text);

        }
    }

    public interface settext{
       void  settext(String aa);
    }


    public AlramTimeAdapter.CustomViewHolder return_holder(){
        return holder;
    }

}
