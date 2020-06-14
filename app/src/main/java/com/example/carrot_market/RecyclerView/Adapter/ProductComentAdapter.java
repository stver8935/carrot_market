package com.example.carrot_market.RecyclerView.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.carrot_market.CONTROLLER.ProductComent;
import com.example.carrot_market.CONTROLLER.ProfileDetail;
import com.example.carrot_market.MODEL.DTO.ProductComentItem;
import com.example.carrot_market.MODEL.HttpConnect.ComentDeleteTask;
import com.example.carrot_market.MODEL.HttpConnect.ProductComentUpdateTask;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class ProductComentAdapter extends RecyclerView.Adapter<ProductComentAdapter.CustomViewHolder> {

    private ArrayList<ProductComentItem> arrayList;
     Context context;
     EditText coment;
     Boolean product_coment_and_coment_list;
    Handler handler;

    public ProductComentAdapter(ArrayList<ProductComentItem> arrayList,Context context,EditText coment,Handler handler) {
        product_coment_and_coment_list=true;
        this.arrayList = arrayList;
        this.context=context;
        this.coment=coment;
        this.handler=handler;
    }
    public ProductComentAdapter(ArrayList<ProductComentItem> arrayList,Context context) {
        product_coment_and_coment_list=false;
        this.arrayList = arrayList;
        this.context=context;
    }


    @NonNull
    @Override
    public ProductComentAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_coment_item,parent,false);
        ProductComentAdapter.CustomViewHolder holder=new ProductComentAdapter.CustomViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductComentAdapter.CustomViewHolder holder, final int i) {

        if (!arrayList.get(holder.getAdapterPosition()).getProfile_image_path().equals("null")) {
            Glide.with(context).load(API_URL + "/image/" + arrayList.get(holder.getAdapterPosition()).getProfile_image_path()).into(holder.profile_image);
        }else {
            holder.profile_image.setImageDrawable(context.getResources().getDrawable(R.drawable.profile_image_man));
        }

        holder.id.setText(arrayList.get(i).getId());
        holder.coment.setText(arrayList.get(i).getComent());
        holder.location.setText(arrayList.get(i).getLocation());
        holder.date.setText(arrayList.get(i).getDate());


        //대댓글 일시 패딩 레프트 10~20 dp 추가
        //holder.itemView.setPadding();





        //댓글 수정 버튼 클릭 이벤트 처리
        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (product_coment_and_coment_list) {
                    final PopupMenu coment_setting_menu = new PopupMenu(context, holder.more);
                    coment_setting_menu.inflate(R.menu.coment_menu);
                    coment_setting_menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            //댓글 보기에서의 댓글 더보기 이벤트 처리
                            if (!holder.id.getText().equals(new UserInfoSave(context).return_account().getId())) {
                                Toast.makeText(context, "작성자만 수정 할 수 있습니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                switch (item.getItemId()) {
                                    case R.id.coment_setting:
                                        coment.setText(holder.coment.getText());
                                        coment.requestFocus();

                                        coment.requestFocus();

                                        //키보드 보이게 하는 부분

                                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

                                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);


                                        ((ProductComent)context).write_commit.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                ProductComentUpdateTask productComentUpdateTask=new ProductComentUpdateTask(arrayList.get(holder.getAdapterPosition()).getKey(),coment.getText().toString(),handler);
                                                Thread thread=new Thread(productComentUpdateTask);
                                                thread.start();

                                            }
                                        });

                                        //데이터베스에 수정
                                        break;
                                    case R.id.coment_delete:


                                        Log.e("coment_key",arrayList.get(holder.getAdapterPosition()).getKey());

                                        ComentDeleteTask comentDeleteTask=new ComentDeleteTask(arrayList.get(holder.getAdapterPosition()).getKey(),handler);
                                        Thread thread=new Thread(comentDeleteTask);
                                        thread.run();

                                        //데이터베스에서 삭제
                                        break;


                                    default:
                                        return false;

                                }
                            }


                            return false;


                        }

                    });

                    coment_setting_menu.show();

                }else {
                    //제품보기에서의 댓글 아이템의 더보기 클릭 했을때의 이벤트 처리
                    PopupMenu coment_setting_menu = new PopupMenu(context, holder.more);
                    coment_setting_menu.inflate(R.menu.coment_menu_product);
                    coment_setting_menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                                switch (item.getItemId()) {
                                    case R.id.coment_see_profile:


                                        Intent intent=new Intent(context, ProfileDetail.class);
                                        intent.putExtra("id","댓글 작성자 아이디");
                                        context.startActivity(intent);

                                        //해당 댓글의 작성자 프로필로 이동
                                        break;
                                    case R.id.coment_declaration:
                                        Toast.makeText(context, "미구현 입니다.", Toast.LENGTH_SHORT).show();
                                        break;


                                    default:
                                        return false;

                                }



                            return false;


                        }

                    });

                    coment_setting_menu.show();

                }













            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView profile_image;
        private TextView id,location,date,coment;
        private ImageView more;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
        profile_image=itemView.findViewById(R.id.product_coment_item_profile_image);
        id=itemView.findViewById(R.id.product_coment_item_id);
        location=itemView.findViewById(R.id.product_coment_item_location);
        date=itemView.findViewById(R.id.product_coment_item_date);

        more=itemView.findViewById(R.id.product_coment_item_more);
        coment=itemView.findViewById(R.id.product_coment_item_textarea);


        }
    }




}
