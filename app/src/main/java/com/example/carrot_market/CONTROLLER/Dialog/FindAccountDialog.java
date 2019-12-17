package com.example.carrot_market.CONTROLLER.Dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrot_market.MODEL.HttpConnect.FindAccountAuthonticationCodeTask;
import com.example.carrot_market.MODEL.HttpConnect.FindAccountTask;

import com.example.carrot_market.MODEL.HttpConnect.RETROFIT.RetrofitService;
import com.example.carrot_market.R;
import com.example.carrot_market.RecyclerView.Adapter.FindAccountAdapter;
import com.example.carrot_market.MODEL.DTO.FindAccountItem;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.carrot_market.CONTROLLER.SplashActivity.API_URL;

public class FindAccountDialog extends AppCompatActivity {

    private RecyclerView account_list_recycler;
    private FindAccountAdapter accountAdapter;
    private ArrayList<FindAccountItem> arrayList=new ArrayList<>();

    private EditText email,name,authontication_code;
    private Button authontication_button,find_account;

    private Retrofit retrofit;
    private RetrofitService retrofitService;
    private Call<ResponseBody> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_find_account);

        name=findViewById(R.id.find_account_name);
        email=findViewById(R.id.find_account_email);
        authontication_code=findViewById(R.id.find_account_authontication_num);

        authontication_button=findViewById(R.id.find_account_authontication_button);
        find_account=findViewById(R.id.find_account_button);

        account_list_recycler=findViewById(R.id.find_account_list_recycler);


        accountAdapter=new FindAccountAdapter(arrayList);
        account_list_recycler.setAdapter(accountAdapter);
        account_list_recycler.setLayoutManager(new LinearLayoutManager(this));



        retrofit=new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService=retrofit.create(RetrofitService.class);


        find_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FindAccountTask task=new FindAccountTask();
                String asd="null";
                try {
                   asd= task.execute(name.getText().toString(),email.getText().toString(),authontication_code.getText().toString()).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                try {
                    JSONArray jsonArray=new JSONArray(asd);

                    for (int a=0; a<jsonArray.length();a++){
                        FindAccountItem item=new FindAccountItem();
                        item.setId(jsonArray.getJSONObject(a).getString("id"));
                        item.setPassword(jsonArray.getJSONObject(a).getString("password"));
                        arrayList.add(item);
                        accountAdapter.notifyItemInserted(a);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Toast.makeText(FindAccountDialog.this, ""+asd, Toast.LENGTH_SHORT).show();

            }
        });

        authontication_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FindAccountAuthonticationCodeTask findaccount_task=new FindAccountAuthonticationCodeTask();
                String responscode= null;

                try {
                    responscode = findaccount_task.execute(name.getText().toString(),email.getText().toString(),authontication_code.getText().toString()).get();

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (responscode.equals("1")){
                    Toast.makeText(FindAccountDialog.this, "입력한 이메일로 코드를 전송했습니다.", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(FindAccountDialog.this, "입력한 정보에 맞는 계정이 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });




        

    }





}
