package com.example.carrot_market.RecyclerView.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.CONTROLLER.ProductComent;
import com.example.carrot_market.CONTROLLER.ProfileDetail;
import com.example.carrot_market.R;
import com.example.carrot_market.MODEL.DTO.ProductComentItem;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductComentAdapter extends RecyclerView.Adapter<ProductComentAdapter.CustomViewHolder> {

    private ArrayList<ProductComentItem> arrayList;
     Context context;
     EditText coment;
     Boolean product_coment_and_coment_list;

    public ProductComentAdapter(ArrayList<ProductComentItem> arrayList,Context context,EditText coment) {
        product_coment_and_coment_list=true;
        this.arrayList = arrayList;
        this.context=context;
        this.coment=coment;
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

        holder.profile_image.setImageResource(arrayList.get(i).getProfile_image());
        holder.id.setText(arrayList.get(i).getId());
        holder.coment.setText(arrayList.get(i).getComent());
        holder.location.setText(arrayList.get(i).getLocation());
        holder.date.setText(arrayList.get(i).getDate());


        //대댓글 일시 패딩 레프트 10~20 dp 추가
        //holder.itemView.setPadding();


        //대댓글 달기 버튼 이벤트 처리
        holder.reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,ProductComent.class);
                intent.putExtra("key","reply");
                context.startActivity(intent);
            }
        });



        //댓글 수정 버튼 클릭 이벤트 처리
        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (product_coment_and_coment_list) {
                    PopupMenu coment_setting_menu = new PopupMenu(context, holder.more);
                    coment_setting_menu.inflate(R.menu.coment_menu);
                    coment_setting_menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            //댓글 보기에서의 댓글 더보기 이벤트 처리
                            if (!holder.id.getText().equals("stver8935")) {
                                Toast.makeText(context, "작성자만 수정 할 수 있습니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                switch (item.getItemId()) {
                                    case R.id.coment_setting:
                                        coment.setText(holder.coment.getText());
                                        //데이터베스에 수정
                                        break;
                                    case R.id.coment_delete:

                                        //데이터베스에서 삭제
                                        arrayList.remove(holder.getAdapterPosition());
                                        notifyItemRemoved(holder.getAdapterPosition());
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

                                        //댓글 신고
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
        private TextView id,location,date,reply,coment;
        private ImageView more;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
        profile_image=itemView.findViewById(R.id.product_coment_item_profile_image);
        id=itemView.findViewById(R.id.product_coment_item_id);
        location=itemView.findViewById(R.id.product_coment_item_location);
        date=itemView.findViewById(R.id.product_coment_item_date);
        reply=itemView.findViewById(R.id.product_coment_item_reply);
        more=itemView.findViewById(R.id.product_coment_item_more);
        coment=itemView.findViewById(R.id.product_coment_item_textarea);


        }
    }
}
