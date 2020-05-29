package com.example.carrot_market.CONTROLLER.Dialog;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.MODEL.DTO.CategoryDialogItem;
import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.CategoryDialogAdapter;

import java.util.ArrayList;

public class Category extends AppCompatActivity {




    private RecyclerView recyclerView;
    private CategoryDialogAdapter adapter;
    private ArrayList<CategoryDialogItem> arrayList= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

    recyclerView=findViewById(R.id.category_dialog_recycler);
    adapter=new CategoryDialogAdapter(arrayList);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(adapter);
        setting_category_list();

    }

    private void setting_category_list(){
        CategoryDialogItem item1=new CategoryDialogItem();
        CategoryDialogItem item2=new CategoryDialogItem();
        CategoryDialogItem item3=new CategoryDialogItem();
        CategoryDialogItem item4=new CategoryDialogItem();
        CategoryDialogItem item5=new CategoryDialogItem();
        CategoryDialogItem item6=new CategoryDialogItem();
        CategoryDialogItem item7=new CategoryDialogItem();
        CategoryDialogItem item8=new CategoryDialogItem();
        CategoryDialogItem item9=new CategoryDialogItem();
        CategoryDialogItem item0=new CategoryDialogItem();
        CategoryDialogItem item11=new CategoryDialogItem();
        CategoryDialogItem item12=new CategoryDialogItem();
        CategoryDialogItem item13=new CategoryDialogItem();
        CategoryDialogItem item14=new CategoryDialogItem();

        item1.setCategory("디지털/가전");
        item2.setCategory("가구/인테리어");
        item3.setCategory("유아동/유아도서");
        item4.setCategory("생활/가공식품");
        item5.setCategory("여성의류");
        item6.setCategory("여성잡화");
        item7.setCategory("뷰티/미용");
        item8.setCategory("남성패션/잡화");
        item9.setCategory("스포츠/레저");
        item0.setCategory("게임/취미");
        item11.setCategory("도서/티켓/음반");
        item12.setCategory("반려동물용품");
        item13.setCategory("기타 중고물품");
        item14.setCategory("삽니다");

        arrayList.add(item1);
        arrayList.add(item2);
        arrayList.add(item3);
        arrayList.add(item4);
        arrayList.add(item5);
        arrayList.add(item6);
        arrayList.add(item7);
        arrayList.add(item8);
        arrayList.add(item9);
        arrayList.add(item0);
        arrayList.add(item11);
        arrayList.add(item12);
        arrayList.add(item13);
        arrayList.add(item14);

        adapter.notifyDataSetChanged();

    }
}
