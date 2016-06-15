package com.chat;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * Created by Clanner on 2016/5/24.
 */
public class MyApplication extends Application {

    //用于存放倒计时时间
    public static Map<String,Long> map;

    public static OkHttpClient client;
//    private static MyApplication instance;
//    private List<Activity> activityList = new LinkedList<Activity>();
//
//    private MyApplication() {
//
//    }
//
//    //单列模式中获取唯一的MyApplication实例
//    public static MyApplication getInstance() {
//        if (instance == null) {
//            instance = new MyApplication();
//        }
//        return instance;
//    }

    public static OkHttpClient getClient(final Context context) {
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
     * 保存Cookie
     */
    public static void saveCookie(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences("cookie", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

//    //添加Activity到容器中
//    public void addActivity(Activity activity) {
//        activityList.add(activity);
//    }
//
//    //遍历所有Activity并finish
//    public void exit() {
//        for (Activity activity : activityList) {
//            activity.finish();
//        }
//        System.exit(0);
//    }
}
