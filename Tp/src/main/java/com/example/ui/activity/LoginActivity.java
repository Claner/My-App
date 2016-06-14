package com.example.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ui.activity.com.example.entity.LoginHelper;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private String url = "http://tpwhcm.com/TDetc/Home/User/login";
    private Gson gson;
    private Handler handler;

    //控件
    private EditText etphoneNumber;
    private EditText etpassWord;
    private Button log;
    private Button register;
    private TextView forget;
    private ProgressBar progressBar;

    private String phone;
    private String passWord;
    int code = 0;//同于判断是否登陆成功

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        etphoneNumber = (EditText) findViewById(R.id.et_phoneNumber);
        etpassWord = (EditText) findViewById(R.id.et_passWord);
        log = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        forget = (TextView) findViewById(R.id.forget);
        progressBar = (ProgressBar) findViewById(R.id.lg_progressbar);
        log.setOnClickListener(this);
        register.setOnClickListener(this);
        forget.setOnClickListener(this);
        gson = new Gson();
        handler = new Handler();
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
                Toast.makeText(LoginActivity.this, "忘记密码", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void Login() {
        phone = etphoneNumber.getText().toString().trim();
        passWord = etpassWord.getText().toString().trim();

        OkHttpClient client = new OkHttpClient.Builder().cookieJar(new CookieJar() {
            private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                if (cookies != null) {
                    StringBuilder cookieBuilder = new StringBuilder();
                    for (Cookie cookie : cookies) {
                        cookieBuilder.append(cookie);
                    }
                    saveCookie(getApplicationContext(), "cookie", cookieBuilder.toString());
                }
                cookieStore.put(url, cookies);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url.host());
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        }).build();
        RequestBody body = new FormBody.Builder().add("phone", phone).add("password", passWord).build();
        Request request = new Request.Builder().url(url).post(body).build();
        Call call = client.newCall(request);

        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(passWord)) {
            Toast.makeText(LoginActivity.this, "请确认所有信息已填", Toast.LENGTH_SHORT).show();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("Hello", "失败");
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    LoginHelper helper = gson.fromJson(response.body().string(), LoginHelper.class);
                    code = helper.getCode();
                    if (code == 20000) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
//                        Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
//                        Log.d("Hello", "用户名或密码错误");
                    }
                }
            });
        }
    }

    private void Register() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * 保存Cookie
     */
    public void saveCookie(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences("cookie", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
