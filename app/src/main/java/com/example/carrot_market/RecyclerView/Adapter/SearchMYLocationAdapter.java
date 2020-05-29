package com.example.carrot_market.RecyclerView.Adapter;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.CONTROLLER.SearchMyLocation;
import com.example.carrot_market.MODEL.DTO.AddressItem;
import com.example.carrot_market.MODEL.HttpConnect.MyLocationInsertTask;
import com.example.carrot_market.MODEL.HttpConnect.MyLocationUpdateTask;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SearchMYLocationAdapter extends RecyclerView.Adapter<SearchMYLocationAdapter.CustomViewHolder> {
    ArrayList<AddressItem> SearchList;
    private Geocoder geocoder;
    private Context context;
    private Handler handler=new Handler();

    String[] address;
    List<Address> list;
    boolean button_update;
    String before_address;
    UserInfoSave userInfoSave;


    public SearchMYLocationAdapter(ArrayList<AddressItem> searchList,boolean button_date,String before_address) {
        this.SearchList = searchList;
        this.button_update=button_date;
        this.before_address=before_address;
    }


    @NonNull
    @Override
    public SearchMYLocationAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        geocoder=new Geocoder(parent.getContext());
        context=parent.getContext();

        userInfoSave=new UserInfoSave(context);

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_my_location_item,parent,false);
        SearchMYLocationAdapter.CustomViewHolder holder=new SearchMYLocationAdapter.CustomViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchMYLocationAdapter.CustomViewHolder holder, int i) {

        holder.address.setText(SearchList.get(i).getAddress());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                try {
                    list= geocoder.getFromLocationName(SearchList.get(holder.getAdapterPosition()).getAddress(),10);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                address=SearchList.get(holder.getAdapterPosition()).getAddress().split("[\\(\\)]");
                SearchList.get(holder.getAdapterPosition()).setAddress(address[0]);
                address=address[1].split(",");

                SearchList.get(holder.getAdapterPosition()).setLat(list.get(0).getLatitude());
                SearchList.get(holder.getAdapterPosition()).setLng(list.get(0).getLongitude());
                SearchList.get(holder.getAdapterPosition()).setLocation(address[0]);



                //지역 삭제 버튼을 눌렀을때 지역을 업데이트 하는것인지 삭제하는것인지 나누는 조건 문
                if (button_update) {
                    MyLocationUpdateTask task=new MyLocationUpdateTask();

                    JSONArray after_jsonArray=new JSONArray();
                    JSONObject after_jsonobj=new JSONObject();

                    after_jsonobj=SearchList.get(holder.getAdapterPosition()).return_jsonobject();
                    after_jsonArray.put(after_jsonobj);
                    Log.e("json",after_jsonArray.toString());

                    try {
                        JSONObject before_jsonobj=new JSONObject(before_address);
                        JSONArray before_jsonarray=new JSONArray();
                        before_jsonarray.put(before_jsonobj);

                        Log.e("update array",""+before_address+"//"+before_jsonarray);

                        String responscode=task.execute(before_jsonarray.toString(),after_jsonArray.toString(),userInfoSave.return_account().getId()).get();

                        Log.e("code",responscode);

                        if (responscode.equals("1"))
                        {
                            ((SearchMyLocation)context).finish();
                        }
                        else {
                            Toast.makeText(context, "잠시후 다시 시도해 주세요=insert"+responscode, Toast.LENGTH_SHORT).show();
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }else {

                    String address=SearchList.get(holder.getAdapterPosition()).return_jsonobject().toString();
                    MyLocationInsertTask task=new MyLocationInsertTask();
                    String responscode= null;
                    Log.e("address",address);

                    try {
                        responscode = task.execute(address,userInfoSave.return_account().getId()).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.e("code",responscode);

                    if (responscode.equals("1")) {

                        ((SearchMyLocation)context).finish();
                    }
                    else {
                        Toast.makeText(context, "잠시후 다시 시도해 주세요insert"+responscode, Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return SearchList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView address;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
        this.address=itemView.findViewById(R.id.search_my_location_address_item);

        }
    }
}
