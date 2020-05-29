package com.example.carrot_market.CONTROLLER;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.MODEL.DTO.AddKeywordItem;
import com.example.carrot_market.MODEL.HttpConnect.InsertKeyWordTask;
import com.example.carrot_market.MODEL.HttpConnect.LoadKeyWordTask;
import com.example.carrot_market.MODEL.LOCALMODEL.SharedPreference.UserInfoSave;
import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.AddKeywordAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddKeyword extends AppCompatActivity {


    ArrayList<AddKeywordItem> arrayList=new ArrayList<>();
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    AddKeywordAdapter adapter;
    ImageButton back;
    Button keyword_insert_button;
    TextView keyword_count;
    EditText keyword_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_keyword);

        back=findViewById(R.id.add_keyword_back);
        keyword_text=findViewById(R.id.add_keyword_insert_text);
        keyword_insert_button=findViewById(R.id.add_keyword_insert_button);
        keyword_count=findViewById(R.id.add_keyword_count);


        recyclerView=findViewById(R.id.add_keyword_recycler);
        adapter=new AddKeywordAdapter(arrayList,this,keyword_count);

        gridLayoutManager=new GridLayoutManager(this,2);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);


        LoadKeyWordTask loadKeyWordTask=new LoadKeyWordTask(new UserInfoSave(AddKeyword.this).return_account().getId(),handler);
        Thread thread=new Thread(loadKeyWordTask);
        thread.start();

        //현재 액티비티 종료 버튼
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //키워드 입력버튼 이벤트 처리
        keyword_insert_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(keyword_text.getText().toString().equals("")) {
                    Toast.makeText(AddKeyword.this, "키워드를 입력해 주세요", Toast.LENGTH_SHORT).show();

                }else if (30<=arrayList.size()){
                    Toast.makeText(AddKeyword.this, "키워드를 30개 이상 등록 할수 없습니다.", Toast.LENGTH_SHORT).show();
                }else {

                    if (check_keyword(keyword_text.getText().toString())){
                        InsertKeyWordTask insertKeyWordTask=new InsertKeyWordTask(new UserInfoSave(AddKeyword.this).return_account().getId()
                                ,keyword_text.getText().toString(),handler);
                        Thread thread=new Thread(insertKeyWordTask);
                        thread.start();



                    }
                    else {

                        Toast.makeText(AddKeyword.this, "이미 추가된 키워드 입니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });




    }


//이미 추가된 키워드가 있는 체크하는 메서드
public boolean check_keyword(String insert_keyword){
        for (AddKeywordItem keyword : arrayList){
            if (keyword.getKeyword().equals(insert_keyword)){
                return false;
            }
        }


        return true;
}



protected Handler handler=new Handler(new Handler.Callback() {
    @Override
    public boolean handleMessage(@NonNull Message msg) {
        switch (msg.what){
            //키워드 입력 성공 했을때 받는 부분
            case 0:

                try {
                    JSONObject response_code=new JSONObject(msg.getData().getString("insert_key_word_result"));

                    if (response_code.getString("check").equals("sucessfully")){

                        AddKeywordItem item=new AddKeywordItem();
                        item.setKey(response_code.getString("key"));
                        item.setKeyword(response_code.getString("keyword"));
                        arrayList.add(item);

                        keyword_count.setText("("+arrayList.size()+"/30)");

                        adapter.notifyItemInserted(arrayList.size());

                        keyword_text.setText(null);

                    }else if (response_code.getString("check").equals("failed")){
                        Toast.makeText(AddKeyword.this, "키워드 입력에 실패 했습니다", Toast.LENGTH_SHORT).show();
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }




                break;

                //이전에 내가 키워드를 입력했던 키워드 로드 부분
            case 1:
                try {
                    JSONArray keyword_list_json=new JSONArray(msg.getData().getString("key_word_list"));
                    Log.e("keyword_list",keyword_list_json.toString());

                    for (int i=0;i<keyword_list_json.length();i++) {
                        JSONObject jsonObject = keyword_list_json.getJSONObject(i);

                        AddKeywordItem item=new AddKeywordItem();
                        item.setKey(jsonObject.getString("key"));
                        item.setKeyword(jsonObject.getString("keyword"));
                        arrayList.add(item);
                        keyword_count.setText("("+arrayList.size()+"/30)");

                        adapter.notifyItemInserted(arrayList.size());

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                break;

        }



        return false;
    }
});
}
