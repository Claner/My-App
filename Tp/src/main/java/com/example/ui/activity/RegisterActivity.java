package com.example.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Clanner on 2016/5/21.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private String url = "http://tpwhcm.com/TDetc/Home/User/register";
    //用于发送验证码的url
    private String codeUrl = "http://tpwhcm.com/TDetc/Home/User/sendMsg";
    private Handler handler;

    //控件
    private Button send;
    private EditText et_sendCode;
    private EditText et_code;
    private EditText et_userName;
    private EditText et_pass;
    private EditText et_surePass;
    private Button btn_register;
    private RadioButton btn_1;
    private RadioButton btn_2;
    private RadioButton btn_3;
    private RadioButton btn_4;

    private String number;          //用于发送的验证码的手机号
    private String code;            //验证码
    private String userName;        //用户名
    private String pass;            //密码
    private String surePass;        //确认密码
    private String role;            //职业

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        et_sendCode = (EditText) findViewById(R.id.et_sendCode);
        send = (Button) findViewById(R.id.btn_sendCode);
        et_code = (EditText) findViewById(R.id.et_code);
        et_userName = (EditText) findViewById(R.id.et_userName);
        et_pass = (EditText) findViewById(R.id.et_password);
        et_surePass = (EditText) findViewById(R.id.et_surepassWord);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_1 = (RadioButton) findViewById(R.id.radiobtn1);
        btn_2 = (RadioButton) findViewById(R.id.radiobtn2);
        btn_3 = (RadioButton) findViewById(R.id.radiobtn3);
        btn_4 = (RadioButton) findViewById(R.id.radiobtn4);
        handler = new Handler();
        send.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sendCode:
                sendCode();
                break;
            case R.id.btn_register:
                Register();
                break;
            case R.id.radiobtn1:
                role = "1";
                break;
            case R.id.radiobtn2:
                role = "2";
                break;
            case R.id.radiobtn3:
                role = "3";
                break;
            case R.id.radiobtn4:
                role = "4";
                break;
        }
    }

    /**
     * 发送验证码
     */
    private void sendCode() {
        number = et_sendCode.getText().toString().trim();
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("phone", number).build();
        Request request = new Request.Builder().url(codeUrl).post(body).build();
        Call cal = client.newCall(request);
        if (TextUtils.isEmpty(number)) {
            Toast.makeText(RegisterActivity.this, "请输入手机号码以获得验证码", Toast.LENGTH_SHORT).show();
        } else {
            cal.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("Hello", "失败");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.d("Hello", "成功");
                    Log.d("Helo", response.body().string());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterActivity.this, "发送成功,请注意查收", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }

    /**
     * 注册
     */
    private void Register() {
        code = et_code.getText().toString();
        userName = et_userName.getText().toString().trim();
        pass = et_pass.getText().toString().trim();
        surePass = et_surePass.getText().toString().trim();

        if (TextUtils.isEmpty(code) || TextUtils.isEmpty(number) || TextUtils.isEmpty(userName) ||
                TextUtils.isEmpty(pass) || TextUtils.isEmpty(surePass) || TextUtils.isEmpty(role)) {
            Toast.makeText(RegisterActivity.this, "必填信息不能为空", Toast.LENGTH_SHORT).show();
        } else {
            OkHttpClient client = new OkHttpClient();
            RequestBody body = new FormBody.Builder().add("phone", number).add("code", code)
                    .add("password", pass).add("rePassword", surePass).add("role", role).build();
            Request request = new Request.Builder().url(url).post(body).build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
            }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.d("Hello", response.body().string());
                }
            });
        }
    }

}
