package com.example.carrot_market.CONTROLLER;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.carrot_market.R;

public class CategoeyList extends Fragment implements View.OnClickListener {
    private TextView popular,digital,interior,baby,life,womancloth,woman_ac,cosmetic,man,sports,game,
    car,jobs,real_estate,Agricultural_products,Introduction,study,show;
    ImageButton search,notification;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v=inflater.inflate(R.layout.activity_categoey_list,container,false);


       popular=v.findViewById(R.id.category_list_popular);
       digital=v.findViewById(R.id.category_list_digital);
       interior=v.findViewById(R.id.category_list_interior);
       baby=v.findViewById(R.id.category_list_baby);
       life=v.findViewById(R.id.category_list_life);
       womancloth=v.findViewById(R.id.category_list_woman_cloth);
       woman_ac=v.findViewById(R.id.category_list_woman_ac);
       cosmetic=v.findViewById(R.id.category_list_cosmetic);
       man=v.findViewById(R.id.category_list_man_ac);
       sports=v.findViewById(R.id.category_list_sports);
       game=v.findViewById(R.id.category_list_game);
       car=v.findViewById(R.id.category_list_car);
       jobs=v.findViewById(R.id.category_list_jobs);
       real_estate=v.findViewById(R.id.category_list_real_estate);
       Agricultural_products=v.findViewById(R.id.category_list_Agricultural_products);
        Introduction=v.findViewById(R.id.category_list_Introduction);
        study=v.findViewById(R.id.category_list_study);
        show=v.findViewById(R.id.category_list_show);


        search=v.findViewById(R.id.category_list_search);
        notification=v.findViewById(R.id.category_list_notification);
        btn_click_setting();
       return v;


    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()){

            case R.id.category_list_search:

                intent=new Intent(getContext(),HomeSearch.class);

                startActivity(intent);
                break;
            case R.id.category_list_notification:
                intent=new Intent(getContext(),ProductList.class);
                startActivity(intent);
                break;
            case R.id.category_list_popular:
                intent=new Intent(getContext(),ProductList.class);
                startActivity(intent);
                break;
            case R.id.category_list_digital:
                intent=new Intent(getContext(),ProductList.class);
                startActivity(intent);
                break;
            case R.id.category_list_interior:
                intent=new Intent(getContext(),ProductList.class);
                startActivity(intent);
                break;
            case R.id.category_list_baby:
                intent=new Intent(getContext(),ProductList.class);
                startActivity(intent);
                break;
            case R.id.category_list_life:
                intent=new Intent(getContext(),ProductList.class);
                startActivity(intent);
                break;
            case R.id.category_list_woman_cloth:
                intent=new Intent(getContext(),ProductList.class);
                startActivity(intent);
                break;
            case R.id.category_list_woman_ac:
                intent=new Intent(getContext(),ProductList.class);
                startActivity(intent);
                break;
            case R.id.category_list_cosmetic:
                intent=new Intent(getContext(),ProductList.class);
                startActivity(intent);
                break;
            case R.id.category_list_man_ac:
                intent=new Intent(getContext(),ProductList.class);
                startActivity(intent);
                break;
            case R.id.category_list_sports:
                intent=new Intent(getContext(),ProductList.class);
                startActivity(intent);
                break;
            case R.id.category_list_game:
                intent=new Intent(getContext(),ProductList.class);
                startActivity(intent);
                break;
            case R.id.category_list_car:
                intent=new Intent(getContext(),ProductList.class);
                startActivity(intent);
                break;
            case R.id.category_list_jobs:
                intent=new Intent(getContext(),ProductList.class);
                startActivity(intent);
                break;
            case R.id.category_list_real_estate:
                intent=new Intent(getContext(),ProductList.class);
                startActivity(intent);
                break;
            case R.id.category_list_Agricultural_products:
                intent=new Intent(getContext(),ProductList.class);
                startActivity(intent);
                break;
            case R.id.category_list_Introduction:
                intent=new Intent(getContext(),ProductList.class);
                startActivity(intent);
                break;
            case R.id.category_list_study:
                intent=new Intent(getContext(),ProductList.class);
                startActivity(intent);
                break;
            case R.id.category_list_show:
                intent=new Intent(getContext(),ProductList.class);
                startActivity(intent);
                break;


        }


    }

public void btn_click_setting(){
    popular.setOnClickListener(this);
    digital.setOnClickListener(this);
    interior.setOnClickListener(this);
    baby.setOnClickListener(this);
    life.setOnClickListener(this);
    womancloth.setOnClickListener(this);
    woman_ac.setOnClickListener(this);
    cosmetic.setOnClickListener(this);
    man.setOnClickListener(this);
    sports.setOnClickListener(this);
    game.setOnClickListener(this);
    car.setOnClickListener(this);
    jobs.setOnClickListener(this);
    real_estate.setOnClickListener(this);
    Agricultural_products.setOnClickListener(this);
    Introduction.setOnClickListener(this);
    study.setOnClickListener(this);
    show.setOnClickListener(this);
    search.setOnClickListener(this);
    notification.setOnClickListener(this);
}

}
