package com.example.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.Utils.PersistentCookieStore;
import com.example.entity.LoginHelper;
import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;

/**
 * Created by Clanner on 2016/5/18.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private String url = "http://115.28.28.8/TDetc/Home/User/login";
    private OkHttpClient client;
    private Handler handler;
    private Gson gson;

    private EditText et_phoneNumber;
    private EditText et_passWord;
    private ProgressBar progressbar;
    private String phone;
    private String passWord;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);
        initView();
    }

    /**
     * 初始化控件,并设置点击事件
     */
    private void initView() {
        et_phoneNumber = (EditText) findViewById(R.id.et_phoneNumber);
        et_passWord = (EditText) findViewById(R.id.et_passWord);
        progressbar = (ProgressBar) findViewById(R.id.lg_progressbar);
        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.register).setOnClickListener(this);
        findViewById(R.id.forget).setOnClickListener(this);

        handler = new Handler();
        gson = new Gson();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                Login();
                break;
            case R.id.register:
                Register();
                break;
            case R.id.forget:
                break;
        }
    }

    /**
     * 登陆
     */
    private void Login() {
        phone = et_phoneNumber.getText().toString().trim();
        passWord = et_passWord.getText().toString().trim();
        client = new OkHttpClient();
        client.setCookieHandler(new CookieManager(new PersistentCookieStore(getApplicationContext()), CookiePolicy.ACCEPT_ALL));
        RequestBody body = new FormEncodingBuilder().add("phone", phone).add("password", passWord).build();
        Request request = new Request.Builder().url(url).post(body).build();
        Call call = client.newCall(request);
        progressbar.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(passWord)) {
            Toast.makeText(LoginActivity.this, "必填信息不能为空", Toast.LENGTH_SHORT).show();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    progressbar.setVisibility(View.GONE);
                }
            });
        } else {
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    Log.d("Hello", "失败");
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    Log.d("Hello", "成功");
                    LoginHelper helper = gson.fromJson(response.body().string(), LoginHelper.class);
                    final int code = helper.getCode();
                    if (code == 20000) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressbar.setVisibility(View.GONE);
                                Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    } else {
                        Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /**
     * 注册
     */
    private void Register() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
