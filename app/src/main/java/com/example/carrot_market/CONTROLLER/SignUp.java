package com.example.carrot_market.CONTROLLER;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carrot_market.MODEL.HttpConnect.IdCheckTask;
import com.example.carrot_market.MODEL.HttpConnect.RETROFIT.RetrofitService;
import com.example.carrot_market.MODEL.HttpConnect.SignUpTask;
import com.example.carrot_market.R;

import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;


public class SignUp extends AppCompatActivity {


    private EditText id,password,password_check,phone_num,email,name;
    private TextView id_check_text,password_text,password_check_text;
    private ImageView id_help,password_help;
    private Button id_overlap_check_button,sign_up_button;

    // 회원가입 정규식
    //영문 대소문자 숫자 포함
    private final String id_pattern="^[a-zA-Z0-9]*$";
    //특수문자 영문 대소문자 숫자 포함 9-자리에서 12자리
    private final String password_pattern="^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z])(?=.*[A-Z]).{9,12}$";

    private final String email_pattern="[0-9a-zA-Z]+(.[_a-z0-9-]+)*@(?:\\\\w+\\\\.)+\\\\w+$\n";

    //private final String phone_pattern="(\\\\d{3})(\\\\d{3,4})(\\\\d{4})"


    private boolean id_overlap_checkable=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        //회원가입시 데이터를 입력할 뷰 초기화
        id=findViewById(R.id.sign_up_id);
        password=findViewById(R.id.sign_up_password);
        password_check=findViewById(R.id.sign_up_password_check);
        phone_num=findViewById(R.id.sign_up_phone_num);
        email=findViewById(R.id.sign_up_email);
        name=findViewById(R.id.sign_up_name);


        //회원가입시 유효성 체크 텍스트뷰 초기화
        id_check_text=findViewById(R.id.sign_up_id_check_text);
        password_text=findViewById(R.id.sign_up_password_text);
        password_check_text=findViewById(R.id.sign_up_password_check_text);

        //회원가입시 사용자가 유효성을 학인 할수 있는 뷰 초기화
        id_help=findViewById(R.id.sign_up_id_help);
        password_help=findViewById(R.id.sign_up_password_help);

        //회원가입 및 아이디 중복확인 버튼 초기화

        id_overlap_check_button=findViewById(R.id.sign_up_id_check);
        sign_up_button=findViewById(R.id.sign_up_button);

        //http 통신을 위한 레트로핏 변수들 초기화



        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sign_up_check(id.getText().toString(),password.getText().toString(),password_check.getText().toString(),phone_num.getText().toString(),email.getText().toString())){

                }else {

                    SignUpTask tesk=new SignUpTask();
                    String Tost = null;

                    try {
                      Tost= tesk.execute(id.getText().toString(),
                              password.getText().toString(),name.getText().toString(),
                              phone_num.getText().toString(),email.getText().toString()).get();



                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (Tost.equals("hello")) {
                        Toast.makeText(SignUp.this, "가입되셨습니다", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(SignUp.this, "잠시후 다시 시도해 주세요"+Tost, Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        //id 입력 텍스트 변화 이벤트
        id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            if (id_check(s.toString())){
                id_check_text.setText("사용 가능한 아이디 입니다.");
                id_overlap_check_button.setClickable(true);
            }else {

                id_check_text.setText("사용이 불가능한 아이디 입니다.");
                id_overlap_check_button.setClickable(false);
            }

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (password_check(s.toString())){
                    password_text.setText("사용이 가능한 비밀번호입니다.");
                }
                else {
                    password_text.setText("사용이 불가능 비밀번호입니다.");
                }
            }
        });


        password_check.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (password_check_check(password.getText().toString(),s.toString())){
                    password_check_text.setText("비밀번호와 일치 합니다");
                }
                else {
                    password_check_text.setText("비밀번호와 일치하지 않습니다.");
                }
            }
        });





        id_overlap_check_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!id_overlap_check(id.getText().toString())){
                    id_check_text.setText("사용이 불가능한 아이디 입니다.");
                    id.requestFocus();
                    id_overlap_checkable=false;
                }else {
                    id_check_text.setText("사용 가능한 아이디 입니다.");
                    id_overlap_checkable=true;
                }

            }
        });


    }

    //입력한 아이디의 중복확인 메서드

    private boolean id_overlap_check(String id){
        boolean availability=false;
        String responsCode="";
        IdCheckTask idCheckTask=new IdCheckTask();

        try {
            responsCode=idCheckTask.execute(id).get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(!id.equals("")) {
            if (responsCode.equals("1")) {
                availability = false;
                Toast.makeText(this, "이미 가입된 아이디 입니다.", Toast.LENGTH_SHORT).show();
                this.id.hasFocus();

            } else if (responsCode.equals("0")) {
                availability = true;
                Toast.makeText(this, "사용 가능한 아이디 입니다.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "잠시후 다시 시도해 주세요", Toast.LENGTH_SHORT).show();
                availability = false;
            }
        }
        else {
            availability=false;
            Toast.makeText(this, "아이디를 입력해 주세요", Toast.LENGTH_SHORT).show();
            this.id.requestFocus();
        }
        return availability;
    }


    //아이디 유효성 검사
    private  boolean id_check(String id){

        if(Pattern.matches(id_pattern,id)){
        return true;
        }
        return false;
    }

    //비밀번호 유효성 검사

    private boolean password_check(String password){
        if (Pattern.matches(password_pattern,password))
        { return true; }
        return false;
    }

    //패스 워드 확인 매치
    private boolean password_check_check(String password,String password_check){
        if (password.equals(password_check))
        { return true; }
        return false;
    }

    private boolean phone_num_check(String phone_num){

        Pattern telephone = Pattern.compile("(\\d{3})(\\d{3,4})(\\d{4})");

        Matcher m = telephone.matcher(phone_num);
        if (m.matches()){
            return true;
        }
        Toast.makeText(this, ""+phone_num, Toast.LENGTH_SHORT).show();
        return false;


    }

    private boolean email_check(String email){

        if (Pattern.matches(email_pattern,email)){
            return true;
        }
        return false;
    }



    private boolean sign_up_check(String id, String password, String password_check,String phone,String E_mail){
        boolean sign_up_checkable=false;

        if (!id_overlap_checkable){
            Toast.makeText(this, "아이디 중복 확인 버튼을 눌려주세요", Toast.LENGTH_SHORT).show();
        }
        else if (!password_check(password)){
            Toast.makeText(this, "사용이 불가능한 비밀번호입니다", Toast.LENGTH_SHORT).show();
            this.password.requestFocus();
        }else if(!password_check_check(password,password_check)){

            Toast.makeText(this, "비밀번호와 비밀번호확인을 같게 입력해주세요", Toast.LENGTH_SHORT).show();
            this.password_check.requestFocus();
        }

        else if(name.getText().toString().equals("")){
            Toast.makeText(this, "이름을 입력해 주세요", Toast.LENGTH_SHORT).show();
            this.name.requestFocus();
        }
        else if(!phone_num_check(phone)){
            Toast.makeText(this, "형식에 맞지 않는 핸드폰 번호입니다.", Toast.LENGTH_SHORT).show();
            this.phone_num.requestFocus();
        }
        else if (email_check(E_mail)){
            this.email.requestFocus();
            Toast.makeText(this, "형식에 맞지 않는 이메일 주소 입니다.", Toast.LENGTH_SHORT).show();
        }
        else {
            sign_up_checkable=true;
        }

        return sign_up_checkable;
    }






}
