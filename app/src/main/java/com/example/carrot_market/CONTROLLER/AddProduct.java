package com.example.carrot_market.CONTROLLER;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asksira.bsimagepicker.BSImagePicker;
import com.bumptech.glide.Glide;
import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.AddProductAdapter;
import com.example.carrot_market.MODEL.DTO.AddProductItem;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;
import com.sangcomz.fishbun.define.Define;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddProduct extends AppCompatActivity implements BSImagePicker.OnSingleImageSelectedListener,
        BSImagePicker.OnMultiImageSelectedListener,BSImagePicker.ImageLoaderDelegate {

    ImageButton back,ducoment;
    EditText title,price,text;
    TextView category,location,camera;
    TextView commit;
    CheckBox price_suggestion;

    RecyclerView image_recycler_view;
    LinearLayoutManager linearLayoutManager;
    AddProductAdapter addProductAdapter;

    ArrayList<AddProductItem> arrayList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);





        back=findViewById(R.id.add_product_back);
        ducoment=findViewById(R.id.add_product_text_list);

        title=findViewById(R.id.add_product_insert_title);
        price=findViewById(R.id.add_product_insert_price);
        text=findViewById(R.id.add_product_insert_text);

        category=findViewById(R.id.add_product_insert_category);
        location=findViewById(R.id.add_product_insert_location);
        camera=findViewById(R.id.add_product_insert_picture);
        commit=findViewById(R.id.add_product_commit);

        price_suggestion=findViewById(R.id.add_product_price_suggestion);
        image_recycler_view=findViewById(R.id.add_product_image_recycler);


        image_recycler_view=findViewById(R.id.add_product_image_recycler);
        linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        addProductAdapter=new AddProductAdapter(this,arrayList,camera);

        image_recycler_view.setAdapter(addProductAdapter);
        image_recycler_view.setLayoutManager(linearLayoutManager);


        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddProduct.this, "text click", Toast.LENGTH_SHORT).show();
            }
        });

        //저장버튼
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //진짜로 저장할것인지 다이얼로그로 체크하기
            }
        });


        //종료 버튼
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //현재 작성중이었던 텍스트 저장
            }
        });

        //자주쓰는 문구 리스트
        ducoment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                

            }
        });

        //갤러리 호출 버튼
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (10<=arrayList.size()){
                    Toast.makeText(AddProduct.this, "사진을 더이상 선택 할 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    FishBun.with(AddProduct.this).setImageAdapter(new GlideAdapter()).setCamera(true).setMaxCount(10 - arrayList.size()).startAlbum();
                }

            }
        });


    }



    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //위에서 인텐트에 담아준 리캐스트 코트를 액티비티 리슐트에서 받아서 처리한다.
        switch (requestCode){
            case Define.ALBUM_REQUEST_CODE:
                if (resultCode== RESULT_OK){
                   ArrayList<Parcelable> path = data.getParcelableArrayListExtra(Define.INTENT_PATH);

                   for (int a=0; a<path.size();a++) {
                       Uri uri = (Uri) path.get(a);
                       AddProductItem item = new AddProductItem();
                       item.setImage(uri);
                       arrayList.add(item);
                       addProductAdapter.notifyItemInserted(arrayList.size());
                   }
                   camera.setText(arrayList.size()+"/"+"10");
                }
                break;
        }


            }

    @Override
    public void loadImage(File imageFile, ImageView ivImage) {
        Glide.with(AddProduct.this).load(imageFile).into(ivImage);
    }

    @Override
    public void onMultiImageSelected(List<Uri> uriList, String tag) {
        for (int a=0;a<uriList.size();a++){
            AddProductItem item=new AddProductItem();
            item.setImage(uriList.get(a));
            arrayList.add(item);
            addProductAdapter.notifyItemInserted(a);

            Log.e("imageuri",""+uriList.get(a));
        }


    }

    @Override
    public void onSingleImageSelected(Uri uri, String tag) {

    }
}

