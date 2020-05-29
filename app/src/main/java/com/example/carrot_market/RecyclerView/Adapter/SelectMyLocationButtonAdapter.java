package com.example.carrot_market.RecyclerView.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.CONTROLLER.SearchMyLocation;
import com.example.carrot_market.MODEL.DTO.AddressItem;
import com.example.carrot_market.MODEL.HttpConnect.MyAddressUpdateTask;
import com.example.carrot_market.MODEL.HttpConnect.MyLocationDeleteTask;
import com.example.carrot_market.MODEL.HttpConnect.MyLocationRangeTask;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;

import org.json.JSONArray;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class SelectMyLocationButtonAdapter extends RecyclerView.Adapter<SelectMyLocationButtonAdapter.CustomVIewHolder> {

    private ArrayList<AddressItem> arrayList;
    private Context context;
    public static int adpater_position=0;
    private TextView location_info;
    private SeekBar location_range;
    private ImageView location_range_image;
    private String rpcode;
    private UserInfoSave userInfo;



    public SelectMyLocationButtonAdapter(ArrayList<AddressItem> arrayList, TextView info, SeekBar location_range, ImageView location_range_image) {
        this.arrayList = arrayList;
        this.location_info=info;
        this.location_range=location_range;
        this.location_range_image=location_range_image;
    }


    @NonNull
    @Override
    public SelectMyLocationButtonAdapter.CustomVIewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.select_my_location_button_item,parent,false);
        SelectMyLocationButtonAdapter.CustomVIewHolder holder=new SelectMyLocationButtonAdapter.CustomVIewHolder(v);
        context=parent.getContext();
        userInfo=new UserInfoSave(context);

        return holder;
    }

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final SelectMyLocationButtonAdapter.CustomVIewHolder holder, int position) {


        //어댑터 내부에서 고쳐야한다 외부로의 adapter position 값을 반환 못하기 때문에씨발발
       //adpater_position=holder.getAdapterPosition();


        holder.location_button.setText(arrayList.get(holder.getAdapterPosition()).getLocation());


        if (holder.location_button.getText().toString().equals("")){
            holder.location_button.setVisibility(View.GONE);
            holder.location_button_clear.setVisibility(View.GONE);
            holder.location_button_add.setVisibility(View.VISIBLE);
        }else {
            holder.location_button.setVisibility(View.VISIBLE);
            holder.location_button_clear.setVisibility(View.VISIBLE);
            holder.location_button_add.setVisibility(View.GONE);
        }




        Class<R.drawable> drawable = R.drawable.class;
        Field field = null;
        int r;

            if (arrayList.get(holder.getAdapterPosition()).isSelect()) {
                try {
                    field = drawable.getField("border_line_fill_orange");
                    r = field.getInt(null);
                    holder.itemView.setBackgroundResource(r);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                range_image_update(arrayList.get(holder.getAdapterPosition()).getRange());
                location_info.setText(arrayList.get(holder.getAdapterPosition()).getLocation()+"반경"+arrayList.get(holder.getAdapterPosition()).getRange()+"km");
            } else {
                try {
                    field = drawable.getField("border_line");
                    r = field.getInt(null);
                    holder.itemView.setBackgroundResource(r);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }


        //범위 선택 식바의 클릭 이벤트 처리
        location_range.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int range=0;

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


                MyLocationRangeTask task=new MyLocationRangeTask();
                if (seekBar.getProgress()<25){
                    seekBar.setProgress(0,true);
                    location_range_image.setImageResource(R.drawable.setting_map);
                    range=5;
                }else if(25 <= seekBar.getProgress()&&seekBar.getProgress()<50){
                    seekBar.setProgress(33,true);

                    location_range_image.setImageResource(R.drawable.setting_map1);
                    range=10;
                }else if(50<=seekBar.getProgress()&&seekBar.getProgress()<75){
                    seekBar.setProgress(66,true);

                    location_range_image.setImageResource(R.drawable.setting_map2);
                    range=15;
                }else {
                    seekBar.setProgress(100,true);
                    location_range_image.setImageResource(R.drawable.setting_map3);
                    range=20;
                }

                //선택한 범위로 바꾸기 어댑터 포지션이 아니라
                for (int i =0 ; i< arrayList.size();i++){
                    if (arrayList.get(i).isSelect()){
                        String rpcode = null;

                        location_info.setText(arrayList.get(i).getLocation()+"반경"+range+"km");

                        try {
                            String String_range= String.valueOf(range);
                            rpcode=task.execute(userInfo.return_account().getId(),String_range).get();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (rpcode.equals("1")) {
                            arrayList.get(i).setRange(range);
                        }else {
                            Toast.makeText(context, "잠시후 다시 시도해 주세요1", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        });



        holder.location_button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                adpater_position=holder.getAdapterPosition();

//                Toast.makeText(context, ""+holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                range_image_update(arrayList.get(holder.getAdapterPosition()).getRange());

                int other_position=arrayList.size()-holder.getAdapterPosition()-1;

                MyAddressUpdateTask task=new MyAddressUpdateTask();
                Log.e("select", ""+arrayList.get(holder.getAdapterPosition()).isSelect()+"//"+arrayList.get(other_position).isSelect());

                arrayList.get(holder.getAdapterPosition()).setSelect(true);
                arrayList.get(other_position).setSelect(false);



                JSONArray jsonArray=new JSONArray();
                jsonArray.put(arrayList.get(holder.getAdapterPosition()).return_jsonobject());
                jsonArray.put(arrayList.get(other_position).return_jsonobject());

                try {
                    //업데이트인데 이전 주소가 없어서..
                    //
                    String codee=task.execute(userInfo.return_account().getId(),jsonArray.toString()).get();


                    Log.e("code",codee+arrayList.get(holder.getAdapterPosition()).getRange());
                    if (codee.equals("1"))
                    {
                        range_image_update(arrayList.get(holder.getAdapterPosition()).getRange());
                    }else {
                        Toast.makeText(context, "잠시후 다시 시도해 주세요", Toast.LENGTH_SHORT).show();
                    }

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                notifyDataSetChanged();

            }
        });



        holder.location_button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, SearchMyLocation.class);
                context.startActivity(intent);

                //로케이션 세팅
            }
        });

        holder.location_button_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(context);
                    dialogbuilder.setMessage("동네는 최소 1개이상 선택되어있어야 합니다 현재 설정된 동네를 변경하시겠습니까?");
                    dialogbuilder.setNegativeButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(context, SearchMyLocation.class);
                            intent.putExtra("button_update", true);
                            intent.putExtra("before_address", ""+arrayList.get(holder.getAdapterPosition()).return_jsonobject());
                            context.startActivity(intent);
                        }
                    });

                    dialogbuilder.setPositiveButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });



                    switch (holder.getAdapterPosition()){
                        case 0:
                            if (arrayList.get(1).getAddress().equals("")){
                                dialogbuilder.show();
                            }else {
                                MyLocationDeleteTask task=new MyLocationDeleteTask();
                                try {
                                    rpcode=task.execute(userInfo.return_account().getId(),arrayList.get(holder.getAdapterPosition()).getAddress()).get();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                if (rpcode.equals("1")){
                                AddressItem item=new AddressItem();
                                arrayList.set(0,arrayList.get(1));
                                arrayList.set(1,item);
                                }else {
                                    Toast.makeText(context, "잠시후 다시 시도해 주세요", Toast.LENGTH_SHORT).show();
                                }
                            }

                            break;

                            case 1:
                                MyLocationDeleteTask task = new MyLocationDeleteTask();

                                try {
                                    rpcode=task.execute(userInfo.return_account().getId(),arrayList.get(holder.getAdapterPosition()).getAddress()).get();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if (rpcode.equals("1")) {
                                    AddressItem item = new AddressItem();
                                    arrayList.get(0).setSelect(true);
                                    arrayList.set(1, item);
                                }else {
                                    Toast.makeText(context, "잠시후 다시 시도해 주세요(삭제 에러)", Toast.LENGTH_SHORT).show();
                                }
                            break;

                            default:
                                Log.e("데이터 삭제 오류","데이터 삭제 오류");
                                break;
                    }
                    notifyDataSetChanged();




            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }



    public class CustomVIewHolder extends RecyclerView.ViewHolder {
        TextView location_button;
        ImageButton location_button_clear;
        ImageButton location_button_add;

        public CustomVIewHolder(@NonNull View itemView) {
            super(itemView);

        location_button_add=itemView.findViewById(R.id.select_my_location_button_add);
        location_button=itemView.findViewById(R.id.select_my_location_button_title);
        location_button_clear=itemView.findViewById(R.id.select_my_location_button_clear);

        }

    }

    public void range_image_update(int range){

        switch (range){
            case 5:
                location_range.setProgress(0);
                location_range_image.setImageResource(R.drawable.setting_map);
                break;
            case 10:
                location_range.setProgress(33);
                location_range_image.setImageResource(R.drawable.setting_map1);
                break;
            case 15:

                location_range.setProgress(66);
                location_range_image.setImageResource(R.drawable.setting_map2);

                break;
            case 20:

                location_range.setProgress(100);
                location_range_image.setImageResource(R.drawable.setting_map3);

                break;
            default:
                break;
        }
    }
}
