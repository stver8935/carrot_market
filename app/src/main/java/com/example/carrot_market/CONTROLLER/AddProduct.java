package com.example.carrot_market.CONTROLLER;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.asksira.bsimagepicker.BSImagePicker;
import com.bumptech.glide.Glide;
import com.example.carrot_market.CONTROLLER.Dialog.Category;
import com.example.carrot_market.MODEL.DTO.AddProductImageItem;
import com.example.carrot_market.MODEL.DTO.AddProductItem;
import com.example.carrot_market.MODEL.HttpConnect.ProductDetaileDownloadTask;
import com.example.carrot_market.MODEL.HttpConnect.ProductEditTask;
import com.example.carrot_market.MODEL.HttpConnect.ProductUpLoadTask;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.AddProductAdapter;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;
import com.sangcomz.fishbun.define.Define;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class AddProduct extends AppCompatActivity implements BSImagePicker.OnSingleImageSelectedListener,
        BSImagePicker.OnMultiImageSelectedListener,BSImagePicker.ImageLoaderDelegate {

    private int ADD_PRODUCT_LOCATION_SETTING_CODE=1;

    private ImageButton back,ducoment;
    private EditText title,price,text;
    private TextView category,location,camera;
    private TextView commit;
    private CheckBox price_suggestion;
    private boolean product_edit_check=false;
    private RecyclerView image_recycler_view;
    private LinearLayoutManager linearLayoutManager;
    private AddProductAdapter addProductAdapter;
    private int locaton_range;

    ProgressDialog progressDialog;
    private ArrayList<AddProductImageItem> arrayList=new ArrayList<>();

    //로그인시 저장해 두었던 사용자의 아이디나 비밀번호 정보
    private UserInfoSave userInfoSave;
    String product_key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        progressDialog=new ProgressDialog(AddProduct.this);
        progressDialog.setCancelable(false);


        userInfoSave=new UserInfoSave(AddProduct.this);

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



            if (getIntent().getStringExtra("product_key")!=null){

                product_key=getIntent().getStringExtra("product_key");
                Log.e("edit_key",product_key);
                product_edit_check=true;
                ProductDetaileDownloadTask productDetaileDownloadTask=new ProductDetaileDownloadTask(getIntent().getStringExtra("product_key"),userInfoSave.return_account().getId(),"0","0",handler);
                Thread thread=new Thread(productDetaileDownloadTask);
                thread.run();

            }

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent=new Intent(AddProduct.this, Category.class);
                startActivityForResult(intent,1);
            }
        });

        //리슐트에서 받아야 되는데 겟 인텐트로 받아 버림..
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddProduct.this,MyLocationSetting.class);
                intent.putExtra("title","게시글 보여줄 동네 고르기");
                intent.putExtra("product",true);
                startActivityForResult(intent,2);

            }
        });

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

            if (title.getText().toString().equals(""))
            {
                Toast.makeText(AddProduct.this, "제목을 입력해 주세요", Toast.LENGTH_SHORT).show();
            }else if (category.getText().toString().equals("")) {
                Toast.makeText(AddProduct.this, "카테고리를 선택해 주세요", Toast.LENGTH_SHORT).show();
            }else if(location.getText().toString().equals("")) {
                Toast.makeText(AddProduct.this, "보여줄 동네 반경을 선택해 주세요", Toast.LENGTH_SHORT).show();
            }else if(price.getText().toString().equals("")&&!price_suggestion.isChecked()){
                Toast.makeText(AddProduct.this, "가격을 입력하거나 가격제안 받기를 선택해 주세요", Toast.LENGTH_SHORT).show();
            }else if (text.getText().toString().equals("")){
                Toast.makeText(AddProduct.this, "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
            }else {
                try {
                    if (product_edit_check) {
                        edit();
                    }else {

                        upload();
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            }

        });


        //종료 버튼
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Log.e("check",""+product_edit_check);


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
                       AddProductImageItem item = new AddProductImageItem();
                       item.setImage(uri.toString());
                       arrayList.add(item);
                       addProductAdapter.notifyItemInserted(arrayList.size());
                   }
                   camera.setText(arrayList.size()+"/"+"10");

                }
                break;

            case 1:

                if (data!=null){
                category.setText(data.getStringExtra("category_title"));
                    Log.e("이건 되냐?","ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ카테");
                    Toast.makeText(this, ""+data.getStringExtra("category_title"), Toast.LENGTH_SHORT).show();
                }else{

                }
                break;

            case 2:
                if (data!=null){
                    location.setText(data.getStringExtra("add_product_location_info"));
                     locaton_range=data.getIntExtra("add_prodct_location_range",77);
                    String location_name=data.getStringExtra("add_product_location");
                    location.setText(location_name+"반경"+locaton_range+"km");
                    Log.e("이건 되냐?","ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ");

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
            AddProductImageItem item=new AddProductImageItem();
            item.setImage(uriList.get(a).toString());
            arrayList.add(item);
            addProductAdapter.notifyItemInserted(a);

        }


    }

    @Override
    public void onSingleImageSelected(Uri uri, String tag) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        int price_int=0;

        if (!price.getText().toString().equals("")) {
            price_int = Integer.parseInt(price.getText().toString());
        }

        UserInfoSave user=new UserInfoSave(AddProduct.this);
        JSONObject jsonObject=new JSONObject();
        try {
        jsonObject.put("title",title.getText().toString());
        jsonObject.put("category",category.getText().toString());
        jsonObject.put("price",price_int);
        jsonObject.put("deal",price_suggestion.isChecked());
        jsonObject.put("text",text.getText().toString());
        jsonObject.put("location",location.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        user.add_product_save(jsonObject,arrayList);

    }


    //AddProductitem에 jsonobj를 반환해주는 메서드가 있으므로 데이터를 세팅해준뒤 데이터 저장 task에 매개변수에 JSONobject를 리턴해준다.
    //이미지의 절대 경로값반환하여  JSON Array 형태로 만들어 저장task의 매개변수에 넣어준다
    //


    public void edit() throws JSONException {
        progressDialog.setMessage("수정중 입니다");
        progressDialog.show();

        final JSONArray jsonArray=new JSONArray();

        AddProductItem addProductItem=new AddProductItem();
        addProductItem.setCategory(category.getText().toString());
        addProductItem.setDeal(price_suggestion.isChecked());
        addProductItem.setPrice(Integer.parseInt(price.getText().toString()));
        addProductItem.setText(text.getText().toString());
        addProductItem.setTitle(title.getText().toString());

        ProductEditTask productEditTask=new ProductEditTask(addProductItem,arrayList,userInfoSave.return_account().getId(),product_key,handler,AddProduct.this);
        Thread thread=new Thread(productEditTask);
        thread.start();
        }



    public void upload() throws JSONException {
        progressDialog.setMessage("업로드 중 입니다");
        progressDialog.show();
        JSONArray jsonArray=new JSONArray();

        for (int i =0; i<arrayList.size();i++) {


            Cursor c = AddProduct.this.getContentResolver().query(Uri.parse(arrayList.get(i).getImage()), null, null, null, null);
            c.moveToFirst();
            String path = c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA));
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("image_path",path);
            jsonArray.put(jsonObject);
        }

        AddProductItem item=new AddProductItem();
        item.setCategory(category.getText().toString());
        item.setTitle(title.getText().toString());
        item.setPrice(Integer.parseInt(price.getText().toString()));
        item.setDeal(price_suggestion.isChecked());
        item.setText(text.getText().toString());
        item.setRange(locaton_range);

        ProductUpLoadTask task=new ProductUpLoadTask(AddProduct.this);

        task.execute(userInfoSave.return_account().getId(),item.return_jsonobj().toString(),jsonArray.toString());
        progressDialog.dismiss();
        finish();
    }






//수정시 기존의 품목 데이터로드를 위해 사용되는 핸들러
Handler handler=new Handler(new Handler.Callback() {
    @Override
    public boolean handleMessage(@NonNull Message msg) {

   switch (msg.what){
       case 0:

           Log.e("produt_edit",""+msg.getData().getString("product_detaile_list"));
           JSONObject jsonObject= null;
           try {
               jsonObject = new JSONObject(msg.getData().getString("product_detaile_list"));

               title.setText(jsonObject.getString("title"));
               price.setText(jsonObject.getString("price"));
               category.setText(jsonObject.getString("category"));
               text.setText(jsonObject.getString("description"));
               if (jsonObject.getString("deal").equals("0")){
                   price_suggestion.setChecked(false);
               }else if (jsonObject.getString("deal").equals("1")){
                   price_suggestion.setChecked(true);

               }
           JSONArray jsonArray=new JSONArray(jsonObject.getString("image_path_list"));
           for (int i=0;i<jsonArray.length();i++){
               jsonArray.getJSONObject(i).getString("image_path");
               AddProductImageItem item=new AddProductImageItem();
               item.setImage(API_URL+"image/"+jsonArray.getJSONObject(i).getString("image_path"));
               arrayList.add(item);
               addProductAdapter.notifyItemInserted(arrayList.size()-1);
           }
           } catch (JSONException e) {
               e.printStackTrace();
           }





           break;
       case 10:
           Toast.makeText(AddProduct.this, "수정 완료", Toast.LENGTH_SHORT).show();
           progressDialog.dismiss();
           finish();
           break;
       case 11:
           Toast.makeText(AddProduct.this, "업로드 완료", Toast.LENGTH_SHORT).show();
           progressDialog.dismiss();
           finish();
           break;
   }






        return false;
    }
});

}

