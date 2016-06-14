package com.chat.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import com.chat.MyApplication;

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

/**
 * Created by Clanner on 2016/5/24.
 */
public class MyOkHttpManager {

    private static MyOkHttpManager manager;
    private OkHttpClient client;
    private Handler handler;
    private Context context;

    private MyOkHttpManager() {
        client = new OkHttpClient.Builder().cookieJar(new CookieJar() {
            private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                if (cookies != null) {
                    StringBuilder cookieBuilder = new StringBuilder();
                    for (Cookie cookie : cookies) {
                        cookieBuilder.append(cookie);
                    }
                    saveCookie(context, "cookie", cookieBuilder.toString());
                }
                cookieStore.put(url, cookies);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url.host());
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        }).build();
        handler = new Handler();
    }

    public static MyOkHttpManager getInstance() {
        if (manager == null) {
            synchronized (MyOkHttpManager.class) {
                if (manager == null) {
                    manager = new MyOkHttpManager();
                }
            }
        }
        return manager;
    }

    /**
     * 对外公开的方法
     */

    public static Response Post(String url, Param[] params) throws IOException {
        return getInstance().post_Sync(url, params);
    }

    public static void Post(String url, ResultCallback callback, Param... params) {
        getInstance().post_Asyn(url, callback, params);
    }

    public static void Get(String url, ResultCallback callback) {
        getInstance().get_Sync(url, callback);
    }

    /**
     * 保存Cookie
     */
    public static void saveCookie(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /***/

    public OkHttpClient getClient(final Context context) {
        if (client == null) {
            synchronized (MyApplication.class) {
                if (client == null) {
                    client = new OkHttpClient.Builder().cookieJar(new CookieJar() {
                        private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

                        @Override
                        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                            if (cookies != null) {
                                StringBuilder cookieBuilder = new StringBuilder();
                                for (Cookie cookie : cookies) {
                                    cookieBuilder.append(cookie);
                                }
                                saveCookie(context, "cookie", cookieBuilder.toString());
                            }
                            cookieStore.put(url, cookies);
                        }

                        @Override
                        public List<Cookie> loadForRequest(HttpUrl url) {
                            List<Cookie> cookies = cookieStore.get(url.host());
                            return cookies != null ? cookies : new ArrayList<Cookie>();
                        }
                    }).build();
                }
            }
        }
        return client;
    }

    /**
     * 获取Cookie
     */
    public String getCookie(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    public void setContext(Context context) {
        this.context = context;
    }


    /**
     * 以下方法为内部封装的
     */

    /**
     * 同步的Get请求
     */
    private void get_Sync(String url, final ResultCallback callback) {
        String cookieString = getCookie(context, "cookie");
        Request request = new Request.Builder().url(url).header("cookie", cookieString).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callback.onResponse(response);
            }
        });
    }

    /**
     * 同步的post请求
     */
    private Response post_Sync(String url, Param[] params) throws IOException {
        RequestBody body = buildBody(params);

        Request request = new Request.Builder().url(url).post(body).build();
        Response response = client.newCall(request).execute();
        return response;
    }

    /**
     * 异步的post请求
     *
     * @param url
     * @param callback
     * @param params
     */
    private void post_Asyn(String url, final ResultCallback callback, Param[] params) {
        RequestBody body = buildBody(params);

        Request request = new Request.Builder().url(url).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                callback.onResponse(response);
            }
        });
    }

    private RequestBody buildBody(Param[] params) {
        FormBody.Builder builder = new FormBody.Builder();
        for (Param param : params) {
            builder.add(param.key, param.value);
        }
        RequestBody body = builder.build();
        return body;
    }

    public static abstract class ResultCallback {

        public abstract void onError(IOException e);

        public abstract void onResponse(Response response) throws IOException;
    }

    public static class Param {
        String key;
        String value;

        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
}
